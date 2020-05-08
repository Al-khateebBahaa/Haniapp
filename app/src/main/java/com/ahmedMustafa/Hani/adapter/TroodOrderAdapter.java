package com.ahmedMustafa.Hani.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.orders.RequestOrderConnectionActivity;
import com.ahmedMustafa.Hani.model.WatingTroodModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.PrefManager;
import com.ahmedMustafa.Hani.utils.base.BaseAdapter;
import com.ahmedMustafa.Hani.utils.base.BaseHolder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import javax.inject.Inject;

public class TroodOrderAdapter extends BaseAdapter<WatingTroodModel.MyOrder, TroodOrderAdapter.OrderHolder> {


    public @Inject
    TroodOrderAdapter(Context context) {
        super(context);
    }

    @Override
    protected int itemId() {
        return R.layout.res_order_item;
    }

    @NonNull
    @Override
    public TroodOrderAdapter.OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TroodOrderAdapter.OrderHolder(getView(parent));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        WatingTroodModel.MyOrder item = getItem(position);
        holder.name.setText(item.getCompanyName());
        holder.details.setText(item.getOrderDetails());
        DecimalFormat precision = new DecimalFormat("0.00");
        holder.location.setText(precision.format(getDis(
                Double.parseDouble(item.getFromLat()),
                Double.parseDouble(item.getFromLng()),
                Double.parseDouble(item.getToLat()),
                Double.parseDouble(item.getToLng())
        )) + " KM");
      //  double duratin = item.getDeliveryDuration() / 60;
        holder.time.setText("--" + "ساعة");
        Picasso.get().load(Constant.BASE_IMAGE+item.getUserIdImage()).into(holder.imageView);
    }

    private double getDis(double resLat, double resLng, double lat, double lng) {
        Location res = new Location("A");
        res.setLatitude(resLat);
        res.setLongitude(resLng);
        Location user = new Location("B");
        user.setLatitude(lat);
        user.setLongitude(lng);
        float v = user.distanceTo(res);
        return v / 1000;
    }

    class OrderHolder extends BaseHolder {

        private ImageView imageView;
        private TextView name, time, location, details;

        public OrderHolder(View itemView) {
            super(itemView);
            details = itemView.findViewById(R.id.details);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            location = itemView.findViewById(R.id.location);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String clientID = list.get(getAdapterPosition()).getUserId() + "";
                    if (clientID.equals(new PrefManager(context).getUserId())) {
                        Toast.makeText(context, "لا يمكنك توصيل الطلب لنفسك", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(context, RequestOrderConnectionActivity.class);
                        i.putExtra("id", list.get(getAdapterPosition()).getId() + "");
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }
            });
        }
    }


}
