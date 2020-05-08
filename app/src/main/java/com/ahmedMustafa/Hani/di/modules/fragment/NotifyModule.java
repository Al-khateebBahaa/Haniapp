package com.ahmedMustafa.Hani.di.modules.fragment;

import android.content.Context;

import com.ahmedMustafa.Hani.adapter.NotificationAdapter;
import com.ahmedMustafa.Hani.fragment.NotifyFragment;
import com.ahmedMustafa.Hani.utils.GPSTracker;

import dagger.Module;
import dagger.Provides;

@Module
public class NotifyModule {

    @Provides
    GPSTracker provideLocation(Context context){
        return new GPSTracker(context);
    }
    @Provides
    NotificationAdapter provideAdapter(Context context, NotifyFragment fragment) {
        return new NotificationAdapter(context,fragment);
    }


}
