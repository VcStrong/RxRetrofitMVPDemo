package com.dingtao.topdemo.core;

import android.support.v7.widget.RecyclerView;

import com.dingtao.topdemo.bean.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingtao
 * @date 2019/1/27 08:57
 * qq:1940870847
 */
public abstract class DTRecyclerAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    List<T> list = new ArrayList<>();
    @Override
    public int getItemCount() {
        return list.size();
    }

    public T getItem(int position) {
        return list.get(position);
    }

    public void addItem(T t) {
        list.add(t);
    }

    public void addItem(int index,T t) {
        list.add(index,t);
    }

    public void removeItem(T t){
        list.remove(t);
    }

    public void clear(){
        list.clear();
    }
}
