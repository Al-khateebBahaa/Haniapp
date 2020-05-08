package com.ahmedMustafa.Hani.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.trood.RequestAgentActivity;
import com.ahmedMustafa.Hani.model.AllAgetntForTroodModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.PrefManager;
import com.ahmedMustafa.Hani.utils.base.BaseAdapter;
import com.ahmedMustafa.Hani.utils.base.BaseHolder;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import javax.inject.Inject;

public class AllAgentFroTroodAdapter extends BaseAdapter<AllAgetntForTroodModel.Agent, AllAgentFroTroodAdapter.AllAgentFroTroodHolder> {

    private PrefManager manager;

    @Inject
    public AllAgentFroTroodAdapter(Context context) {
        super(context);
        manager = new PrefManager(context);
    }


    private String item = null;

    public void setDate(String item) {
        this.item = item;
    }

    @Override
    protected int itemId() {
        return R.layout.agent_item;
    }

    @NonNull
    @Override
    public AllAgentFroTroodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllAgentFroTroodHolder(getView(parent));
    }

    @Override
    public void onBindViewHolder(@NonNull AllAgentFroTroodHolder holder, int position) {

        AllAgetntForTroodModel.Agent item = getItem(position);
        Picasso.get().load(Constant.BASE_IMAGE + item.getImage()).into(holder.imageView);
        holder.nameView.setText(item.getName());
        holder.phoneView.setText(item.getPhone());
        holder.ratingBar.setRating(item.getRating());
        holder.locationView.setText(" يبعد عنك مسافة " + getDistance(item.getLat(), item.getLng()));
    }

    private String getDistance(String lat, String lng) {
        Location location = new Location("A");
        location.setLongitude(Double.parseDouble(manager.getLng()));
        location.setLatitude(Double.parseDouble(manager.getLat()));

        Location location2 = new Location("B");
        location2.setLongitude(Double.parseDouble(lng));
        location2.setLatitude(Double.parseDouble(lat));

        double distanse = (location.distanceTo(location2)) / 1000;
        DecimalFormat precision = new DecimalFormat("0.00");
        return precision.format(distanse);
    }

    class AllAgentFroTroodHolder extends BaseHolder {
        private ImageView imageView;
        private TextView nameView, phoneView, locationView;
        private RatingBar ratingBar;

        public AllAgentFroTroodHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nameView = itemView.findViewById(R.id.name);
            phoneView = itemView.findViewById(R.id.phone);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            locationView = itemView.findViewById(R.id.location);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item != null) {
                        Intent i = new Intent(context, RequestAgentActivity.class);
                        i.putExtra("item", item);
                        i.putExtra("agent", new Gson().toJson(getItem(getAdapterPosition())));
                        context.startActivity(i);
                    }

                }
            });
        }
    }
}
