package com.ahmedMustafa.Hani.di.modules.fragment;

import android.content.Context;

import com.ahmedMustafa.Hani.adapter.CompanyMainAdapter;
import com.ahmedMustafa.Hani.adapter.MyComapnyOrderAdapter;
import com.ahmedMustafa.Hani.fragment.company.CompanyOrderFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class CompanyModule {

    @Provides
    CompanyMainAdapter CompanyMainAdapter(Context context){
        return new CompanyMainAdapter(context);
    }

    @Provides
    MyComapnyOrderAdapter adapter(Context context, CompanyOrderFragment fragment){
        return new MyComapnyOrderAdapter(context,fragment);
    }
}
