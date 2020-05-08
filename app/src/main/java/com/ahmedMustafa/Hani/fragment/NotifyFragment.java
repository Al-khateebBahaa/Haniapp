package com.ahmedMustafa.Hani.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.orders.ChatActivity;
import com.ahmedMustafa.Hani.activity.orders.RequestOrderConnectionActivity;
import com.ahmedMustafa.Hani.adapter.NotificationAdapter;
import com.ahmedMustafa.Hani.model.AssigmentInfoModel;
import com.ahmedMustafa.Hani.model.ChatModel;
import com.ahmedMustafa.Hani.model.DeliveryRequestModel;
import com.ahmedMustafa.Hani.model.NotificationModel;
import com.ahmedMustafa.Hani.model.PopupModel;
import com.ahmedMustafa.Hani.model.PublicModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.CustomApi;
import com.ahmedMustafa.Hani.utils.GPSTracker;
import com.ahmedMustafa.Hani.utils.base.BaseFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NotifyFragment extends BaseFragment implements NotificationAdapter.Callback {

    @Inject
    NotificationAdapter adapter;
    @Inject
    GPSTracker tracker;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notify, container, false);
        bind(v);
        Log.e("userID", getPrefManager().getUserId());
        return v;
    }

    private void bind(View v) {
        recyclerView = v.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        toolbar = v.findViewById(R.id.toolbar);
        setDataFrame(v.findViewById(R.id.dataLayout), recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("التنبيهات");
    }

    @Override
    protected void loadData() {
        super.loadData();
        getApi().getAllNotification(getPrefManager().getUserId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NotificationModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NotificationModel notificationModel) {
                        if (notificationModel.getStatus() == 1) {
                            if (notificationModel.getNotifications().size() > 0) {
                                adapter.setList(notificationModel.getNotifications());
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
                    public void onError(Throwable e) {
                        dataFrame.setVisible(ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private final int MAKE_DELIVERY_ORDER = 1;
    private final int YOU_HAVE_DELIVARY_REQUEST = 2;
    private final int OPEN_CHAT = 3;
    private final int MAKE_DELIVERY_TROOD = 100;
    private final int YOU_HAVE_TROOD_REQUEST = 101;
    private final int OPEN_CHAT_TROOD = 110;
    private final int AGENET_DILEVERED_TO_CAMPANY = 102;//
    private final int DELIVIRING_FROM_COMPANY_TO_CLEINT = 103;
    private final int DONE_DELIVERD_TO_CLIENT = 104;
    private final int CAMPANY_REQUEST_AGENT = 105;

    @Override
    public void onItemClick(final int position, final NotificationModel.Notification item) {
        Log.e("oderId", item.getOrderId() + "");
        switch (item.getType()) {
            case MAKE_DELIVERY_ORDER:
                Intent i = new Intent(getActivity(), RequestOrderConnectionActivity.class);
                i.putExtra("id", item.getOrderId() + "");
                openActivity(i);
                break;
            case YOU_HAVE_DELIVARY_REQUEST:
                if (item.getOrder_status() == 0){
                    haveDelivryRequest(position, item);
                }else {
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("id", item.getOrderId() + "");
                    openActivity(intent);
                }
                break;
            case OPEN_CHAT:
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("id", item.getOrderId() + "");
                openActivity(intent);
                break;
            case MAKE_DELIVERY_TROOD:
                Intent intent1 = new Intent(getActivity(), RequestOrderConnectionActivity.class);
                intent1.putExtra("id", item.getOrderId() + "");
                intent1.putExtra("type", 1);
                openActivity(intent1);
                break;
            case YOU_HAVE_TROOD_REQUEST:
                haveTroodRequest(position, item);
                break;
            case OPEN_CHAT_TROOD:
                Intent ii = new Intent(getActivity(), ChatActivity.class);
                ii.putExtra("id", item.getOrderId() + "");
                ii.putExtra("type", 1);
                openActivity(ii);
            case CAMPANY_REQUEST_AGENT:
                campanyRequestAgent(item.getOrderId() + "", item.getToUserId() + "");
        }
    }



    //company
    private void campanyRequestAgent(final String orderId, final String agentID) {
        showProgress();
        getApi().getAssignmentInfo(agentID, orderId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<AssigmentInfoModel>(new CustomApi.CallApi<AssigmentInfoModel>() {
                    @Override
                    public void onResponse(AssigmentInfoModel response) {
                        hideProgress();
                        if (response.getStatus() == 1) {

                            AssigmentInfoModel.AssignmentInfo model = response.getAssignmentInfo();

                            final Dialog dialog = new Dialog(getActivity());
                            dialog.setContentView(R.layout.response_to_agent_request_popup);
                            ImageView imageView = dialog.findViewById(R.id.image);
                            TextView agentName = dialog.findViewById(R.id.agentName);
                            TextView location = dialog.findViewById(R.id.location);
                            TextView price = dialog.findViewById(R.id.price);
                            Log.e("mLocation", tracker.getLon() + "");
                            agentName.setText(model.getAgentName());
                            price.setVisibility(View.GONE);
                            //  price.setText(" تكلفة الوصول : " + item.getDeliveryFee() + " ريال ");
                            Location mLocation = new Location("M_LOCATION");
                            mLocation.setLatitude(tracker.getLat());
                            mLocation.setLongitude(tracker.getLon());
                            Picasso.get().load(Constant.BASE_IMAGE + model.getAgentImage()).into(imageView);
                            Location agentLocation = new Location("AGENT_LOCATION");
                            agentLocation.setLatitude(Double.parseDouble(model.getAgentLat()));
                            agentLocation.setLongitude(Double.parseDouble(model.getAgentLng()));

                            final double d = agentLocation.distanceTo(mLocation) / 1000;
                            final DecimalFormat precision = new DecimalFormat("0.00");
                            location.setText("يبعد عنك مسافة " + precision.format(d) + " KM");
                            dialog.show();
                            dialog.findViewById(R.id.acceptBut).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (isConnected()) {
                                        dialog.dismiss();
                                        confirmAsAgentToRequestCompany(1, agentID, orderId);
                                    } else {
                                        toast(R.string.noNetwork);
                                    }

                                }
                            });
                            dialog.findViewById(R.id.rejectBut).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (isConnected()) {
                                        dialog.dismiss();
                                        confirmAsAgentToRequestCompany(0, agentID, orderId);
                                    } else {
                                        toast(R.string.noNetwork);
                                    }

                                }
                            });

                        } else {
                            toast("خطأ برجاء المحاولة في وقت لاحق");
                        }
                    }

                    @Override
                    public void onError(String msgError) {
                        hideProgress();
                        toast(msgError);
                    }
                }, ""));
    }

    private void confirmAsAgentToRequestCompany(final int type, String agnetId, String orderId) {
        showProgress();
        String confirm = "agent-confirm-company-assignment";
        String reject = "agent-deny-company-assignment";
        getApi().agentConfrmCompanyRequest(type == 1 ? confirm : reject, agnetId, orderId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<PublicModel>(new CustomApi.CallApi<PublicModel>() {
                    @Override
                    public void onResponse(PublicModel response) {
                        hideProgress();
                        if (response.getStatus() == 1) {
                            if (type == 1) {
                                toast("تم الموافقة علي العرض بنجاح");
                            } else {
                                toast("تم الرفض بنجاح");
                            }

                        } else {
                            toast(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(String msgError) {
                        hideProgress();
                        toast(msgError);
                    }
                }, ""));
    }

    //store
    private void haveDelivryRequest(final int position, final NotificationModel.Notification item) {
        showProgress();
        getApi().getPopupModel(item.getOrderId() + "", item.getDeliveryRequestId() + "")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PopupModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PopupModel popupModel) {
                        hideProgress();
                        if (popupModel.getStatus() == 1) {
                            ConfirmAgentRequest(position, popupModel.getOrderDeliveryRequests().get(0), item.getId() + "", item.getDeliveryRequestId() + "");
                        } else {
                            toast("خطأ برجاء المحاولة في وقت لاحق");
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

    private void ConfirmAgentRequest(final int position, final PopupModel.OrderDeliveryRequest item, final String notifyId, final String deliveryId) {
        final Dialog dialog;
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.response_to_agent_request_popup);
        ImageView imageView = dialog.findViewById(R.id.image);
        TextView agentName = dialog.findViewById(R.id.agentName);
        TextView location = dialog.findViewById(R.id.location);
        TextView price = dialog.findViewById(R.id.price);
        Log.e("mLocation", tracker.getLon() + "");
        agentName.setText(item.getName());
        price.setText(" تكلفة الوصول : " + item.getDeliveryFee() + " ريال ");
        Location mLocation = new Location("M_LOCATION");
        mLocation.setLatitude(tracker.getLat());
        mLocation.setLongitude(tracker.getLon());
        Picasso.get().load(Constant.BASE_IMAGE + item.getImage()).into(imageView);
        Location agentLocation = new Location("AGENT_LOCATION");
        agentLocation.setLatitude(Double.parseDouble(item.getAgentLat()));
        agentLocation.setLongitude(Double.parseDouble(item.getAgentLng()));

        final double d = agentLocation.distanceTo(mLocation) / 1000;
        final DecimalFormat precision = new DecimalFormat("0.00");
        location.setText("يبعد عنك مسافة " + precision.format(d) + " KM");

        dialog.findViewById(R.id.acceptBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showProgress();
                getApi().confirmDelievryRequest(item.getOrderId() + "", item.getAgentId() + "")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<PublicModel>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(PublicModel publicModel) {
                                int duration = Integer.parseInt(item.getDeliveryDuration()) / 60;
                                if (publicModel.getStatus() == 1) {

                                    initChat(precision.format(d) + "", item.getDeliveryFee() + "", duration + "", item.getOrderId() + "");
                                } else {
                                    toast(publicModel.getMsg());
                                    initChat(precision.format(d) + "", item.getDeliveryFee() + "", duration + "", item.getOrderId() + "");
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                hideProgress();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
        dialog.findViewById(R.id.rejectBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                rejectReqquest(notifyId, deliveryId, position);
            }
        });
        dialog.show();

    }

    private void rejectReqquest(String notifyId, String deliveryId, final int position) {
        showProgress();
        getApi().rejectDelivaryRequest(notifyId, deliveryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PublicModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PublicModel publicModel) {
                        hideProgress();
                        if (publicModel.getStatus() == 1) {
                            toast("تم رفض عرض المندوب بنجاح");
                            adapter.removeItem(position);
                        } else {
                            toast(publicModel.getMsg());
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

    private DatabaseReference ref;

    private void initChat(final String location, final String price, final String time, final String orderId) {

        ref = FirebaseDatabase.getInstance().getReference().child("chats").child("shops").child(orderId).child("messages");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren()) {
                    long timeMap = System.currentTimeMillis();
                    double lon = tracker.getLon();
                    double lat = tracker.getLat();
                    String msg = lat + "," + lon;
                    ChatModel model = new ChatModel();
                    model.setMessage_type("3");
                    model.setMessage_body(msg);
                    model.setSender_user_id(getPrefManager().getUserId());
                    ref.child(timeMap + "").setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            String msg = "تكلفة الوصول " + price + " ريال " + "\n" +
                                    "التوصيل خلال " + time + " ساعة " + "\n" +
                                    "يبعد عنك مسافة " + location + " KM";
                            long time = System.currentTimeMillis();
                            ChatModel model = new ChatModel();
                            model.setSender_user_id(getPrefManager().getUserId());
                            model.setMessage_body(msg);
                            model.setMessage_type("1");
                            ref.child(time + "").setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    hideProgress();
                                    Intent i = new Intent(getActivity(), ChatActivity.class);
                                    i.putExtra("id", orderId);
                                    openActivity(i);
                                }
                            });
                        }
                    });
                } else {
                    Intent i = new Intent(getActivity(), ChatActivity.class);
                    i.putExtra("id", orderId);
                    openActivity(i);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    // trood

    private void haveTroodRequest(final int position, final NotificationModel.Notification item) {
        showProgress();
        getApi().getDeliveryReqest(item.getFromUserId() + "", item.getOrderId() + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<DeliveryRequestModel>(new CustomApi.CallApi<DeliveryRequestModel>() {
                    @Override
                    public void onResponse(DeliveryRequestModel response) {
                        hideProgress();
                        if (response.getStatus() == 1) {
                            final DeliveryRequestModel.RequestInfo agent = response.getRequestInfo();
                            final Dialog dialog;
                            dialog = new Dialog(getActivity());
                            dialog.setContentView(R.layout.response_to_agent_request_popup);
                            ImageView imageView = dialog.findViewById(R.id.image);
                            TextView agentName = dialog.findViewById(R.id.agentName);
                            TextView location = dialog.findViewById(R.id.location);
                            TextView price = dialog.findViewById(R.id.price);
                            Log.e("mLocation", tracker.getLon() + "");
                            agentName.setText(agent.getAgentName());
                            price.setVisibility(View.GONE);
                            //  price.setText(" تكلفة الوصول : " + item.getDeliveryFee() + " ريال ");
                            Location mLocation = new Location("M_LOCATION");
                            mLocation.setLatitude(tracker.getLat());
                            mLocation.setLongitude(tracker.getLon());
                            Picasso.get().load(Constant.BASE_IMAGE + agent.getAgentImage()).into(imageView);
                            Location agentLocation = new Location("AGENT_LOCATION");
                            agentLocation.setLatitude(Double.parseDouble(agent.getAgentLat()));
                            agentLocation.setLongitude(Double.parseDouble(agent.getAgentLng()));

                            final double d = agentLocation.distanceTo(mLocation) / 1000;
                            final DecimalFormat precision = new DecimalFormat("0.00");
                            location.setText("يبعد عنك مسافة " + precision.format(d) + " KM");

                            dialog.findViewById(R.id.acceptBut).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    actionTardRequest(1, position, agent, precision.format(d));
                                }
                            });
                            dialog.findViewById(R.id.rejectBut).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    actionTardRequest(0, position, agent, precision.format(d));
                                }
                            });
                            dialog.show();
                        } else {
                            toast("error");
                        }
                    }

                    @Override
                    public void onError(String msgError) {
                        hideProgress();
                        toast(msgError);
                    }
                }, "DeliveryRequestModel"));

        /*  showProgress();

         */

    }

    private void actionTardRequest(final int path, final int position, final DeliveryRequestModel.RequestInfo item, final String location) {
        showProgress();
        getApi().userActionTardRequest(path == 1 ? "user-confirm-delivery-request" : "user-deny-delivery-request", item.getAgentId() + "", item.getOrderId() + "")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<PublicModel>(new CustomApi.CallApi<PublicModel>() {
                    @Override
                    public void onResponse(PublicModel response) {
                        hideProgress();
                        if (response.getStatus() == 1) {

                            if (path == 1) {
                                initTroodChat(item.getOrderId() + "", location);
                                if (response.getStatus() == 1)
                                    toast("تم قبول الخدمه بنجاح");

                            } else {
                                adapter.removeItem(position);
                                toast("تم رفض الخدمه");
                            }

                        } else {
                            toast(response.getMsg());
                            if (path == 1) {
                                initTroodChat(item.getOrderId() + "", location);
                            }
                        }
                    }

                    @Override
                    public void onError(String msgError) {
                        toast(msgError);
                        hideProgress();
                        toast(msgError);
                    }
                }, "USER_ACTION"));
    }

    private void initTroodChat(final String orderId, final String location) {
        ref = FirebaseDatabase.getInstance().getReference().child("chats").child("trd").child(orderId).child("messages");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.hasChildren()) {

                    long timeMap = System.currentTimeMillis();
                    double lon = tracker.getLon();
                    double lat = tracker.getLat();
                    String msg = lat + "," + lon;
                    ChatModel model = new ChatModel();
                    model.setMessage_type("3");
                    model.setMessage_body(msg);
                    model.setSender_user_id(getPrefManager().getUserId());
                    ref.child(timeMap + "").setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            String msg = "يبعد عنك مسافة " + location + " KM";
                            long time = System.currentTimeMillis();
                            ChatModel model = new ChatModel();
                            model.setSender_user_id(getPrefManager().getUserId());
                            model.setMessage_body(msg);
                            model.setMessage_type("1");
                            ref.child(time + "").setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    hideProgress();
                                    Intent i = new Intent(getActivity(), ChatActivity.class);
                                    i.putExtra("id", orderId);
                                    i.putExtra("type", 1);
                                    openActivity(i);
                                }
                            });
                        }
                    });
                } else {
                    Intent i = new Intent(getActivity(), ChatActivity.class);
                    i.putExtra("id", orderId + "");
                    i.putExtra("type", 1);
                    openActivity(i);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static NotifyFragment newInstance() {
        NotifyFragment fragment = new NotifyFragment();
        return fragment;
    }
}
