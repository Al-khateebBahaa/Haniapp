package com.ahmedMustafa.Hani.di.modules.fragment;

import android.content.Context;

import com.ahmedMustafa.Hani.adapter.RestaurantOrderAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class WatingOrderModule {

    @Provides
    RestaurantOrderAdapter provideAdapter(Context context){
        return new RestaurantOrderAdapter(context);
    }
}
