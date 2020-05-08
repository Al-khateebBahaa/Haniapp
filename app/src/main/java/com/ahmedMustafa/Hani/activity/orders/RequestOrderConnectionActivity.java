package com.ahmedMustafa.Hani.activity.orders;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.CommentsActivity;
import com.ahmedMustafa.Hani.model.OrderModel;
import com.ahmedMustafa.Hani.model.TroodOrderDetailsModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.CustomApi;
import com.ahmedMustafa.Hani.utils.base.BaseActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RequestOrderConnectionActivity extends BaseActivity {

    private ImageView backBut, popup;
    private CircleImageView imageView;
    private TextView resName, orderNum, clientName;
    private Button but;
    private OrderModel.Order order;
    private TroodOrderDetailsModel.Order troodOrder;
    private String orderID;
    private int type;
    private final int TROOD = 1;
    private final int STORE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_order_connection);
        type = getIntent().getIntExtra("type", 0);
        bind();
        init();
        onClick();
    }

    private void bind() {
        backBut = findViewById(R.id.backBut);
        resName = findViewById(R.id.resName);
        orderNum = findViewById(R.id.orderNum);
        popup = findViewById(R.id.popup);
        imageView = findViewById(R.id.image);
        clientName = findViewById(R.id.clientName);
        but = findViewById(R.id.but);
        setDataFrame(findViewById(R.id.dataLayout), findViewById(R.id.layout));
    }

    private void init() {

        orderID = getIntent().getStringExtra("id");
        orderNum.setText("رقم الطلب : " + orderID);
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (type == STORE) {
            loadStoreOrder();
        } else {
            loadTroodOrder();
        }
    }

    private void loadStoreOrder() {
        getApi().getOrder(orderID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OrderModel orderModel) {
                        if (orderModel.getStatus() == 1) {
                            order = orderModel.getOrder();
                            clientName.setText(order.getName());
                            Picasso.get().load(Constant.BASE_IMAGE + order.getImage()).into(imageView);
                            resName.setText(order.getRestaurantName());
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
    }

    private void loadTroodOrder() {
        getApi().getTroodOrderDetails(orderID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomApi<TroodOrderDetailsModel>(new CustomApi.CallApi<TroodOrderDetailsModel>() {
                    @Override
                    public void onResponse(TroodOrderDetailsModel response) {
                        if (response.getStatus() == 1) {
                            troodOrder = response.getOrder();
                            clientName.setText(troodOrder.getToAddress());
                            Picasso.get().load(Constant.BASE_IMAGE + troodOrder.getUserIdImage()).into(imageView);
                            resName.setText(troodOrder.getCompanyName());
                            dataFrame.setVisible(LAYOUT);
                        } else {
                            dataFrame.setVisible(ERROR);
                        }
                    }

                    @Override
                    public void onError(String msgError) {
                        dataFrame.setVisible(ERROR);
                    }
                }, "TroodOrderDetails"));
    }

    private void onClick() {

        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientID = type == STORE ? order.getUserId() + "" : troodOrder.getUserId() + "";
                if (clientID.equals(getPrefManager().getUserId())) {
                    toast("لا يمكنك توصيل الطلب لنفسك");
                } else {
                    String item = new Gson().toJson(type == STORE ? order : troodOrder);
                    Intent i = new Intent(RequestOrderConnectionActivity.this, DelivaryRequestActivity.class);
                    i.putExtra("order", item);
                    i.putExtra("type", type);
                    openActivity(i);
                }
            }
        });

        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(RequestOrderConnectionActivity.this);
                dialog.setContentView(R.layout.popup_agent);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ((TextView) dialog.findViewById(R.id.clientName)).setText(type == STORE ? order.getName() : troodOrder.getToAddress());
                dialog.findViewById(R.id.comments).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        openActivity(new Intent(RequestOrderConnectionActivity.this, CommentsActivity.class));
                    }
                });
                Picasso.get().load(Constant.BASE_IMAGE + (type == STORE ? order.getImage() : troodOrder.getUserIdImage())).into(((ImageView) dialog.findViewById(R.id.image)));
                dialog.show();
            }
        });
    }
}
