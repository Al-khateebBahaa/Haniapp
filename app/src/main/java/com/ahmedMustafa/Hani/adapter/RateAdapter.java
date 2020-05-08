package com.ahmedMustafa.Hani.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.model.RateModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.base.BaseAdapter;
import com.ahmedMustafa.Hani.utils.base.BaseHolder;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class RateAdapter extends BaseAdapter< RateModel.Comment,RateAdapter.Holder> {


    public @Inject
    RateAdapter(Context context) {
        super(context);
    }

    @Override
    protected int itemId() {
        return R.layout.comment_item;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(getView(parent));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        RateModel.Comment item = getItem(position);

        holder.name.setText(item.getUsername());
        Picasso.get().load(Constant.BASE_IMAGE+item.getImage()).into(holder.imageView);
        holder.comment.setText(item.getComment());

    }


    class Holder extends BaseHolder {
        private TextView name,comment;
        private ImageView imageView;
        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            comment = itemView.findViewById(R.id.comment);
        }
    }
}
