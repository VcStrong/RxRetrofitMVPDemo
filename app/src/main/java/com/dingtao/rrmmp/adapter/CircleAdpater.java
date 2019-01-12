package com.dingtao.rrmmp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dingtao.rrmmp.R;
import com.dingtao.rrmmp.bean.Circle;
import com.dingtao.rrmmp.util.DateUtils;
import com.dingtao.rrmmp.util.StringUtils;
import com.dingtao.rrmmp.util.recyclerview.SpacingItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author dingtao
 * @date 2019/1/2 15:52
 * qq:1940870847
 */
public class CircleAdpater extends RecyclerView.Adapter<CircleAdpater.MyHolder> {

    Context context;
    private List<Circle> list = new ArrayList<>();

    public CircleAdpater(Context context){
        this.context = context;
    }

    public void addAll(List<Circle> list){
        if (list!=null){
            this.list.addAll(list);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
           View view = View.inflate(viewGroup.getContext(), R.layout.circle_item, null);
           return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        Circle circle = list.get(position);
        myHolder.avatar.setImageURI(Uri.parse(circle.getHeadPic()));
        myHolder.nickname.setText(circle.getNickName());
        try {
            myHolder.time.setText(DateUtils.dateFormat(new Date(circle.getCreateTime()),DateUtils.MINUTE_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myHolder.text.setText(circle.getContent());

        if (StringUtils.isEmpty(circle.getImage())){
            myHolder.gridView.setVisibility(View.GONE);
        }else{
            myHolder.gridView.setVisibility(View.VISIBLE);
            String[] images = circle.getImage().split(",");

//            int imageCount = (int)(Math.random()*9)+1;
            int imageCount = images.length;

            int colNum;//列数
            if (imageCount == 1){
                colNum = 1;
            }else if (imageCount == 2||imageCount == 4){
                colNum = 2;
            }else {
                colNum = 3;
            }
            myHolder.imageAdapter.clear();//清空
//            for (int i = 0; i <imageCount ; i++) {
//                myHolder.imageAdapter.addAll(Arrays.asList(images));
//            }
            myHolder.imageAdapter.addAll(Arrays.asList(images));
            myHolder.gridLayoutManager.setSpanCount(colNum);//设置列数


            myHolder.imageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView avatar;
        TextView nickname;
        TextView time;
        TextView text;
        RecyclerView gridView;
        GridLayoutManager gridLayoutManager;
        ImageAdapter imageAdapter;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
            nickname = itemView.findViewById(R.id.nickname);
            time = itemView.findViewById(R.id.time);
            gridView = itemView.findViewById(R.id.grid_view);
            imageAdapter = new ImageAdapter();
            int space = context.getResources().getDimensionPixelSize(R.dimen.dip_10);;//图片间距
            gridLayoutManager = new GridLayoutManager(context,3);
            gridView.addItemDecoration(new SpacingItemDecoration(space));
            gridView.setLayoutManager(gridLayoutManager);
            gridView.setAdapter(imageAdapter);
        }
    }
}
