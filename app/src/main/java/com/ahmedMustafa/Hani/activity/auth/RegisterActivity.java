package com.ahmedMustafa.Hani.activity.auth;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.MainActivity;
import com.ahmedMustafa.Hani.model.UserInfoModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.GPSTracker;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;

import java.util.HashMap;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity {

    private static final int SELECT_IMAGE = 5;
    //  private Toolbar toolbar;
    private EditText editPassword, editName, editPhone;
    private CircleImageView imageView;
    private String image;
    private boolean isSelctImage;
    private Button registerBut;

    @Inject
    GPSTracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bind();
        onClick();
    }

    private void bind() {
        registerBut = findViewById(R.id.registerBut);
        editPassword = findViewById(R.id.editPassword);
        editName = findViewById(R.id.regist_edit_name);
        imageView = findViewById(R.id.image);
        editPhone = findViewById(R.id.editPhone);
        //  toolbar = findViewById(R.id.toolbar);
        //   setSupportActionBar(toolbar);
        //  getSupportActionBar().setTitle("البروفايل");


    }

    private void onClick() {

/*
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 4)) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, SELECT_IMAGE);
                }
            }
        });*/

        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
                startActivity(new Intent(RegisterActivity.this, VerficationPhoneCode.class));
            }
        });
    }

    private void validation() {
        if (isValidText(editName) && isValidPassword(editPassword) && isValidPhone(editPhone)) {

            Log.e("image", image + "");
            if (isConnected()) {
                sendRequest();
            } else {
                toast(R.string.noNetwork);
            }
        }
    }

    private void sendRequest() {
        showProgress();

        HashMap<String, String> map = new HashMap<>();
        if (isSelctImage) map.put("image", image);
        map.put("name", getString(editName));
        // map.put("email", getString(editEmail));
        map.put("phone", getString(editPhone));
        map.put("lat", tracker.getLat() + "");
        map.put("lng", tracker.getLon() + "");
        map.put("password", getString(editPassword));
        /*
        Log.e("Sing-up name", getString(editName));
     //   Log.e("Sing-up email", getString(editEmail));
        Log.e("Sing-up phone", getIntent().getStringExtra("phone"));
        Log.e("Sing-up lat", getString(editName));
        Log.e("Sing-up lng", getString(editName));
        Log.e("Sing-up password", getIntent().getStringExtra("password"));*/
        if (isSelctImage) Log.e("Sing-up img", image);

        getApi().singUp(map)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserInfoModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserInfoModel userInfoModel) {
                hideProgress();
                if (userInfoModel.getStatus() == 1) {
                    UserInfoModel.User user = userInfoModel.getUser();
                    getPrefManager().updateData(
                            user.getId() + "",
                            user.getName() + "",
                            user.getEmail() + "",
                            user.getPhone() + "",
                            user.getLat() + "",
                            user.getLng() + "",
                            user.getImage()

                    );
                    getPrefManager().setLoginMode(Constant.PHONE_MODE);
                    openActivity(new Intent(RegisterActivity.this, MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
                    toast(userInfoModel.getError());
                }
            }

            @Override
            public void onError(Throwable e) {
                hideProgress();
                toast("بريد إلكروني موجود بالفعل");
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
                Bitmap bitmap = BitmapFactory.decodeFile(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                imageView.setImageBitmap(bitmap);
                image = convertImageToString(bitmap);
                isSelctImage = true;

            }
        }
    }
}
