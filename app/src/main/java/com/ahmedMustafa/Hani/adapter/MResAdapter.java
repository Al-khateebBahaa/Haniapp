package com.ahmedMustafa.Hani.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.orders.RestaurantDetailsActivity;
import com.ahmedMustafa.Hani.model.MResModel;
import com.ahmedMustafa.Hani.model.NearbyRestaurantModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.base.BaseAdapter;
import com.ahmedMustafa.Hani.utils.base.BaseHolder;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class MResAdapter extends BaseAdapter<NearbyRestaurantModel.Restaurant, MResAdapter.Holder> {

    private Callback callback;

    public @Inject
    MResAdapter(Context context,Callback callback) {
        super(context);
        this.callback = callback;
    }

    @Override
    protected int itemId() {
        return R.layout.m_res_item;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(getView(parent));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        NearbyRestaurantModel.Restaurant item = getItem(position);
        holder.resName.setText(item.getName());
        Picasso.get().load(item.getIcon()).into(holder.imageView);
    }

    class Holder extends BaseHolder {
        private ImageView imageView;
        private TextView resName;
        private Button cancelButton;

        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            resName = itemView.findViewById(R.id.resName);
            cancelButton = itemView.findViewById(R.id.cancel_but);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.RemeveMeAsAgentListner(getAdapterPosition(),getItem(getAdapterPosition()).getId());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NearbyRestaurantModel.Restaurant item = getItem(getAdapterPosition());
                    Intent i = new Intent(context, RestaurantDetailsActivity.class);
                    i.putExtra("id", item.getId());
                    i.putExtra("name", item.getName());
                    i.putExtra("lat", item.getGeometry().getLocation().getLat());
                    i.putExtra("lng", item.getGeometry().getLocation().getLng());
                    i.putExtra("address", item.getVicinity());
                    i.putExtra("type", Constant.RES);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            });
        }
    }

    public interface Callback{
        void RemeveMeAsAgentListner(int position,String resId);
    }
}
