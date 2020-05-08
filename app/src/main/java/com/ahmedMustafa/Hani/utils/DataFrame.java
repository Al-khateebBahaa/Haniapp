package com.ahmedMustafa.Hani.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;

public class DataFrame extends FrameLayout {

    private View noInternetView, noItemView, errorView, progressView, layoutView;
    private TextView noItemText;

    public final int LAYOUT = 0;
    public final int NO_ITEM = 1;
    public final int NO_INTERNET = 2;
    public final int ERROR = 3;
    public final int PROGRESS = 4;

    public DataFrame(@NonNull Context context) {
        super(context);
        init();
    }

    public DataFrame(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DataFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DataFrame(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(getContext());

        noInternetView = inflater.inflate(R.layout.no_internet, null);
        noItemView = inflater.inflate(R.layout.no_item, null);
        errorView = inflater.inflate(R.layout.error_view, null);
        progressView = inflater.inflate(R.layout.progress_view, null);
        noItemText = noItemView.findViewById(R.id.noItem);
        this.addView(errorView);
        this.addView(progressView);
        this.addView(noItemView);
        this.addView(noInternetView);

    }

    public void setLayout(View layoutView) {

        this.layoutView = layoutView;

        if (Common.isConnected(getContext())) {
            setVisible(PROGRESS);
        } else {
            setVisible(NO_INTERNET);
        }
    }

    public View getLayoutView() {
        return layoutView;
    }

    public void setNoItemText(String text) {
        noItemText.setText(text);
    }

    private void setNoItemText(int textId) {
        noItemText.setText(getContext().getString(textId));
    }

    public void setVisible(int type) {

        View[] views = new View[]{layoutView, noItemView, noInternetView, errorView, progressView};

        for (int x = 0; x < views.length; x++) {

            if (type == x) {
                views[type].setVisibility(View.VISIBLE);
            } else {
//                views[x].setVisibility(View.GONE);
            }
        }
    }

    public void setHiddenAll() {

        View[] views = new View[]{layoutView, noItemView, noInternetView, errorView, progressView};

        for (int x = 0; x < views.length; x++) {
            views[x].setVisibility(View.GONE);
        }

    }
}
