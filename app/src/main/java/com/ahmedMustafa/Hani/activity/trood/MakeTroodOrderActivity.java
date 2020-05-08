package com.ahmedMustafa.Hani.activity.trood;

import android.Manifest;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.MainActivity;
import com.ahmedMustafa.Hani.activity.auth.SelectLocationActivity;
import com.ahmedMustafa.Hani.activity.orders.NewOrderActivity;
import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.utils.CustomApi;
import com.ahmedMustafa.Hani.utils.PrefManager;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MakeTroodOrderActivity extends BaseActivity {

    private View timeView, placeView, sendBut;
    private TextView timeText, placeText;
    private EditText editText;
    private Toolbar toolbar;
    private boolean isSelctFrom, isSelectTo;
    private final static int SELECT_FROM = 3;
    private final static int SELECT_TO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_trood_order);

        findViewById(R.id.sendBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MakeTroodOrderActivity.this , NewOrderActivity.class));
            }
        });

     //   bind();
      //  onClick();
    }

    private void bind() {
        sendBut = findViewById(R.id.sendBut);
        toolbar = findViewById(R.id.toolbar);
     //   editText = findViewById(R.id.editText);
      //  timeView = findViewById(R.id.timeView);
      //  timeText = findViewById(R.id.timeText);
      //  placeText = findViewById(R.id.placeText);
      //  placeView = findViewById(R.id.placeView);
    }

    private void onClick() {
        placeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MakeTroodOrderActivity.this, SelectLocationActivity.class);
                startActivityForResult(i, SELECT_FROM);
            }
        });

        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MakeTroodOrderActivity.this, SelectLocationActivity.class);
                startActivityForResult(i, SELECT_TO);
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
        if (!isSelctFrom) {
            toast("يجب تحديد موقعك الحالي أولا");
        } else if (!isSelectTo) {
            toast("يجب تحديد موقع توصيل الطلب أولا");
        } else if (TextUtils.isEmpty(getString(editText))) {
            editText.setError(getString(R.string.emptyField));
            editText.requestFocus();
        } else if (!isConnected()) {
            toast(R.string.noNetwork);
        } else {
            sendRequest();
        }
    }

    private void sendRequest() {
        showProgress();
        getApi().makeTroodOrder(getPrefManager().getUserId(), getPrefManager().getImage(), getString(editText), getIntent().getStringExtra("id"),
                latFrom, lngFrom, addressFrom, latTo, lngTo, addressTo,getPrefManager().getName(),getPrefManager().getImage()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<PublicModel>(new CustomApi.CallApi<PublicModel>() {
                    @Override
                    public void onResponse(PublicModel response) {
                        hideProgress();
                        if (response.getStatus() == 1) {
                            toast("تم إرسال الطلب بنجاح");
                            openActivity(new Intent(MakeTroodOrderActivity.this, MainActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        } else {
                            toast(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(String msgError) {
                        hideProgress();
                        toast(msgError);
                    }
                }, "makeTroodOrder"));
    }

    private String latTo, lngTo, addressTo, latFrom, lngFrom, addressFrom;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FROM && resultCode == RESULT_OK) {

            addressFrom = data.getStringExtra("address");
            latFrom = data.getStringExtra("lat");
            lngFrom = data.getStringExtra("lng");
            placeText.setText(addressFrom);
            isSelctFrom = true;

        } else if (requestCode == SELECT_TO && resultCode == RESULT_OK) {

            addressTo = data.getStringExtra("address");
            latTo = data.getStringExtra("lat");
            lngTo = data.getStringExtra("lng");
            timeText.setText(addressTo);
            isSelectTo = true;

        }

    }
}
