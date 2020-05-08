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
import com.ahmedMustafa.Hani.activity.settingPages.MyResActivity;
import com.ahmedMustafa.Hani.model.TroodAsCompanyModel;
import com.ahmedMustafa.Hani.utils.Constant;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyComapnyOrderAdapter extends BaseExpandableListAdapter {

    private Context context;
    private MyOrderAdapter.Callback callback;
    private HashMap<Integer, List<TroodAsCompanyModel.MyOrder>> list;

    @Inject
    public MyComapnyOrderAdapter(Context context, MyOrderAdapter.Callback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void setList(HashMap<Integer, List<TroodAsCompanyModel.MyOrder>> list) {
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
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
        ((TextView) convertView.findViewById(R.id.text)).setText(groupPosition == 0 ? "طلبات جديده" : "طلبات نشطه");
        ((ImageView) convertView.findViewById(R.id.image)).setImageResource(isExpanded ? R.drawable.ic_up : R.drawable.ic_down);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.my_order_company_item, parent, false);
            holder = new Holder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final TroodAsCompanyModel.MyOrder item = (TroodAsCompanyModel.MyOrder) getChild(groupPosition, childPosition);
        holder.resName.setText(item.getUserName());
        holder.orderNum.setText("رقم : " + item.getId() + "");
        holder.details.setText(item.getOrderDetails());
        Picasso.get().load(Constant.BASE_IMAGE + item.getUserImage()).into(holder.imageView);
        holder.agentName.setText("--");
        switch (item.getStatus()) {
            case 0:
                holder.time.setText("طلب جديد");
                break;
            case 1:
                holder.time.setText("جاري التوصيل لمقر الشركه");
                break;
            case 2:
                holder.time.setText("تم التوصيل لمقر الشركه");
                holder.cancel_but.setVisibility(View.VISIBLE);
            case 3:
                holder.time.setText("جاري توصيل الطرد");
                holder.cancel_but.setText("تم التوصيل");
                holder.cancel_but.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.time.setText("تم توصيل الطرد");
        }

        holder.cancel_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getStatus() == 2){
                    Intent i = new Intent(context, MyResActivity.class);
                    i.putExtra("type", 1);
                    i.putExtra("item", new Gson().toJson(item));
                    context.startActivity(i);
                }else {
                    callback.cancelOrder(childPosition,item.getId()+"");
                }

            }
        });
        return holder.view;
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
}
