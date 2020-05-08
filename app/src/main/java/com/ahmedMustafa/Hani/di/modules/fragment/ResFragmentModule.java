package com.ahmedMustafa.Hani.di.modules.fragment;

import android.content.Context;

import com.ahmedMustafa.Hani.adapter.NearbyResAdapter;
import com.ahmedMustafa.Hani.utils.GPSTracker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ResFragmentModule {

    @Provides
    NearbyResAdapter provideAdapter(Context context){
        return new NearbyResAdapter(context);
    }
}
