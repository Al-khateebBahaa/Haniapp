package com.ahmedMustafa.Hani.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.MainActivity;
import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.CustomApi;
import com.ahmedMustafa.Hani.utils.PrefManager;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
      //  getPrefManager().setLogout();
        if (getPrefManager().isLogin() && isConnected()){
            sendToken();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openActivity(new Intent(SplashActivity.this, getPrefManager().isLogin()?MainActivity.class:SendPhoneNumberActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }, 2000);
    }

    private void sendToken(){
        final String token = FirebaseInstanceId.getInstance().getToken();
        getApi().refreshToken(getPrefManager().getUserId(),token,"1")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(new CustomApi<PublicModel>(new CustomApi.CallApi<PublicModel>() {
                    @Override
                    public void onResponse(PublicModel response) {
                        if (response.getStatus() == 1){
                            getPrefManager().setToken(token);
                        }
                    }

                    @Override
                    public void onError(String msgError) {

                    }
                },"refresh"));
    }


}
