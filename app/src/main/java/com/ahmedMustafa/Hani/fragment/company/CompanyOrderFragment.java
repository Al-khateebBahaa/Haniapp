package com.ahmedMustafa.Hani.fragment.company;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.RadioButton;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.MainActivity;
import com.ahmedMustafa.Hani.activity.settingPages.MyResActivity;
import com.ahmedMustafa.Hani.adapter.MyComapnyOrderAdapter;
import com.ahmedMustafa.Hani.adapter.MyOrderAdapter;
import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.model.TroodAsCompanyModel;
import com.ahmedMustafa.Hani.utils.CustomApi;
import com.ahmedMustafa.Hani.utils.DataFrame;
import com.ahmedMustafa.Hani.utils.NetworkReceiver;
import com.ahmedMustafa.Hani.utils.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CompanyOrderFragment extends BaseFragment implements MyOrderAdapter.Callback {

    @Inject
    MyComapnyOrderAdapter adapter;
    private ExpandableListView expandableListViewOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_company, container, false);

        bind(view);
        onClick();

        return view;
    }

    @Override
    protected void loadData() {
        super.loadData();
        getApi().getOrdersAsTroodCompany(getPrefManager().getUserId())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<TroodAsCompanyModel>(new CustomApi.CallApi<TroodAsCompanyModel>() {
                    @Override
                    public void onResponse(TroodAsCompanyModel response) {
                        if (response.getStatus() == 1) {

                            if (response.getMyOrders().size() > 0) {
                                HashMap<Integer, List<TroodAsCompanyModel.MyOrder>> map = new HashMap<>();
                                List<TroodAsCompanyModel.MyOrder> newList = new ArrayList<>();
                                List<TroodAsCompanyModel.MyOrder> list = new ArrayList<>();
                                for (TroodAsCompanyModel.MyOrder item : response.getMyOrders()) {
                                    if (item.getStatus() == 0) {
                                        newList.add(item);
                                    } else {
                                        list.add(item);
                                    }
                                }
                                map.put(0, newList);
                                map.put(1, list);
                                adapter.setList(map);
                                expandableListViewOrder.setAdapter(adapter);
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

    private void bind(View view) {
        expandableListViewOrder = view.findViewById(R.id.expandableListView);
        setDataFrame(view.findViewById(R.id.dataLayout), expandableListViewOrder);
    }

    private void onClick() {

    }

    @Override
    public void cancelOrder(int potion, String id) {
        showProgress();
        getApi().changeStatusTrood(id, "4")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<PublicModel>(new CustomApi.CallApi<PublicModel>() {
                    @Override
                    public void onResponse(PublicModel response) {
                        hideProgress();
                        if (response.getStatus() == 1) {
                            toast("تم توصيل الطلب بنجاح");
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            openActivity(i);
                        } else {
                            toast(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(String msgError) {
                        hideProgress();
                        toast(msgError);
                    }
                }, "end_tard"));
    }
}
