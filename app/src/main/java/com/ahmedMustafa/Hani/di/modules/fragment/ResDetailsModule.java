package com.ahmedMustafa.Hani.di.modules.fragment;

import android.content.Context;

import com.ahmedMustafa.Hani.adapter.TroodOrderAdapter;
import com.ahmedMustafa.Hani.adapter.TroodPriceAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class ResDetailsModule {

    @Provides
    TroodPriceAdapter provideAdapter(Context context){
        return new TroodPriceAdapter(context);
    }
}
