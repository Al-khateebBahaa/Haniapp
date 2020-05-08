package com.ahmedMustafa.Hani.activity.orders;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.MainActivity;
import com.ahmedMustafa.Hani.activity.auth.SelectLocationActivity;
import com.ahmedMustafa.Hani.model.MakeOrderModel;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewOrderActivity extends BaseActivity {

    private View addImageView, timeView, placeView, sendBut;
    // private TextView addImageText, timeText, placeText;
    private EditText editText;
    private Toolbar toolbar;
    private boolean isSelctTime, isSelectImage, isSelectPlace;
    private final static int SELECT_LOCATION = 3;
    private final static int SELECT_IMAGE = 2;
    private final static int SELECT_TIME = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        bind();
        onClick();
        initTime();
    }

    private void bind() {

        sendBut = findViewById(R.id.sendBut);
        toolbar = findViewById(R.id.toolbar);
        //addImageText = findViewById(R.id.addImageText);
        addImageView = findViewById(R.id.addImageView);
        editText = findViewById(R.id.editText);
        timeView = findViewById(R.id.timeView);
        //  timeText = findViewById(R.id.timeText);
        //   placeText = findViewById(R.id.placeText);
        placeView = findViewById(R.id.placeView);

    }

    private void onClick() {

        placeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewOrderActivity.this, SelectLocationActivity.class);
                startActivityForResult(i, SELECT_LOCATION);
            }
        });

        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dialog.show();

                startActivityForResult(new Intent(NewOrderActivity.this , DeliverdTime.class ) , SELECT_TIME);

            }
        });

        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 4)) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, SELECT_IMAGE);
                }*/

                showNewOrderDialog();
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
        if (!isSelectPlace) {
            toast("يجب تحديد مكان الطلب أولا");
        } else if (!isSelctTime) {
            toast("يجب تحديد وقت توصيل الطلب أولا");
        } else if (TextUtils.isEmpty(getString(editText))) {
            editText.setError(getString(R.string.emptyField));
            editText.requestFocus();
        } else if (!isConnected()) {
            toast(R.string.noNetwork);
        } else {
            sendRequest();
        }
    }

    private String userAddress;
    private String lat, lng, image, time;

    private void sendRequest() {

        showProgress();
        HashMap<String, String> map = new HashMap<>();
        map.put("restaurant_id", getIntent().getStringExtra("id"));
        map.put("restaurant_name", getIntent().getStringExtra("name"));
        map.put("restaurant_lat", getIntent().getStringExtra("lat"));
        map.put("restaurant_lng", getIntent().getStringExtra("lng"));
        map.put("restaurant_address", getIntent().getStringExtra("address"));
        map.put("user_id", getPrefManager().getUserId());
        map.put("order_details", getString(editText));
        map.put("address", userAddress);
        map.put("lat", lat);
        map.put("lng", lng);
        map.put("delivery_duration", time);//minute
        if (isSelectImage) map.put("image", image);
        getApi().makeOrder(map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MakeOrderModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MakeOrderModel makeOrderModel) {
                        hideProgress();
                        if (makeOrderModel.getStatus() == 1) {
                            toast("تم إرسال الطلب بنجاح");
                            openActivity(new Intent(NewOrderActivity.this, MainActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        } else {
                            toast(makeOrderModel.getMsg());
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

    private Dialog dialog;

    private void initTime() {
        final String[] list = new String[]{"1 ساعة", "2 ساعة", "3 ساعة", "1 يوم", "2 يوم", "3 يوم"};
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.country_dialog);
        ListView listView = dialog.findViewById(R.id.listView);
        final TextView title = dialog.findViewById(R.id.title);
        title.setText("إختر وقت التوصيل");
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((Button) timeView).setText(list[position]);
                int t = 0;
                switch (position) {
                    case 0:
                        t = 60;
                        break;
                    case 1:
                        t = 120;
                        break;
                    case 2:
                        t = 60 * 3;
                        break;
                    case 3:
                        t = 60 * 24;
                        break;
                    case 4:
                        t = 60 * 48;
                        break;
                    case 5:
                        t = 60 * 72;
                        break;
                }
                time = t + "";
                isSelctTime = true;
                Log.e("time", time);
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_LOCATION && resultCode == RESULT_OK) {
            userAddress = data.getStringExtra("address");
            lat = data.getStringExtra("lat");
            lng = data.getStringExtra("lng");
            ((Button) placeView).setText(userAddress);
            isSelectPlace = true;
        } else if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {

            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA},
                    null, null, null);

            if (data != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                //addImageText.setText(cursor.getString(nameIndex));
                //   addImageText.setText("تم إرفاق صوره");
                Bitmap bitmap = BitmapFactory.decodeFile(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                image = convertImageToString(bitmap);
                isSelectImage = true;

            }else if(requestCode == SELECT_TIME && requestCode == RESULT_OK){
                ((Button)timeView).setText(data.getStringExtra("time"));
            }

        }
    }

    private void showNewOrderDialog() {

        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);

        mBottomSheetDialog.setContentView(R.layout.new_order_bottom_sheet);

        mBottomSheetDialog.show();

    }

}
