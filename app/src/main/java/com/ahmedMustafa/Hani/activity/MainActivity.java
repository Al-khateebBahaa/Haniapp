package com.ahmedMustafa.Hani.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedMustafa.Hani.BuildConfig;
import com.ahmedMustafa.Hani.MainActivityFragment;
import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.auth.LoginActivity;
import com.ahmedMustafa.Hani.activity.auth.LogoutBottomSheet;
import com.ahmedMustafa.Hani.fragment.MyOrderFragment;
import com.ahmedMustafa.Hani.fragment.NotifyFragment;
import com.ahmedMustafa.Hani.fragment.ProfileFragment;
import com.ahmedMustafa.Hani.fragment.RestaurantFragment;
import com.ahmedMustafa.Hani.fragment.StoreFragment;
import com.ahmedMustafa.Hani.fragment.TroodFragment;
import com.ahmedMustafa.Hani.fragment.company.ComapnyMainFragment;
import com.ahmedMustafa.Hani.fragment.company.CompanyOrderFragment;
import com.ahmedMustafa.Hani.utils.GPSTracker;
import com.ahmedMustafa.Hani.utils.GetCurrntLocation;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector, BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private TabLayout tabLayout;
    private GPSTracker gpsTracker;
    private final static int REQUEST_LOCATION = 5;
    private GetCurrntLocation mLocation;
    private int icons[];
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocation = new GetCurrntLocation(this);

        Log.e("userId", getPrefManager().getUserId());
        Log.e("newLocation", mLocation.getLat() + "");
        bind();
        initTabLayout();

        showMapsPermission();


    }

    private void initTabLayout() {
        tabLayout = findViewById(R.id.navigationView);
        int companyIcons[] = {R.drawable.ic_user, R.drawable.ic_delivery, R.drawable.ic_shop, R.drawable.ic_restorance, R.drawable.ic_home};
        String companyTitle[] = {"أنا", "الطرود", "المتاجر", "المطاعم", "الرئيسية"};
        icons = new int[]{R.mipmap.my_orders, R.mipmap.person, R.mipmap.bell, R.mipmap.packege, R.mipmap.shop, R.mipmap.order2,};
        //  String[] title = new String[]{"طلباتي", "أنا", "إشعارات", "طرود", "متاجر", "مطاعم"};
        if (getPrefManager().isLogin() && getPrefManager().getUserType() != 1) {
            for (int x = 0; x < icons.length; x++) {
                tabLayout.addTab(tabLayout.newTab().setIcon(icons[x]).setText(companyTitle[x]));
            }
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.secondary_color);
                    tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.OVERLAY);

                    switch (tab.getPosition()) {
                        case 0:
                            if (getPrefManager().isLogin()) {
                                if (type != PROFILE) {
                                    openFragment(ProfileFragment.newInstance(), PROFILE);
                                }

                            } else {
                                openActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                            break;
                            /*
                        case 5:
                            if (type != MY_ORDER) {
                                openFragment(MyOrderFragment.newInstance(), MY_ORDER);

                            }
                            if (getPrefManager().isLogin()) {

                            } else {
                                openActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                            break;
                        case 4:
                            if (getPrefManager().isLogin()) {
                                if (type != NOTIFY) {
                                    openFragment(NotifyFragment.newInstance(), NOTIFY);
                                }
                            } else {
                                openActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                            break;*/
                        case 1:
                            if (type != TROOD) {
                                openFragment(TroodFragment.newInstance(), TROOD);
                            }
                            break;
                        case 2:
                            if (type != STORE) {
                                openFragment(StoreFragment.newInstance(), STORE);
                            }
                            break;
                        case 3:
                            if (type != RES) {

                                openFragment(RestaurantFragment.newInstance(), RES);
                            }
                            break;

                        case 4:
                            if (type != Main)
                                openFragment(MainActivityFragment.newInstance(), Main);

                            break;
                    }


                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, MainActivityFragment.newInstance()).commit();
        } else {
            for (int x = 0; x < companyIcons.length; x++) {
                tabLayout.addTab(tabLayout.newTab().setIcon(companyIcons[x]).setText(companyTitle[x]));
            }
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition()) {

                        case 4:
                            if (type != Main)
                                openFragment(MainActivityFragment.newInstance(), Main);

                            break;

                        case 3:
                            openFragment(RestaurantFragment.newInstance(), RES);
                            //   openFragment(new ComapnyMainFragment(), 1);
                            break;

                        case 2:
                            openFragment(StoreFragment.newInstance(), STORE);
                            //   openFragment(NotifyFragment.newInstance(), 2);
                            break;
                        case 1:
                            openFragment(TroodFragment.newInstance(), TROOD);
                            //  openFragment(new CompanyOrderFragment(), 5);
                            break;
                        case 0:
                            openFragment(ProfileFragment.newInstance(), 8);
                            showDeliveredDone();
                            break;
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MainActivityFragment()).commit();
        }


        tabLayout.getTabAt((getPrefManager().isLogin() && getPrefManager().getUserType() != 1) ? 5 : 4).select();
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.textClolor));
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                return true;
            }
        }
        return false;
    }

    private void initPermission() {
        if (!hasPermission()) {
            gpsTracker = new GPSTracker(this);
            if (!gpsTracker.isCanGetLocation()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("تحديد الموقع")
                        .setCancelable(false)
                        .setMessage("يجب تحديد الموقع الخاص بك أولا")
                        .setPositiveButton("تحديد الموقع", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onBackPressed();
                            }
                        }).create().show();
            } else {

            }
        }
    }

    private void bind() {

        /*
        setSupportActionBar((Toolbar) findViewById(R.id.main_activitiy_toolbar));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mToolbarTitle = findViewById(R.id.toolbar_title);
*/
        //navigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
            mBottomSheetDialog.setContentView(R.layout.logout_bottom_sheet);
            mBottomSheetDialog.show();

        }

        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                if (getPrefManager().isLogin()) {
                    if (type != PROFILE) {
                        openFragment(ProfileFragment.newInstance(), PROFILE);

                    }
                    return true;
                } else {
                    openActivity(new Intent(this, LoginActivity.class));
                    return false;
                }


            case R.id.myOrder:
                if (getPrefManager().isLogin()) {
                    if (type != MY_ORDER) {
                        openFragment(MyOrderFragment.newInstance(), MY_ORDER);

                    }
                    return true;
                } else {
                    openActivity(new Intent(this, LoginActivity.class));
                    return false;
                }

            case R.id.res:
                if (type != RES) {

                    openFragment(RestaurantFragment.newInstance(), RES);
                }
                return true;
            case R.id.notify:
                if (getPrefManager().isLogin()) {
                    if (type != NOTIFY) {
                        openFragment(NotifyFragment.newInstance(), NOTIFY);

                    }
                    return true;
                } else {
                    openActivity(new Intent(this, LoginActivity.class));
                    return false;
                }

           /* case R.id.store:
                if (type != STORE) {
                    openFragment(StoreFragment.newInstance(), STORE);
                }
                return true;
                */
            case R.id.trood:
                if (type != TROOD) {
                    openFragment(TroodFragment.newInstance(), TROOD);
                }
                return true;
        }
        return false;
    }

    private final int PROFILE = 0;
    private final int MY_ORDER = 1;
    private final int RES = 2;
    private final int NOTIFY = 3;
    private final int STORE = 5;
    private final int TROOD = 6;
    private final int Main = 7;

    private int type = 2;

    private void openFragment(Fragment fragment, int type) {
        this.type = type;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }


    private void showMapsPermission() {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(R.layout.maps_permission_bottom_sheet);

        //  ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.maps_permission_bottom_sheet, null);

        //Button button = (Button) constraintLayout.getViewById(R.id.inti_permission);

        mBottomSheetDialog.setCancelable(false);

        try {
            mBottomSheetDialog.findViewById(R.id.inti_permission).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                    initPermission();
                }
            });
        } catch (NullPointerException e) {
            e.getMessage();
            initPermission();
        }

        try {

            mBottomSheetDialog.findViewById(R.id.cancel_permission).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                }
            });
        } catch (NullPointerException e) {
            e.getMessage();
            mBottomSheetDialog.dismiss();
        }


        mBottomSheetDialog.show();

    }


    private void showDeliveredDone() {

        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);

        mBottomSheetDialog.setContentView(R.layout.delivered_done);
        mBottomSheetDialog.show();

    }


}
