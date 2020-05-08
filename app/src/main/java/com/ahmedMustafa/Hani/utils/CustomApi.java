package com.ahmedMustafa.Hani.utils;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public  class CustomApi<T> implements Observer<T> {
    private CallApi<T> callApi;
    private String TAG;

    public CustomApi(CallApi<T> callApi, String TAG) {
        this.callApi = callApi;
        this.TAG = TAG;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        Log.e(TAG + "_response", response.toString());
        callApi.onResponse(response);
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG + "_error", e.getMessage());
        callApi.onError(e.getMessage());
    }

    @Override
    public void onComplete() {

    }

    public interface CallApi<T> {
        void onResponse(T response);

        void onError(String msgError);
    }
}
