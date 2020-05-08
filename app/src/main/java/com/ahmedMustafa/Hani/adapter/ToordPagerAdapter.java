package com.ahmedMustafa.Hani.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ahmedMustafa.Hani.fragment.trood.TroodDetailsFragment;
import com.ahmedMustafa.Hani.fragment.trood.WatingTroodFragment;
import com.ahmedMustafa.Hani.utils.Constant;

public class ToordPagerAdapter extends FragmentPagerAdapter {
    private String id, name, phone;
    private double lat, lng;

    public ToordPagerAdapter(FragmentManager fm, String id, String name, double lat, double lng, String phone) {
        super(fm);
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return WatingTroodFragment.newInstance(id);
            case 1:
                return TroodDetailsFragment.newInstance(id, name, lat, lng, phone);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "قائمة الأسعار";
            case 1:
                return "معلومات عن الشركه";
        }
        return super.getPageTitle(position);
    }
}
