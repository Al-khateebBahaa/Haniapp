package com.ahmedMustafa.Hani.activity.settingPages;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.model.UserInfoModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditProfileActivity extends BaseActivity {

    private EditText editName, editEmail, editPhone;
    private CircleImageView imageView;
    private final static int SELECT_IMAGE = 1;
    private String image;
    private boolean isSelectImage;
    private Toolbar toolbar;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        bind();
        onClick();
    }

    private void bind() {

        imageView = findViewById(R.id.image);
        button = findViewById(R.id.sendBut);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("تعديل الملف الشخصي");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhoneCard);

        editName.setText(getPrefManager().getName());
        editEmail.setText(getPrefManager().getEmail());
        editPhone.setText(getPrefManager().getPhone());

        String imagePath;
        if (getPrefManager().getLoginMode() == Constant.PHONE_MODE) {
            imagePath = Constant.BASE_IMAGE + getPrefManager().getImage();
        } else {
            imagePath = getPrefManager().getImage();
        }
        Picasso.get().load(imagePath).into(imageView);
    }

    private void onClick() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 4)) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, SELECT_IMAGE);
                }
            }
        });
    }

    private void validation() {

        if (isValidText(editName) && isValidEmail(editEmail) && isValidPhone(editPhone)) {
            if (isConnected())
                sendRequest();
            else
                toast(R.string.noNetwork);
        }
    }

    private void sendRequest() {
        showProgress();
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", getPrefManager().getUserId());
        map.put("name", getString(editName));
        map.put("email", getString(editEmail));
        map.put("phone", getString(editPhone));
        if (isSelectImage) map.put("image", image);

        getApi().editProfile(map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserInfoModel userInfoModel) {
                        hideProgress();
                        if (userInfoModel.getStatus() == 1) {
                            UserInfoModel.User user = userInfoModel.getUser();
                            getPrefManager().setImage(user.getImage());
                            getPrefManager().updateData(user.getId() + "", user.getName(), user.getEmail(), user.getPhone(), user.getLat(), user.getLng(), user.getImage());
                            toast("تم تحديث بيانات الملف الشخصي بنجاح");
                            onBackPressed();
                            finish();
                        } else {
                            toast(userInfoModel.getError());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                        toast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {

            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA},
                    null, null, null);

            if (data != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                Bitmap bitmap = BitmapFactory.decodeFile(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                imageView.setImageBitmap(bitmap);
                image = convertImageToString(bitmap);
                isSelectImage = true;

            }
        }
    }
}
