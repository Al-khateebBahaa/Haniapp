package com.ahmedMustafa.Hani.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.model.NotificationModel;
import com.ahmedMustafa.Hani.utils.Common;
import com.ahmedMustafa.Hani.utils.Constant;
import com.ahmedMustafa.Hani.utils.base.BaseAdapter;
import com.ahmedMustafa.Hani.utils.base.BaseHolder;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class NotificationAdapter extends BaseAdapter<NotificationModel.Notification, NotificationAdapter.NotificationHolder> {

    public @Inject NotificationAdapter(Context context,Callback callback) {
        super(context);
        this.callback = callback;
    }

    private Callback callback;
    @Override
    protected int itemId() {
        return R.layout.notification_item;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationHolder(getView(parent));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        NotificationModel.Notification item = getItem(position);
        holder.time.setText(Common.getTime(item.getCreatedAt(), context));
        holder.title.setText(item.getTitle());
        String image = "http://khdmatyapp.com/"+item.getImagePath();
        Picasso.get().load(image).into(holder.imageView);
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    class NotificationHolder extends BaseHolder {

        private TextView title, time;
        private ImageView imageView;

        public NotificationHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onItemClick(getAdapterPosition(),getItem(getAdapterPosition()));
                }
            });
        }
    }

    public interface Callback{
        void onItemClick(int position,NotificationModel.Notification item);
    }

}
