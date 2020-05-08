package com.ahmedMustafa.Hani.activity.orders;

import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.adapter.MyOrderAdapter;
import com.ahmedMustafa.Hani.adapter.NearbyResAdapter;
import com.ahmedMustafa.Hani.fragment.MyOrderFragment;
import com.ahmedMustafa.Hani.model.MyOrderModel;
import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.utils.NetworkReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class MyOrders extends AppCompatActivity{


    //    private RadioButton myOrder, drive;
    private RecyclerView expandableListViewOrder, expandableListViewDrive;
    //   private DataFrame dataFrameOrder, dataFrameDrive;
    @Inject
    NearbyResAdapter adapter;
    @Inject
    MyOrderAdapter adapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        adapter = new NearbyResAdapter(this);

        bind();
        onClick();

    }

    private boolean isDataLoaded, isOpenReciver;
    private NetworkReceiver receiver;


    @Override
    public void onPause() {
        super.onPause();
        if (isOpenReciver && receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    private void bind() {
        // myOrder = view.findViewById(R.id.myOrder);
        //  drive = view.findViewById(R.id.drive);
        expandableListViewOrder = findViewById(R.id.expandableListView1);
        expandableListViewDrive = findViewById(R.id.expandableListView2);
        //  dataFrameOrder = view.findViewById(R.id.dataLayout1);
        //  dataFrameDrive = view.findViewById(R.id.dataLayout2);
        //   dataFrameOrder.setLayout(expandableListViewOrder);
        //   dataFrameDrive.setLayout(expandableListViewDrive);
        // myOrder.setChecked(true);
        //    drive.setChecked(false);
        // adapter2.setType(1);
        // viewMyOrder(true);

        expandableListViewOrder.setAdapter(adapter);
        expandableListViewDrive.setAdapter(adapter);

    }

    private void onClick() {
        /*
        myOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myOrder.setBackground(getResources().getDrawable(R.drawable.btton_order));
                    myOrder.setTextColor(getResources().getColor(R.color.textClolor));

                    drive.setBackground(null);
                    drive.setTextColor(getResources().getColor(R.color.white));

                    viewMyOrder(true);
                }
            }
        });

        drive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewMyOrder(false);
                    myOrder.setBackground(null);
                    myOrder.setTextColor(getResources().getColor(R.color.white));

                    drive.setBackground(getResources().getDrawable(R.drawable.btton_order));
                    drive.setTextColor(getResources().getColor(R.color.textClolor));
                }
            }
        });*/
    }

    private void viewMyOrder(boolean isViewOrder) {
        /*
        if (isViewOrder) {
          //  dataFrameOrder.setVisibility(View.VISIBLE);
            dataFrameDrive.setVisibility(View.GONE);
        } else {
            dataFrameOrder.setVisibility(View.GONE);
            dataFrameDrive.setVisibility(View.VISIBLE);
        }*/
    }

    public static MyOrderFragment newInstance() {
        MyOrderFragment fragment = new MyOrderFragment();
        return fragment;
    }
    
}
