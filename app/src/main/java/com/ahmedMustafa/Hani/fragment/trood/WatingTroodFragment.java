package com.ahmedMustafa.Hani.fragment.trood;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.adapter.RestaurantOrderAdapter;
import com.ahmedMustafa.Hani.adapter.TroodOrderAdapter;
import com.ahmedMustafa.Hani.adapter.TroodPriceAdapter;
import com.ahmedMustafa.Hani.model.TroodDetailsModel;
import com.ahmedMustafa.Hani.model.WatingTroodModel;
import com.ahmedMustafa.Hani.utils.CustomApi;
import com.ahmedMustafa.Hani.utils.PrefManager;
import com.ahmedMustafa.Hani.utils.base.BaseFragment;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WatingTroodFragment extends BaseFragment {

    private RecyclerView recyclerView;
    @Inject
    TroodPriceAdapter adapter;
    private String id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_wating_trood, container, false);
        bind(view);
        init();
        return view;
    }

    private void bind(View v) {
        recyclerView = v.findViewById(R.id.recycler);
        setDataFrame(v.findViewById(R.id.dataLayout), recyclerView);
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
    }

    @Override
    protected void loadData() {
        super.loadData();
        getApi().getCompany(id, getPrefManager().getUserId())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<TroodDetailsModel>(new CustomApi.CallApi<TroodDetailsModel>() {
                    @Override
                    public void onResponse(TroodDetailsModel response) {
                        if (response.getStatus() == 1){
                            if (response.getCompanyPricesList().size()>0){
                                dataFrame.setVisible(LAYOUT);
                                adapter.setList(response.getCompanyPricesList());
                                recyclerView.setAdapter(adapter);
                            }else {
                                dataFrame.setVisible(NO_ITEM);
                            }
                        }else {
                            dataFrame.setVisible(NO_ITEM);
                        }
                    }

                    @Override
                    public void onError(String msgError) {
                        dataFrame.setVisible(ERROR);
                    }
                },"ff"));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            id = arguments.getString("id");
        }
    }

    public static WatingTroodFragment newInstance(String id) {
        WatingTroodFragment fragment = new WatingTroodFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }
}
