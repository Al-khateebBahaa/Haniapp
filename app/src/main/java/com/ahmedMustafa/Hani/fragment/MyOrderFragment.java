
package com.ahmedMustafa.Hani.fragment;


import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.RadioButton;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.adapter.MyOrderAdapter;
import com.ahmedMustafa.Hani.model.MyOrderModel;
import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.utils.DataFrame;
import com.ahmedMustafa.Hani.utils.NetworkReceiver;
import com.ahmedMustafa.Hani.utils.base.BaseFragment;

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

public class MyOrderFragment extends BaseFragment implements MyOrderAdapter.Callback {

//    private RadioButton myOrder, drive;
    private ExpandableListView expandableListViewOrder, expandableListViewDrive;
 //   private DataFrame dataFrameOrder, dataFrameDrive;
    @Inject
    MyOrderAdapter adapter;
    @Inject
    MyOrderAdapter adapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);

        bind(view);
        onClick();
        return view;
    }

    private boolean isDataLoaded, isOpenReciver;
    private NetworkReceiver receiver;

    @Override
    public void onResume() {
        super.onResume();
        if (!isConnected() && !isDataLoaded) {
     //       dataFrameOrder.setVisible(NO_INTERNET);
     //       dataFrameDrive.setVisible(NO_INTERNET);
            receiver = new NetworkReceiver(new NetworkReceiver.InternetListener() {
                @Override
                public void networkConnected() {
         //           dataFrameOrder.setVisible(PROGRESS);
          //          dataFrameDrive.setVisible(PROGRESS);
                    getActivity().unregisterReceiver(receiver);
                    isOpenReciver = true;
                    IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
                    getActivity().registerReceiver(receiver, filter);
                    getRquest();

                }
            });
        } else if (!isDataLoaded && isConnected()) {
            getRquest();
        }
    }

    private void getRquest() {
        isDataLoaded = true;
        Observable<MyOrderModel> order = getApi().getMyOrder(getPrefManager().getUserId())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Observable<MyOrderModel> agent = getApi().getMyOrderAsAgent(getPrefManager().getUserId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        Observable.zip(order, agent, new BiFunction<MyOrderModel, MyOrderModel, List<MyOrderModel>>() {
            @Override
            public List<MyOrderModel> apply(MyOrderModel myOrderModel, MyOrderModel myOrderModel2) throws Exception {
                List<MyOrderModel> list = new ArrayList<>();
                list.add(myOrderModel);
                list.add(myOrderModel2);
                return list;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<MyOrderModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MyOrderModel> myOrderModels) {
                        MyOrderModel orders = myOrderModels.get(0);
                        MyOrderModel agnets = myOrderModels.get(1);

                        if (orders.getStatus() == 1) {

                            HashMap<Integer, List<MyOrderModel.Order>> list = new HashMap<>();
                            list.put(0, orders.getActiveOrders());
                            list.put(1, orders.getFinishedOrders());
                            adapter.setList(list);
                            expandableListViewOrder.setAdapter(adapter);
                    //        dataFrameOrder.setVisible(LAYOUT);
                        } else {
                    //        dataFrameOrder.setVisible(ERROR);
                        }

                        if (agnets.getStatus() == 1) {

                            HashMap<Integer, List<MyOrderModel.Order>> list = new HashMap<>();
                            list.put(0, agnets.getActiveOrders());
                            list.put(1, agnets.getFinishedOrders());
                            adapter2.setList(list);
                            expandableListViewDrive.setAdapter(adapter2);
                        //    dataFrameDrive.setVisible(LAYOUT);
                        } else {
                       //     dataFrameDrive.setVisible(ERROR);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                   //     dataFrameDrive.setVisible(ERROR);
                    //    dataFrameOrder.setVisible(ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isOpenReciver && receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
    }

    private void bind(View view) {
       // myOrder = view.findViewById(R.id.myOrder);
      //  drive = view.findViewById(R.id.drive);
        expandableListViewOrder = view.findViewById(R.id.expandableListView1);
        expandableListViewDrive = view.findViewById(R.id.expandableListView2);
      //  dataFrameOrder = view.findViewById(R.id.dataLayout1);
      //  dataFrameDrive = view.findViewById(R.id.dataLayout2);
     //   dataFrameOrder.setLayout(expandableListViewOrder);
     //   dataFrameDrive.setLayout(expandableListViewDrive);
       // myOrder.setChecked(true);
    //    drive.setChecked(false);
        adapter2.setType(1);
        viewMyOrder(true);
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

    @Override
    public void cancelOrder(final int position, String id) {
        showProgress();
        getApi().deleteOrder(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PublicModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PublicModel model) {
                hideProgress();
                if (model.getStatus() == 1) {
                    toast("تم حذف الطلب بنجاح");
                    adapter.removeChaild(position);
                } else {
                    toast(model.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                hideProgress();
                toast(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
