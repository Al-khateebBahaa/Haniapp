package com.ahmedMustafa.Hani.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.ahmedMustafa.Hani.fragment.restaurant.ResDetailsFragment;
import com.ahmedMustafa.Hani.fragment.restaurant.WatingOrderFragment;
import com.ahmedMustafa.Hani.utils.Constant;

public class RestaurantDetailsPager extends FragmentPagerAdapter {
    private String id, name, address;
    private double lat, lng;
    private int type;

    public RestaurantDetailsPager(FragmentManager fm, String id, String name, double lat, double lng, String address, int type) {
        super(fm);
        this.id = id;
        this.address = address;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return WatingOrderFragment.newInsanse(id);
            case 1:
                return ResDetailsFragment.newInstance(id, name, address, lat, lng);
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
                return "طلبات في الإنتظار";
            case 1:
                return type == Constant.STORE ? "معلومات عن المتجر" : "معلومات عن المطعم";
        }
        return super.getPageTitle(position);
    }
}
