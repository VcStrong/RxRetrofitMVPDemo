package com.dingtao.topdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dingtao.topdemo.R;
import com.dingtao.topdemo.bean.Car;
import com.dingtao.topdemo.core.DTRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingtao
 * @date 2019/1/9 22:07
 * qq:1940870847
 */
public class MyAdapter extends DTRecyclerAdapter<Car,MyAdapter.MyHolder> {

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.list_item,null);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Car car = getItem(position);
        holder.textView.setText(car.getCarName());
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
        }
    }
}
