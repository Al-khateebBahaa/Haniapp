package com.ahmedMustafa.Hani.adapter;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.model.TroodAsCompanyModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.base.BaseAdapter;
import com.ahmedMustafa.Hani.utils.base.BaseHolder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import javax.inject.Inject;

public class CompanyMainAdapter extends BaseAdapter<TroodAsCompanyModel.MyOrder, CompanyMainAdapter.CompanyMainHolder> {


    @Inject
    public CompanyMainAdapter(Context context) {
        super(context);
    }

    @Override
    protected int itemId() {
        return R.layout.restaurant_item;
    }

    @NonNull
    @Override
    public CompanyMainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompanyMainHolder(getView(parent));
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyMainHolder holder, int position) {
        TroodAsCompanyModel.MyOrder item = getItem(position);
        holder.resName.setText(item.getUserName());
        Picasso.get().load(Constant.BASE_IMAGE+item.getUserImage()).into(holder.imageView);
        Location location = new Location("C");
        location.setLatitude(Double.parseDouble(item.getToLat()));
        location.setLongitude(Double.parseDouble(item.getToLng()));

        Location l = new Location("U");
        location.setLatitude(Double.parseDouble(item.getFromLat()));
        location.setLongitude(Double.parseDouble(item.getFromLng()));

        float v = l.distanceTo(location) / 1000;
        holder.resLocation.setText(new DecimalFormat("0.00").format(v) + " KM");
    }

    class CompanyMainHolder extends BaseHolder {
        protected ImageView imageView;
        protected TextView resName, resLocation;

        public CompanyMainHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            resName = itemView.findViewById(R.id.name);
            resLocation = itemView.findViewById(R.id.location);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
