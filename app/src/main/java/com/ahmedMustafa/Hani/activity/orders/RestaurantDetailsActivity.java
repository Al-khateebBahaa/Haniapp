package com.ahmedMustafa.Hani.activity.orders;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.adapter.RestaurantDetailsPager;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RestaurantDetailsActivity extends BaseActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private String id, name, address;
    private double lat, lng;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        lat = getIntent().getDoubleExtra("lat", 0);
        lng = getIntent().getDoubleExtra("lng", 0);
        address = getIntent().getStringExtra("address");
        type = getIntent().getIntExtra("type",1);
        bind();
        onClick();
    }

    private void bind() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new RestaurantDetailsPager(getSupportFragmentManager(), id, name, lat, lng, address,type));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void onClick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
