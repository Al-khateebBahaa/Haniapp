package com.ahmedMustafa.Hani.fragment.restaurant;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.adapter.RestaurantOrderAdapter;
import com.ahmedMustafa.Hani.model.RestaurantOrdersModel;
import com.ahmedMustafa.Hani.utils.base.BaseFragment;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WatingOrderFragment extends BaseFragment {

    private String id;
    private RecyclerView recyclerView;
    @Inject
    RestaurantOrderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wating_order, container, false);
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
        Log.e("res_id", id);
        if (getPrefManager().isLogin()) {
            getApi().getRestaurantOrders(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<RestaurantOrdersModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(RestaurantOrdersModel restaurantOrdersModel) {
                            if (restaurantOrdersModel.getOrders().size() > 0) {
                                adapter.setList(restaurantOrdersModel.getOrders());
                                recyclerView.setAdapter(adapter);
                                dataFrame.setVisible(LAYOUT);
                            } else {
                                dataFrame.setVisible(NO_ITEM);
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
            dataFrame.setVisible(NO_ITEM);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            id = arguments.getString("id");
        }
    }

    public static WatingOrderFragment newInsanse(String resID) {
        WatingOrderFragment fragment = new WatingOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", resID);
        fragment.setArguments(bundle);
        return fragment;
    }

}
