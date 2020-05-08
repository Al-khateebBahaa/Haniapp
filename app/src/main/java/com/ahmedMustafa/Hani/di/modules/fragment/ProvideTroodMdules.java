package com.ahmedMustafa.Hani.di.modules.fragment;

import com.ahmedMustafa.Hani.di.modules.TroodDetailsModule;
import com.ahmedMustafa.Hani.fragment.trood.TroodDetailsFragment;
import com.ahmedMustafa.Hani.fragment.trood.WatingTroodFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ProvideTroodMdules {

    @ContributesAndroidInjector(modules = {ResDetailsModule.class})
    abstract TroodDetailsFragment provideTroodDetailsFragment();

    @ContributesAndroidInjector(modules = {ResDetailsModule.class})
    abstract WatingTroodFragment provideWatingTroodFragment();
}
