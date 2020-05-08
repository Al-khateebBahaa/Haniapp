package com.ahmedMustafa.Hani.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedMustafa.Hani.R;
import com.ahmedMustafa.Hani.activity.orders.ChatActivity;
import com.ahmedMustafa.Hani.model.MyOrderModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyOrderAdapter extends BaseExpandableListAdapter {

    private HashMap<Integer, List<MyOrderModel.Order>> list;
    private Context context;
    private Callback callback;
    private final int NO_SEND = 0;
    private final int WILL_SENDING = 1;
    private final int DONE_SEND = 2;
    private final int FINISHED = 3;
    private int type = 0;

    public @Inject
    MyOrderAdapter(Context context, Callback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setList(HashMap<Integer, List<MyOrderModel.Order>> list) {
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return 10;//list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition) != null ? list.get(groupPosition).size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.keySet().toArray()[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.my_order_header, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.text)).setText(groupPosition == 0 ? "طلبات نشطة" : "طلبات منتهية");
        ((ImageView) convertView.findViewById(R.id.image)).setImageResource(isExpanded ? R.drawable.ic_up : R.drawable.ic_down);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.my_order_item, parent, false);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final MyOrderModel.Order item = (MyOrderModel.Order) getChild(groupPosition, childPosition);

        double duration = item.getDeliveryDuration() / 60;
        DecimalFormat precision = new DecimalFormat("0.00");
        holder.orderNum.setText("رقم : " + item.getId() + "");
        holder.time.setText(precision.format(duration) + " ساعة ");
        holder.details.setText(item.getOrderDetails());
        Picasso.get().load(Constant.BASE_IMAGE + item.getImage()).into(holder.imageView);
        if (type != 0) {
            holder.agentName.setText(item.getName());
        } else {
            holder.agentName.setText(item.getAgentId() == 0 ? "--" : item.getName());
        }

        holder.resName.setText(item.getRestaurantName());

        if (groupPosition == 0) {
            switch (item.getStatus()) {
                case NO_SEND:
                    holder.orderStatus.setText("يتم الإرسال");
                    holder.cancel_but.setVisibility(View.VISIBLE);
                    break;
                case WILL_SENDING:
                    holder.orderStatus.setText("جاري التوصيل");
                    holder.cancel_but.setVisibility(View.GONE);
                    break;
                case DONE_SEND:
                    holder.orderStatus.setText("تم التوصيل");
                    holder.cancel_but.setVisibility(View.GONE);
                    break;
            }
        } else {
            holder.cancel_but.setVisibility(View.GONE);
            holder.orderStatus.setText("تم التوصيل");
        }

        holder.cancel_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.cancelOrder(childPosition, item.getId() + "");
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupPosition == 0 && item.getStatus() == WILL_SENDING) {
                    Intent i = new Intent(context, ChatActivity.class);
                    i.putExtra("id", item.getId() + "");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
        });
        return holder.view;
    }

    public void removeChaild(int position) {
        list.get(0).remove(position);
        notifyDataSetChanged();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class Holder {
        private View view;
        private TextView resName, time, agentName, orderNum, orderStatus, details;
        private CircleImageView imageView;
        private Button cancel_but;

        public Holder(View view) {
            this.view = view;
            cancel_but = view.findViewById(R.id.cancel_but);
            resName = view.findViewById(R.id.resName);
            time = view.findViewById(R.id.time);
            agentName = view.findViewById(R.id.agentName);
            orderNum = view.findViewById(R.id.orderNum);
            orderStatus = view.findViewById(R.id.orderStatus);
            imageView = view.findViewById(R.id.image);
            details = view.findViewById(R.id.details);
        }
    }

    public interface Callback {
        void cancelOrder(int potion, String id);
    }
}
