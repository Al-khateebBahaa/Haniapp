package com.ahmedMustafa.Hani.activity.auth;

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
import android.widget.ImageView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.MainActivity;
import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;
import com.squareup.picasso.Picasso;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VerficationActivity extends BaseActivity {

    private Toolbar toolbar;
    private EditText editName, editEmail, editPhone, editId, editNational;
    private ImageView idImage, idCarImage, frontCarImage, endCarImage;
    private Button sendBut;
    private final static int SELECT_ID = 1;
    private final static int SELECT_ID_CAR = 2;
    private final static int SELECT_FRONT = 3;
    private final static int SELECT_END = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfication);
        bind();
        init();
        onClick();
    }

    private void bind() {

        toolbar = findViewById(R.id.toolbar);
        editName = findViewById(R.id.editName);
        editId = findViewById(R.id.editId);
        editNational = findViewById(R.id.editNational);
        editPhone = findViewById(R.id.editPhoneCard);
        editEmail = findViewById(R.id.editEmail);

        idImage = findViewById(R.id.idImage);
        idCarImage = findViewById(R.id.idCarImage);
        frontCarImage = findViewById(R.id.frontCarImage);
        endCarImage = findViewById(R.id.endCarImage);
        sendBut = findViewById(R.id.sendBut);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("نموذج توثيق مندوب");
    }

    private void init(){
        editName.setText(getPrefManager().getName());
        editPhone.setText(getPrefManager().getPhone());
        editEmail.setText(getPrefManager().getEmail());
    }
    private void onClick() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        endCarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 4)) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, SELECT_END);
                }
            }
        });

        frontCarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 4)) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, SELECT_FRONT);
                }
            }
        });

        idCarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 4)) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, SELECT_ID_CAR);
                }
            }
        });

        idImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 4)) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, SELECT_ID);
                }
            }
        });

        sendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private void validation() {

        if (isValidText(editName) && isValidText(editId) && isValidText(editNational) && isValidPhone(editPhone) && isValidEmail(editEmail)) {

            if (!isSelcetID) {
                toast("يجب إرفاق صورة الهوية الوطنية");
            } else if (!isSelcetIdCar) {
                toast("يجب إرفاق صورة رخصة القيادة");
            } else if (!isSelectFront) {
                toast("يجب إرفاق صورة  للسياره من الأمام");
            } else if (!isSelcetEnd) {
                toast("يجب إرفاق صورة  للسياره من الخلف");
            } else if (!isConnected()) {
                toast(R.string.noNetwork);
            } else {
                sendRequest();
            }
        }
    }

    private void sendRequest() {
        showProgress();
        getApi().activeAgent(
                getPrefManager().getUserId(),
                getString(editName),
                getString(editId),
                getString(editNational),
                getString(editPhone),
                getString(editEmail),
                id, idCar, front, end).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PublicModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PublicModel publicModel) {
                hideProgress();
                if (publicModel.getStatus() == 1) {
                    openActivity(new Intent(VerficationActivity.this, MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                    toast("تم إرسال طلبك بنجاح ، بإنتظار موافقة الإداره");
                } else {
                    toast(publicModel.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                toast(e.getMessage());
                hideProgress();
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private String id, idCar, front, end;
    private boolean isSelcetID, isSelcetIdCar, isSelectFront, isSelcetEnd;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA},
                    null, null, null);

            if (data != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                Bitmap bitmap = BitmapFactory.decodeFile(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                switch (requestCode) {
                    case SELECT_ID:
                        isSelcetID = true;
                        id = convertImageToString(bitmap);
                        Picasso.get().load(uri).fit().into(idImage);
                        break;
                    case SELECT_ID_CAR:
                        isSelcetIdCar = true;
                        idCar = convertImageToString(bitmap);
                        Picasso.get().load(uri).fit().into(idCarImage);
                        break;
                    case SELECT_FRONT:
                        isSelectFront = true;
                        front = convertImageToString(bitmap);
                        Picasso.get().load(uri).fit().into(frontCarImage);
                        break;
                    case SELECT_END:
                        isSelcetEnd = true;
                        end = convertImageToString(bitmap);
                        Picasso.get().load(uri).fit().into(endCarImage);
                        break;
                }
            }
        }
    }
}
