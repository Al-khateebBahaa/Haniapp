package com.ahmedMustafa.Hani.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.model.PayInfoModel;
import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BankTransferActivity extends BaseActivity {

    private Toolbar toolbar;
    private Button sendBut;
    private EditText editFromAdapter,editPhone,editFromBank,editAccountNum,editTransferNum;
    private TextView accountNum,iban;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer);
        bind();
        editFromAdapter.setText(getPrefManager().getName());
        editPhone.setText(getPrefManager().getPhone());
        onClick();

    }

    private void bind(){
        setDataFrame(findViewById(R.id.dataLayout),findViewById(R.id.layout));
        sendBut = findViewById(R.id.sendBut);
        editFromAdapter = findViewById(R.id.editFromAdapter);
        editPhone = findViewById(R.id.editPhoneCard);
        editFromBank = findViewById(R.id.editFromBank);
        editAccountNum = findViewById(R.id.editAccountNum);
        editTransferNum = findViewById(R.id.editTransferNum);
        accountNum = findViewById(R.id.accountNum);
        iban = findViewById(R.id.iban);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("دفع رصيد الحساب");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void onClick(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private void validation(){
        if (isValidText(editFromAdapter)&& isValidPhone(editPhone)&& isValidText(editFromBank)&& isValidText(editAccountNum)&& isValidText(editTransferNum)){
            if (isConnected()){
                sendRequest();
            }else {
                toast(R.string.noNetwork);
            }
        }
    }

    private void sendRequest(){
        showProgress();
        getApi().makeTrasfer(
                getPrefManager().getUserId(), getString(editFromAdapter),getString(editPhone),getString(editFromBank),
                getString(editAccountNum),getString(editTransferNum)
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PublicModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PublicModel makeTransferModel) {
                hideProgress();
                if (makeTransferModel.getStatus() ==1) {
                    toast("تم إرسال الطلب بنجاح");
                    onBackPressed();
                    finish();
                }else {
                    toast(makeTransferModel.getMsg());
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
    protected void loadData() {
        super.loadData();
        getApi().getPayInfo().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PayInfoModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PayInfoModel payInfoModel) {
                if (payInfoModel.getStatus() == 1){
                    iban.setText(payInfoModel.getBankAccountIban());
                    accountNum.setText(payInfoModel.getBankAccountNumber());
                    dataFrame.setVisible(LAYOUT);
                }else {
                    dataFrame.setVisible(ERROR);
                }
            }

            @Override
            public void onError(Throwable e) {
                dataFrame.setVisible(ERROR);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
