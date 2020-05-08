package com.ahmedMustafa.Hani.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;

import javax.inject.Inject;

public class EntroAdapter extends PagerAdapter {

    public @Inject
    EntroAdapter() {

    }

    private String[] titles = {"نص إختبارنص إختبارنص إختبارنص إختبارنص إختبارنص إختبارنص إختبارنص إختبارنص إختبار",
            "نص إختبارنص إختبارنص إختبارنص إختبارنص إختبارنص إختبارنص إختبارنص إختبارنص إختبارنص إختبار",
            "نص إختبارنص إختبارنص إختبارنص إختبارنص إختبارنص إختبارنص إختبارنص إختبارنص إختبارنص إختبارنص إختبار"};

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.entro_item, container, false);
        ((TextView) view.findViewById(R.id.text)).setText(titles[position]);
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
