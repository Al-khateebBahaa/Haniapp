package com.ahmedMustafa.Hani.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkReceiver extends BroadcastReceiver {

    private InternetListener listener;

    public NetworkReceiver() {
    }

    public NetworkReceiver(InternetListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if ((this.listener != null) && (Common.isConnected(context))) {

            this.listener.networkConnected();

        }

    }

    public interface InternetListener {
        public void networkConnected();
    }
}
