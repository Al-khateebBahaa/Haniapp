package com.ahmedMustafa.Hani.activity.auth;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.adapter.EntroAdapter;
import com.ahmedMustafa.Hani.utils.CircleIndicator;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;

import javax.inject.Inject;

public class EntroActivity extends BaseActivity {

    private ViewPager viewPager;
    private View skipBut;
    private CircleIndicator circleIndicator;
    @Inject
    EntroAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entro);
        bind();
        onClick();
    }

    private void bind() {
        viewPager = findViewById(R.id.viewPager);
        skipBut = findViewById(R.id.skipBut);
        circleIndicator = findViewById(R.id.indicator);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
    }

    private void onClick() {
        skipBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrefManager().setFirstTime();
                Intent i = new Intent(EntroActivity.this, SendPhoneNumberActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                openActivity(i);
            }
        });
    }
}
