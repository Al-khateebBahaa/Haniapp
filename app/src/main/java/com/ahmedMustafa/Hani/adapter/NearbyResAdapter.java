package com.ahmedMustafa.Hani.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.orders.RestaurantDetailsActivity;
import com.ahmedMustafa.Hani.model.NearbyRestaurantModel;
import com.ahmedMustafa.Hani.utils.base.BaseAdapter;
import com.ahmedMustafa.Hani.utils.base.BaseHolder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NearbyResAdapter extends BaseAdapter<NearbyRestaurantModel.Restaurant, NearbyResAdapter.ResHolder> implements Filterable {
    private Location mLocation;
    private int size;

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getItemCount() {
        /*
        if (filtterList == null) {
            return 0;
        } else {
            if (size == 3) {
                return 3;
            } else {
                return filtterList.size();
            }
        }*/

        return 10;

    }

    public @Inject
    NearbyResAdapter(Context context) {
        super(context);
    }

    @Override
    public void setList(List<NearbyRestaurantModel.Restaurant> list) {
        super.setList(list);
        filtterList = list;
    }

    @Override
    protected int itemId() {
        return R.layout.restaurant_item;
    }

    @NonNull
    @Override
    public ResHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResHolder(getView(parent));
    }

    int type;
    public void setType(int type){
        this.type = type;
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

    @Override
    public void onBindViewHolder(@NonNull ResHolder holder, int position) {
        /*
        NearbyRestaurantModel.Restaurant item = filtterList.get(position);
        holder.resName.setText(item.getName());

        double distanse = getDistanse(item.getGeometry().getLocation().getLat(), item.getGeometry().getLocation().getLng());
        DecimalFormat precision = new DecimalFormat("0.00");
        holder.resLocation.setText(precision.format(distanse) + " KM");
        Picasso.get().load(item.getIcon()).into(holder.imageView);*/
    }

    private List<NearbyRestaurantModel.Restaurant> filtterList;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filtterList = list;
                } else {
                    List<NearbyRestaurantModel.Restaurant> filteredList = new ArrayList<>();
                    for (NearbyRestaurantModel.Restaurant row : list) {

                        if (row.getName().toLowerCase().trim().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    filtterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filtterList = (ArrayList<NearbyRestaurantModel.Restaurant>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


     class ResHolder extends BaseHolder {

        protected ImageView imageView;
        protected TextView resName, resLocation;

        public ResHolder(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            resName = itemView.findViewById(R.id.name);
            resLocation = itemView.findViewById(R.id.location);
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
                    i.putExtra("type",type);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    //context.startActivity();
                }
            });
        }
    }
}
