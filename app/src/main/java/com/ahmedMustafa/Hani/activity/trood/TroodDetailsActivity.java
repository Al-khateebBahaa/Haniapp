package com.ahmedMustafa.Hani.activity.trood;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.adapter.RestaurantDetailsPager;
import com.ahmedMustafa.Hani.adapter.ToordPagerAdapter;
import com.ahmedMustafa.Hani.model.TroodDetailsModel;
import com.ahmedMustafa.Hani.utils.CustomApi;
import com.ahmedMustafa.Hani.utils.PrefManager;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class TroodDetailsActivity extends BaseActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private String  name,phone;
    private double lat, lng;
    private int type;
    private String companyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trood_details);
        companyId = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        lat = getIntent().getDoubleExtra("lat", 0);
        lng = getIntent().getDoubleExtra("lng", 0);
        phone = getIntent().getStringExtra("phone");
        bind();
        onClick();
    }

    private void bind() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new ToordPagerAdapter(getSupportFragmentManager(),companyId, name, lat, lng,phone));
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
