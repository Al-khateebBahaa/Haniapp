package com.ahmedMustafa.Hani.di.modules.fragment;

import com.ahmedMustafa.Hani.fragment.restaurant.ResDetailsFragment;
import com.ahmedMustafa.Hani.fragment.restaurant.WatingOrderFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class
ProvideResModule {

    @ContributesAndroidInjector(modules = {WatingOrderModule.class})
    abstract WatingOrderFragment provideOrderFragment();

    @ContributesAndroidInjector(modules = {ResDetailsModule.class})
    abstract ResDetailsFragment provideResDetailsFragment();
}
