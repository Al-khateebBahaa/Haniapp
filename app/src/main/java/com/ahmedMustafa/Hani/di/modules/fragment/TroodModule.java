package com.ahmedMustafa.Hani.di.modules.fragment;

import android.content.Context;

import com.ahmedMustafa.Hani.adapter.TroodAdapter;
import com.ahmedMustafa.Hani.utils.GPSTracker;
import com.ahmedMustafa.Hani.utils.GetCurrntLocation;

import dagger.Module;
import dagger.Provides;

@Module
public class TroodModule {

    @Provides
    GPSTracker tracker(Context context) {
        return new GPSTracker(context);
    }

    @Provides
    TroodAdapter adapter(Context context) {
        return new TroodAdapter(context);
    }
}
