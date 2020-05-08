package com.ahmedMustafa.Hani.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.auth.VerficationActivity;
import com.ahmedMustafa.Hani.activity.settingPages.AboutAppActivity;
import com.ahmedMustafa.Hani.activity.settingPages.EditProfileActivity;
import com.ahmedMustafa.Hani.activity.settingPages.HowWorkActivity;
import com.ahmedMustafa.Hani.activity.settingPages.MyResActivity;
import com.ahmedMustafa.Hani.activity.settingPages.PlociyActivity;
import com.ahmedMustafa.Hani.activity.settingPages.TermsActivity;
import com.ahmedMustafa.Hani.model.ChangeNotificationModel;
import com.ahmedMustafa.Hani.utils.Common;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Switch aSwitch;
    private View openMyResView, editProfileView, plocityView, howWorkView, termsView, reviewAppView, aboutAppView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        bind();
        if (getPrefManager().getUserType() == 1) {
            findViewById(R.id.viewNotify).setVisibility(View.GONE);
            textView.setText("مندوبين مسجلين بالشركه");
            howWorkView.setVisibility(View.GONE);
        }
    }

    private void bind() {
        textView = findViewById(R.id.textView);
        toolbar = findViewById(R.id.toolbar);
        aSwitch = findViewById(R.id.aSwcith);
        openMyResView = findViewById(R.id.openMyResView);
        editProfileView = findViewById(R.id.editProfileView);
        plocityView = findViewById(R.id.plocityView);
        howWorkView = findViewById(R.id.howWorkView);
        termsView = findViewById(R.id.termsView);
        reviewAppView = findViewById(R.id.reviewAppView);
        aboutAppView = findViewById(R.id.aboutAppView);

        openMyResView.setOnClickListener(this);
        editProfileView.setOnClickListener(this);
        plocityView.setOnClickListener(this);
        howWorkView.setOnClickListener(this);
        termsView.setOnClickListener(this);
        aboutAppView.setOnClickListener(this);

        aSwitch.setChecked(getPrefManager().isNearbyNotify());
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                showProgress();
                getApi().changeNearbyNotificationStatus(getPrefManager().getUserId())
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ChangeNotificationModel>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ChangeNotificationModel changeNotificationModel) {
                                hideProgress();
                                if (changeNotificationModel.getStatus() == 1) {
                                    getPrefManager().setNearbyNotify(!getPrefManager().isNearbyNotify());

                                } else {
                                    toast("خطأ برجاء المحاولة في وقت لاحق");
                                    aSwitch.setChecked(!isChecked);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                toast(e.getMessage());
                                hideProgress();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("الإعدادات");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.openMyResView:
                openActivity(new Intent(this, MyResActivity.class));
                break;
            case R.id.editProfileView:
                openActivity(new Intent(this, EditProfileActivity.class));
                break;
            case R.id.plocityView:
                openActivity(new Intent(this, PlociyActivity.class));
                break;
            case R.id.howWorkView:
                openActivity(new Intent(this, VerficationActivity.class));
                break;
            case R.id.termsView:
                openActivity(new Intent(this, TermsActivity.class));
                break;
            case R.id.reviewAppView:
                Common.reviewApp(this);
                break;
            case R.id.aboutAppView:
                openActivity(new Intent(this, AboutAppActivity.class));
                break;
        }
    }

    private void reviewApp() {

    }
}
