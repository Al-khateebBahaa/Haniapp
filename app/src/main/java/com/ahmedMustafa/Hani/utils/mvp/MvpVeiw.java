package com.ahmedMustafa.Hani.utils.mvp;

import android.support.annotation.StringRes;

public interface MvpVeiw {

    void onLoading();

    void hideLoading();

    void onNoNetworkConnected();

    void onError(String error);

    void onError(@StringRes int res);

    void showMsg(String error);

    void showMsg(@StringRes int res);

    void openActivityOnTokenExpire();
}
