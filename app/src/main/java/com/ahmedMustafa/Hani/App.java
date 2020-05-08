package com.ahmedMustafa.Hani;

import android.app.Activity;
import android.app.Application;

import com.ahmedMustafa.Hani.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {

    /*
    agent
    id = 1
    image =
    name = agent
    email = a@a.com
    phone = 987654321
    lat = 31.11
    lng = 31.11


    client
    id = 2
    image =
    name = client
    email = h@h.com
    phone = 123456789
    lat = 31.11
    lng = 31.11

    */
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
