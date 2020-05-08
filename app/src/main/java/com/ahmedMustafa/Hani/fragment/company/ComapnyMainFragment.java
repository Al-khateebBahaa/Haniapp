package com.ahmedMustafa.Hani.fragment.company;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.adapter.CompanyMainAdapter;
import com.ahmedMustafa.Hani.model.TroodAsCompanyModel;
import com.ahmedMustafa.Hani.utils.CustomApi;
import com.ahmedMustafa.Hani.utils.GPSTracker;
import com.ahmedMustafa.Hani.utils.base.BaseFragment;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ComapnyMainFragment extends BaseFragment {

    private RecyclerView recyclerView;
    @Inject
    CompanyMainAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trood, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        setDataFrame(view.findViewById(R.id.dataLayout), recyclerView);
        return view;
    }

    @Override
    protected void loadData() {
        super.loadData();
   //     dataFrame.setNoItemText("لا توجد طلبات بعد");
        getApi().getOrdersAsTroodCompany(getPrefManager().getUserId())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<TroodAsCompanyModel>(new CustomApi.CallApi<TroodAsCompanyModel>() {
                    @Override
                    public void onResponse(TroodAsCompanyModel response) {
                        if (response.getStatus() == 1) {

                            if (response.getMyOrders().size() > 0) {
                                adapter.setList(response.getMyOrders());
                                recyclerView.setAdapter(adapter);
                                dataFrame.setVisible(LAYOUT);
                            } else {
                                dataFrame.setVisible(NO_ITEM);
                            }
                        } else {
                            dataFrame.setVisible(NO_ITEM);
                        }
                    }

                    @Override
                    public void onError(String msgError) {
                        dataFrame.setVisible(ERROR);
                    }
                }, ""));
    }
}
