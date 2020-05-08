package com.ahmedMustafa.Hani.fragment;


import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.adapter.TroodAdapter;
import com.ahmedMustafa.Hani.model.TroodsModel;
import com.ahmedMustafa.Hani.utils.CustomApi;
import com.ahmedMustafa.Hani.utils.GPSTracker;
import com.ahmedMustafa.Hani.utils.GetCurrntLocation;

import com.ahmedMustafa.Hani.utils.base.BaseFragment;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TroodFragment extends BaseFragment {

    private RecyclerView recyclerView;

    @Inject
    TroodAdapter adapter;
    @Inject
    GPSTracker tracker;


    public static TroodFragment newInstance() {
        TroodFragment fragment = new TroodFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trood, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
       // setDataFrame(view.findViewById(R.id.dataLayout), recyclerView);
        return view;
    }

    @Override
    protected void loadData() {
        super.loadData();
        getApi().getAllTroodsCompanies()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<TroodsModel>(new CustomApi.CallApi<TroodsModel>() {
                    @Override
                    public void onResponse(TroodsModel response) {
                        if (response.getStatus() == 1) {
                            if (response.getCompanies().size() > 0) {
                                Location location = new Location("A");
                                location.setLatitude(tracker.getLat());
                                location.setLongitude(tracker.getLon());
                                adapter.setmLocation(location);
                                adapter.setList(response.getCompanies());
                                recyclerView.setAdapter(adapter);
                                dataFrame.setVisible(LAYOUT);
                            } else {
                                dataFrame.setVisible(NO_ITEM);
                            }
                        } else {
                            dataFrame.setVisible(ERROR);
                        }
                    }

                    @Override
                    public void onError(String msgError) {
                        dataFrame.setVisible(ERROR);
                    }
                }, "ALL_TROOD"));
    }
}
