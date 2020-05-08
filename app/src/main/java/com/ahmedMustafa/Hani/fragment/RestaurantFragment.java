package com.ahmedMustafa.Hani.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.AllResActivity;
import com.ahmedMustafa.Hani.adapter.NearbyResAdapter;
import com.ahmedMustafa.Hani.model.NearbyRestaurantModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.GPSTracker;
import com.ahmedMustafa.Hani.utils.base.BaseFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RestaurantFragment extends BaseFragment {

    @Inject
    NearbyResAdapter nearbyAdapter;
    @Inject
    NearbyResAdapter activeAdapter;
   // private Button viewAllBut;
    private RecyclerView nearbyRecycler; //activeRecycler;
    private Toolbar toolbar;
    GPSTracker gpsTracker;
   // private View search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant, container, false);
        bind(v);
        init();
        onClick();

        return v;
    }

    private void bind(View v) {

      //  search = v.findViewById(R.id.search);
        //viewAllBut = v.findViewById(R.id.viewAllBut);
        nearbyRecycler = v.findViewById(R.id.nearbyRecycler);
       // activeRecycler = v.findViewById(R.id.activRecycler);
        setDataFrame(v.findViewById(R.id.dataLayout), v.findViewById(R.id.layout));
    //    search.setVisibility(View.GONE);
       // ((TextView) v.findViewById(R.id.nearbyText)).setText("مطاعم قريبه مني");
     //   ((TextView) v.findViewById(R.id.activeText)).setText("مطاعم نشطه");
    //    toolbar = v.findViewById(R.id.toolbar);
     //   ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("المطاعم");
    }

    private void init() {
        activeAdapter.setType(Constant.RES);
        nearbyAdapter.setType(Constant.RES);
        nearbyRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        nearbyRecycler.setAdapter(nearbyAdapter);
    //    activeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    //    nearbyRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
       // activeRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

    }

    private void onClick() {
/*
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllResActivity.class);
                i.putExtra("res", new Gson().toJson(list));
                i.putExtra("lat", gpsTracker.getLat());
                i.putExtra("lng", gpsTracker.getLon());
                i.putExtra("type", Constant.RES);
                i.putExtra("typee", "s");
                openActivity(i);
            }
        });
        viewAllBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AllResActivity.class);
                i.putExtra("res", new Gson().toJson(list));
                i.putExtra("lat", gpsTracker.getLat());
                i.putExtra("type", Constant.RES);
                i.putExtra("lng", gpsTracker.getLon());
                openActivity(i);
            }
        });*/
    }

    private List<NearbyRestaurantModel.Restaurant> list;

    @Override
    protected void loadData() {
        super.loadData();
        gpsTracker = new GPSTracker(getActivity());
        if (gpsTracker.isCanGetLocation()) {
            final Location location = new Location("MLocation");
            location.setLatitude(gpsTracker.getLat());
            location.setLongitude(gpsTracker.getLon());
            nearbyAdapter.setmLocation(location);
            activeAdapter.setmLocation(location);
            if(gpsTracker.getLon() != 0.0){
                getPrefManager().setLng(gpsTracker.getLon()+"");
                getPrefManager().setLat(gpsTracker.getLat()+"");
            }


            getApi().getNearbyRestaurant(gpsTracker.getLat() + "", gpsTracker.getLon() + "", "restaurant")
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<NearbyRestaurantModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(NearbyRestaurantModel nearbyRestaurantModel) {
                            if (nearbyRestaurantModel.getStatus() == 1) {
                           //     search.setVisibility(View.VISIBLE);
                                list = nearbyRestaurantModel.getRestaurants();
                                List<NearbyRestaurantModel.Restaurant> activeList = new ArrayList<>();
                                List<NearbyRestaurantModel.Restaurant> nearbyList = new ArrayList<>();
                                for (NearbyRestaurantModel.Restaurant item : nearbyRestaurantModel.getRestaurants()) {
                                    if (item.getOpeningHours() == null || item.getOpeningHours().getOpenNow() == null || !item.getOpeningHours().getOpenNow())
                                        nearbyList.add(item);
                                    else activeList.add(item);
                                }
                                if (nearbyList.size() > 3) {
                                    nearbyAdapter.setSize(3);
                                }
                                nearbyAdapter.setList(nearbyList);
                                activeAdapter.setList(activeList);
                                nearbyRecycler.setAdapter(nearbyAdapter);
                           //     activeRecycler.setAdapter(activeAdapter);
                                dataFrame.setVisible(LAYOUT);
                            } else {
                                dataFrame.setVisible(ERROR);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            dataFrame.setVisible(ERROR);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            isDataLoaded = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("تحديد الموقع")
                    .setMessage("يجب تحديد الموقع الخاص بك أولا")
                    .setPositiveButton("تحديد الموقع", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().onBackPressed();
                        }
                    }).create().show();
        }
    }

    public static RestaurantFragment newInstance() {
        RestaurantFragment fragment = new RestaurantFragment();
        return fragment;
    }

}
