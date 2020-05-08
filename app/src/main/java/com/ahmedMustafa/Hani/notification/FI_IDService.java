package com.ahmedMustafa.Hani.notification;

import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceIdService;

public class FI_IDService
        extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        startService(new Intent(this, Token_Service.class));
    }
}
