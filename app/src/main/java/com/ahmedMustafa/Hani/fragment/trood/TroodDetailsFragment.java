package com.ahmedMustafa.Hani.fragment.trood;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.orders.NewOrderActivity;
import com.ahmedMustafa.Hani.activity.trood.MakeTroodOrderActivity;
import com.ahmedMustafa.Hani.model.AllAgetntForTroodModel;
import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.model.RestaurantAgentModel;
import com.ahmedMustafa.Hani.model.SingupAsAgentModel;
import com.ahmedMustafa.Hani.model.TroodDetailsModel;
import com.ahmedMustafa.Hani.utils.CustomApi;
import com.ahmedMustafa.Hani.utils.base.BaseFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TroodDetailsFragment extends BaseFragment implements OnMapReadyCallback {

    private String id, name, phone;
    private double lat, lng;
    private TextView agentCount, agentBut, resName, resLocation;
    private View orderBut;
    private GoogleMap mMap;
    private boolean isAgent;
    private int count;
    private MapView mapView;
    private View but;
    private ImageView icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trood_details, container, false);
        bind(view);
        onClick();
        mapView.onCreate(savedInstanceState);
        return view;
    }

    private void bind(View v) {
        but = v.findViewById(R.id.but);
        mapView = v.findViewById(R.id.map);
        agentBut = v.findViewById(R.id.agentBut);
        agentCount = v.findViewById(R.id.agentCount);
        resName = v.findViewById(R.id.resName);
        resLocation = v.findViewById(R.id.resLocation);
        orderBut = v.findViewById(R.id.orderBut);
        icon = v.findViewById(R.id.icon);
        resName.setText(name);
        resLocation.setText(phone);
        mapView.getMapAsync(this);
        setDataFrame(v.findViewById(R.id.dataLayout), v.findViewById(R.id.layout));

    }

    private void viewIcon(int type){
        icon.setImageResource(type == 1 ?R.drawable.ic_check:R.drawable.ic_un_check);
    }

    private void onClick() {

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPrefManager().isLogin()) {
                    if (count == 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("خطأ");
                        builder.setMessage("لا يمكن وضع طلبك في هذا المتجر لعدم توفر العدد الكافي من المناديب لخدمتك لكننا نعمل علي توسيع شبكتنا ، كن قريبا");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.create().show();
                    } else {
                        Intent i = new Intent(getActivity(), MakeTroodOrderActivity.class);
                        i.putExtra("id", id + "");
                        openActivity(i);
                    }

                } else {
                    noLogin(v);
                }
            }
        });


        orderBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPrefManager().isLogin()) {
                    if (count == 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("خطأ");
                        builder.setMessage("لا يمكن وضع طلبك في هذا المتجر لعدم توفر العدد الكافي من المناديب لخدمتك لكننا نعمل علي توسيع شبكتنا ، كن قريبا");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.create().show();
                    } else {
                        Intent i = new Intent(getActivity(), MakeTroodOrderActivity.class);
                        i.putExtra("id", id + "");
                        openActivity(i);
                    }

                } else {
                    noLogin(v);
                }
            }
        });

        agentBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!getPrefManager().isLogin()) {
                    noLogin(v);
                } else {
                    if (isConnected()) {
                        if (isAgent) {
                            //cancel
                            cancelMeAsAgent();

                        } else {
                            //add
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("إشترك كمندوب");
                            builder.setMessage("يسعدنا إشتراكك معنا كمندوب , هل تريد حقا الإشتراك في أسطول مناديب هاني ؟ الرجاء الضغط علي خيار نعم وسوف نقوم بإخبارك بكل الطلبات القريبه من هذا المتجر");
                            builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    singAsAgent();
                                }
                            });
                            builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.create().show();

                        }
                    } else {
                        toast(R.string.noNetwork);
                    }
                }
            }
        });
    }

    private void singAsAgent() {
        showProgress();
        getApi().singUpAsTroodAgent(id, getPrefManager().getUserId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PublicModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PublicModel singupAsAgentModel) {
                        if (singupAsAgentModel.getStatus() == 1) {
                            toast("تم تسجيلك كمندوب بنجاح");
                            count += 1;
                            agentCount.setText(count + "مندوبين متواجدين");
                            agentBut.setText("إلغاء التسجيل كمندوب");
                            isAgent = true;
                        } else {
                            toast(R.string.error_msg);
                        }
                        hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        toast(R.string.error_msg);
                        hideProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void cancelMeAsAgent() {
        showProgress();
        getApi().removeMeAsTroodAgent(id, getPrefManager().getUserId())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PublicModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PublicModel model) {
                        hideProgress();
                        if (model.getStatus() == 1) {
                            toast("تم إلغاء تسجيلك كمندوب بنجاح");
                            count -= 1;
                            agentCount.setText(count == 0 ? "لا يوجد مندوبين بعد" : count + "مندوبين متواجدين");
                            agentBut.setText("التسجيل كمندوب");
                            isAgent = false;
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

    @Override
    protected void loadData() {
        super.loadData();
        getApi().getAllAgentForTroodCompany(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<AllAgetntForTroodModel>(new CustomApi.CallApi<AllAgetntForTroodModel>() {
                    @Override
                    public void onResponse(AllAgetntForTroodModel response) {
                        count = response.getAgents().size();
                        if (count > 0) {
                            agentCount.setText(count + "مندوبين متواجدين");
                        } else {
                            agentCount.setText("لا يوجد مندوبين بعد");
                        }
                        if (getPrefManager().isLogin()) {
                            for (AllAgetntForTroodModel.Agent item : response.getAgents()) {
                                String id = item.getId() + "";
                                if (id.equals(getPrefManager().getUserId())) {
                                    isAgent = true;
                                }
                            }
                        }
                        agentBut.setText(isAgent ? "إلغاء التسجيل كمندوب" : "التسجيل كمندوب");
                        dataFrame.setVisible(LAYOUT);
                    }

                    @Override
                    public void onError(String msgError) {

                    }
                }, "ALLAGENT"));
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            id = arguments.getString("id");
            name = arguments.getString("name");
            lat = arguments.getDouble("lat");
            lng = arguments.getDouble("lng");
            phone = arguments.getString("phone");
        }
    }

    public static TroodDetailsFragment newInstance(String resID, String name, double lat, double lng, String phone) {
        TroodDetailsFragment fragment = new TroodDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", resID);
        bundle.putString("name", name);
        bundle.putString("phone", phone);
        bundle.putDouble("lat", lat);
        bundle.putDouble("lng", lng);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.e("map", "onMapReady");
        LatLng sydney = new LatLng(lat, lng);
        MarkerOptions marker = new MarkerOptions().position(sydney);
        Marker marker1 = mMap.addMarker(marker);
        marker1.showInfoWindow();
        marker1.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.shop));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(sydney)     // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
