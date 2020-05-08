package com.ahmedMustafa.Hani.activity.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChangeBillActivity extends BaseActivity {

    private View sendBut,cancelBut;
    private TextView orderPriceView,total;
    private EditText editPrice;
    private Integer oldPrice,orderPrice;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_bill);
        orderId = getIntent().getStringExtra("id");
        oldPrice = getIntent().getIntExtra("price",0);
        orderPrice = getIntent().getIntExtra("orderPrice",0);
        bind();
        onClick();
    }

    private void bind(){
        sendBut = findViewById(R.id.sendBut);
        cancelBut = findViewById(R.id.cancelBut);
        orderPriceView = findViewById(R.id.orderPrice);
        total = findViewById(R.id.total);
        editPrice = findViewById(R.id.editPrice);

        orderPriceView.setText(orderPrice+"");
        editPrice.setText(oldPrice+"");
        total.setText((orderPrice+ oldPrice)+"");


    }

    private void onClick(){

        sendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

        cancelBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void validation(){

        if (isValidText(editPrice)){
            if (isConnected()){
                sendRequest();
            }else {
                toast(R.string.noNetwork);
            }
        }
    }

    private void sendRequest(){
        showProgress();
        getApi().changeBill(orderId,orderPrice+"",getString(editPrice))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PublicModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PublicModel model) {
                        hideProgress();
                        if (model.getStatus() == 1){
                            toast("تم تغيير الفاتوره بنجاح");
                            Intent i = new Intent();
                            i.putExtra("price",getString(editPrice));
                            setResult(RESULT_OK,i);
                            finish();
                        }else {
                            toast(model.getMsg());
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
}
