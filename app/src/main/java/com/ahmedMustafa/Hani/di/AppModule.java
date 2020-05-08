package com.ahmedMustafa.Hani.di;

import android.app.Application;
import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.ahmedMustafa.Hani.utils.Api;
import com.ahmedMustafa.Hani.utils.PrefManager;


import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

//    private HttpLoggingInterceptor getInterceptor() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.level(HttpLoggingInterceptor.Level.BODY);
//
//        return interceptor;
//    }

    @Singleton
    @Provides
    Api provideRetrofit() {


        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://khdmatyapp.com/api/")
                .client(new OkHttpClient.Builder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .build())
                .build().create(Api.class);


    }

    @Singleton
    @Provides
    PrefManager providePrefManger(Context context) {
        return new PrefManager(context);
    }

    @Singleton
    @Provides
    Context provideContext(Application application) {
        return application;
    }
}
