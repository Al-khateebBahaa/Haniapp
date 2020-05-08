package com.ahmedMustafa.Hani.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.model.TroodDetailsModel;
import com.ahmedMustafa.Hani.utils.base.BaseAdapter;
import com.ahmedMustafa.Hani.utils.base.BaseHolder;

import javax.inject.Inject;

public class TroodPriceAdapter extends BaseAdapter<TroodDetailsModel.CompanyPricesList,TroodPriceAdapter.TroodPriceHolder>{


    public @Inject TroodPriceAdapter(Context context) {
        super(context);
    }

    @Override
    protected int itemId() {
        return R.layout.trood_price_item;
    }

    @NonNull
    @Override
    public TroodPriceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TroodPriceHolder(getView(parent));
    }

    @Override
    public void onBindViewHolder(@NonNull TroodPriceHolder holder, int position) {
        TroodDetailsModel.CompanyPricesList item = getItem(position);
        holder.name.setText(item.getTitle());
        holder.price.setText(item.getPrice()+" ريال ");
    }

    class TroodPriceHolder extends BaseHolder {
        private TextView name,price;
        public TroodPriceHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
