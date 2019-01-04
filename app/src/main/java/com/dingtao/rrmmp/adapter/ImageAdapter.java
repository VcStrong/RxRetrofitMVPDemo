package com.dingtao.rrmmp.adapter;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dingtao.rrmmp.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingtao
 * @date 2019/1/3 23:24
 * qq:1940870847
 */
public class ImageAdapter extends BaseAdapter {

    private List<String> mList = new ArrayList<>();

    public void addAll(List<String> list){
        mList.addAll(list);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHodler myHodler ;
        if (convertView==null){
            convertView = View.inflate(parent.getContext(), R.layout.circle_image_item,null);
            myHodler = new MyHodler();
            myHodler.image = convertView.findViewById(R.id.circle_image);
            convertView.setTag(myHodler);
        }
        myHodler = (MyHodler) convertView.getTag();
        myHodler.image.setImageURI(Uri.parse(mList.get(position)));
        return convertView;
    }

    public void clear() {
        mList.clear();
    }

    class MyHodler {
        SimpleDraweeView image;
    }
}
