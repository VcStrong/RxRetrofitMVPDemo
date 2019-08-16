package com.dingtao.rrmmp.adapter;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dingtao.rrmmp.main.R;
import com.dingtao.common.bean.shop.Commodity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingtao
 * @date 2019/1/2 15:52
 * qq:1940870847
 */
public class CommodityAdpater extends RecyclerView.Adapter<CommodityAdpater.MyHolder> {

    Context context;
    private List<Commodity> list = new ArrayList<>();
    public static final int HOT_TYPE = 0;
    public static final int FASHION_TYPE = 1;
    private int type;

    public CommodityAdpater(Context context, int type){
        this.context = context;
        this.type = type;
    }

    public void addAll(List<Commodity> list){
        if (list!=null){
            this.list.addAll(list);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
       if (type == HOT_TYPE) {
           View view = View.inflate(viewGroup.getContext(), R.layout.hot_item, null);
           return new MyHolder(view);
       }else {
           View view = View.inflate(viewGroup.getContext(), R.layout.fashion_item, null);
           return new MyHolder(view);
       }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        Commodity commodity = list.get(position);
        myHolder.image.setImageURI(Uri.parse(commodity.getMasterPic()));
        myHolder.price.setText(commodity.getPrice()+"");
        myHolder.text.setText(commodity.getCommodityName());

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取分类id
//                intent.putExtras("id",)

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView image;
        TextView text;
        TextView price;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
            price = itemView.findViewById(R.id.price);
        }
    }
}
