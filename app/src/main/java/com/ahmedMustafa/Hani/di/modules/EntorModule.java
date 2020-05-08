package com.ahmedMustafa.Hani.di.modules;

import com.ahmedMustafa.Hani.adapter.EntroAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class EntorModule {
    @Provides
    EntroAdapter provideAdapter(){
        return new EntroAdapter();
    }
}
