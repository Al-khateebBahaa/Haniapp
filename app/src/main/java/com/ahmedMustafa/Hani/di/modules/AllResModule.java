package com.ahmedMustafa.Hani.di.modules;

import android.content.Context;

import com.ahmedMustafa.Hani.adapter.NearbyResAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class AllResModule {

    @Provides
    NearbyResAdapter provideAdapter(Context c){
        return new NearbyResAdapter(c);
    }
}
