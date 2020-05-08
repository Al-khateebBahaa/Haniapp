package com.ahmedMustafa.Hani.di.modules.fragment;

import android.content.Context;

import com.ahmedMustafa.Hani.adapter.MyOrderAdapter;
import com.ahmedMustafa.Hani.fragment.MyOrderFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class MyOrderModule {

    @Provides
    MyOrderAdapter provideAdapter(Context context, MyOrderFragment fragment) {
        return new MyOrderAdapter(context,fragment);
    }
}
