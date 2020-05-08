package com.ahmedMustafa.Hani.utils.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapter<Model, Holder extends BaseHolder> extends RecyclerView.Adapter<Holder> {

    protected List<Model> list;
    protected Context context;
    protected Callback<Model> callback;

    public BaseAdapter(Context context) {
        this.context = context;
    }

    public BaseAdapter(Context context, Callback<Model> callback) {
        this.context = context;
        this.callback = callback;
    }

    public void addToList(List<Model> newList) {
        if (list != null) {
            list.addAll(newList);
            notifyDataSetChanged();
        }
    }

    public void removeItem(int poition) {
        if (list != null) {
            list.remove(poition);
            notifyDataSetChanged();
        }

    }

    protected void setClickItem(View view, final int position, final int type) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onItemClickedListner(position, getItem(position), type);
                } else {
                    Log.e("callbackError", "Callback must equel null");
                }
            }
        });
    }

    protected void setClickItem(View view, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onItemClickedListner(position, getItem(position), 0);
                } else {
                    Log.e("callbackError", "Callback must equel null");
                }
            }
        });
    }

    @LayoutRes
    protected abstract int itemId();

    protected View getView(@NonNull ViewGroup parent) {
        return LayoutInflater.from(context).inflate(itemId(), parent, false);
    }

    public void setList(List<Model> list) {
        this.list = list;
    }

    protected Model getItem(int position) {

        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public interface Callback<T> {
        void onItemClickedListner(int position, T item, int type);
    }

}
