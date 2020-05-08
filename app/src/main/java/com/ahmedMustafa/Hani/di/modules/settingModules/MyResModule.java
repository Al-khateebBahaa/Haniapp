package com.ahmedMustafa.Hani.di.modules.settingModules;

import android.content.Context;

import com.ahmedMustafa.Hani.activity.settingPages.MyResActivity;
import com.ahmedMustafa.Hani.adapter.AllAgentFroTroodAdapter;
import com.ahmedMustafa.Hani.adapter.MResAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class MyResModule {

    @Provides
    MResAdapter adapter(Context context,MyResActivity activity) {
        return new MResAdapter(context, activity);
    }

    @Provides
    AllAgentFroTroodAdapter adapter2(Context context) {
        return new AllAgentFroTroodAdapter(context);
    }
}
