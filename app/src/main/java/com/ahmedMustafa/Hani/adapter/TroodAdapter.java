package com.ahmedMustafa.Hani.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.trood.TroodDetailsActivity;
import com.ahmedMustafa.Hani.model.TroodsModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.base.BaseAdapter;
import com.ahmedMustafa.Hani.utils.base.BaseHolder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import javax.inject.Inject;

public class TroodAdapter extends BaseAdapter<TroodsModel.Company, TroodAdapter.TroodHolder> {
    private Location mLocation;

    public @Inject
    TroodAdapter(Context context) {
        super(context);
    }

    @Override
    protected int itemId() {
        return R.layout.restaurant_item;
    }

    @NonNull
    @Override
    public TroodAdapter.TroodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TroodAdapter.TroodHolder(getView(parent));
    }

    @Override
    public void onBindViewHolder(@NonNull TroodAdapter.TroodHolder holder, int position) {
        /*
        TroodsModel.Company item = getItem(position);
      holder.resName.setText(item.getName());
        Picasso.get().load(Constant.BASE_IMAGE + item.getImage()).into(holder.imageView);

        try {
            double distanse = getDistanse(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng()));
            DecimalFormat precision = new DecimalFormat("0.00");
            holder.resLocation.setText(precision.format(distanse) + " KM");
        }catch (Exception ex){
            Log.e("error_distanse",ex.getMessage());
            item.setLat("0.0");
            item.setLng("0.0");
        }
*/
    }

    public void setmLocation(Location mLocation) {
        this.mLocation = mLocation;
    }

    private double getDistanse(double lat, double lng) {
        Location location = new Location("LocationA");
        location.setLatitude(lat);
        location.setLongitude(lng);
        double distanse = (location.distanceTo(mLocation)) / 1000;
        return distanse;
    }

    class TroodHolder extends BaseHolder {
        protected ImageView imageView;
        protected TextView resName, resLocation;

        public TroodHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            resName = itemView.findViewById(R.id.name);
            resLocation = itemView.findViewById(R.id.location);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, TroodDetailsActivity.class);
                    TroodsModel.Company item = getItem(getAdapterPosition());
                    i.putExtra("id", item.getId());
                    i.putExtra("name", item.getName());
                    i.putExtra("lat", Double.parseDouble(item.getLat()));
                    i.putExtra("lng", Double.parseDouble(item.getLng()));
                    i.putExtra("phone",item.getPhone());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("id", getItem(getAdapterPosition()).getId() + "");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            });
        }
    }


}
