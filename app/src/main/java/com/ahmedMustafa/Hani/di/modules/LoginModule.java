package com.ahmedMustafa.Hani.di.modules;

import android.content.Context;

import com.ahmedMustafa.Hani.utils.GPSTracker;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {


    @Provides
    GoogleSignInOptions provideGoogleSingIn(){
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }
    @Provides
    CallbackManager provideLoginWithFB(){
        return CallbackManager.Factory.create();
    }

    @Provides
    GPSTracker provideGps(Context c){
        return new GPSTracker(c);
    }
}
