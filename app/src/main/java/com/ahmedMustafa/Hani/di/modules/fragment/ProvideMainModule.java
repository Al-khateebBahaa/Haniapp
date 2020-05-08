package com.ahmedMustafa.Hani.di.modules.fragment;

import com.ahmedMustafa.Hani.fragment.MyOrderFragment;
import com.ahmedMustafa.Hani.fragment.NotifyFragment;
import com.ahmedMustafa.Hani.fragment.ProfileFragment;
import com.ahmedMustafa.Hani.fragment.RestaurantFragment;
import com.ahmedMustafa.Hani.fragment.StoreFragment;
import com.ahmedMustafa.Hani.fragment.TroodFragment;
import com.ahmedMustafa.Hani.fragment.company.ComapnyMainFragment;
import com.ahmedMustafa.Hani.fragment.company.CompanyOrderFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ProvideMainModule {

    @ContributesAndroidInjector(modules = {ResFragmentModule.class})
    abstract RestaurantFragment provideResFragment();

    @ContributesAndroidInjector(modules = {ResFragmentModule.class})
    abstract StoreFragment provideStoreFragment();

    @ContributesAndroidInjector(modules = {ProfileFragmentModule.class})
    abstract ProfileFragment provideProfileFragment();

    @ContributesAndroidInjector(modules = {MyOrderModule.class})
    abstract MyOrderFragment provieMyOrderFragment();

    @ContributesAndroidInjector(modules = {NotifyModule.class})
    abstract NotifyFragment proviedNotifyFragment();

    @ContributesAndroidInjector(modules = {TroodModule.class})
    abstract TroodFragment proviedTroodFragment();

    @ContributesAndroidInjector(modules = {CompanyModule.class})
    abstract ComapnyMainFragment proviedComapnyMainFragment();

    @ContributesAndroidInjector(modules = {CompanyModule.class})
    abstract CompanyOrderFragment proviedCompanyOrderFragment();
}
