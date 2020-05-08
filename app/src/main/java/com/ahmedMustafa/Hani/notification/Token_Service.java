package com.ahmedMustafa.Hani.notification;

import android.app.IntentService;
import android.content.Intent;

import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.utils.Api;
import com.ahmedMustafa.Hani.utils.PrefManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Token_Service extends IntentService {

    public Token_Service() {
        super("FCM");
    }
    private PrefManager prefManager;


    @Override
    protected void onHandleIntent(Intent paramIntent) {
        prefManager = new PrefManager(this);

         new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://khdmatyapp.com/api/")
                .client(new OkHttpClient.Builder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .build())
                .build().create(Api.class).refreshToken(
                        prefManager.getUserId(), FirebaseInstanceId.getInstance().getToken(),"android"
         ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Observer<PublicModel>() {
                     @Override
                     public void onSubscribe(Disposable d) {

                     }

                     @Override
                     public void onNext(PublicModel model) {

                     }

                     @Override
                     public void onError(Throwable e) {

                     }

                     @Override
                     public void onComplete() {

                     }
                 });
    }
}
