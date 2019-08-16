package com.dingtao.rrmmp.adapter;

import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dingtao.rrmmp.main.R;
import com.dingtao.common.core.WDActivity;
import com.dingtao.common.util.UIUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingtao
 * @date 2019/1/3 23:24
 * qq:1940870847
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyHodler> {

    private List<Object> mList = new ArrayList<>();
    private int sign;//0:普通点击，1自定义

    public void addAll(List<Object> list) {
        mList.addAll(list);
    }
    public void addStringListAll(List<String> list) {
        mList.addAll(list);
    }

    public void setSign(int sign){
        this.sign = sign;
    }


    @NonNull
    @Override
    public MyHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.circle_image_item, null);
        return new MyHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHodler myHodler, final int position) {
        if (mList.get(position) instanceof String) {
            String imageUrl = (String) mList.get(position);
            if (imageUrl.contains("http:")) {//加载http
                myHodler.image.setImageURI(Uri.parse(imageUrl));
            } else {//加载sd卡
                Uri uri = Uri.parse("file://" + imageUrl);
                myHodler.image.setImageURI(uri);
            }
        } else {//加载资源文件
            int id = (int) mList.get(position);
            Uri uri = Uri.parse("res://com.dingtao.rrmmp/" + id);
            myHodler.image.setImageURI(uri);
        }

        myHodler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sign == 1) {//自定义点击
                    if (position == 0) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        WDActivity.getForegroundActivity().startActivityForResult(intent, WDActivity.PHOTO);
                    } else {
                        UIUtils.showToastSafe("点击了图片");
                    }
                }else{
                    UIUtils.showToastSafe("点击了图片");
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clear() {
        mList.clear();
    }

    public void add(Object image) {
        if (image != null) {
            mList.add(image);
        }
    }

    public List<Object> getList() {
        return mList;
    }

    class MyHodler extends RecyclerView.ViewHolder {
        SimpleDraweeView image;

        public MyHodler(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.circle_image);
        }
    }
}
