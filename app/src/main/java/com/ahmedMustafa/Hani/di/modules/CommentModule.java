package com.ahmedMustafa.Hani.di.modules;

import android.content.Context;

import com.ahmedMustafa.Hani.adapter.RateAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class CommentModule {

    @Provides
    RateAdapter provideAdapter(Context context){
        return new RateAdapter(context);
    }
}
