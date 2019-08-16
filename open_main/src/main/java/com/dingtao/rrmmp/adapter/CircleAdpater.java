package com.dingtao.rrmmp.adapter;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dingtao.rrmmp.main.R;
import com.dingtao.common.bean.Circle;
import com.dingtao.rrmmp.presenter.GreatPresenter;
import com.dingtao.common.util.DateUtils;
import com.dingtao.common.util.StringUtils;
import com.dingtao.common.util.recyclerview.SpacingItemDecoration;
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
    private GreatPresenter greatPresenter;

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
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int position) {
        final Circle circle = list.get(position);
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
            myHolder.imageAdapter.addStringListAll(Arrays.asList(images));
            myHolder.gridLayoutManager.setSpanCount(colNum);//设置列数


            myHolder.imageAdapter.notifyDataSetChanged();
        }

        if (circle.getWhetherGreat() == 1){
            myHolder.priseImage.setImageResource(R.drawable.common_btn_prise_s);
        }else{
            myHolder.priseImage.setImageResource(R.drawable.common_btn_prise_n);
        }

        myHolder.priseText.setText(circle.getGreatNum()+"");
        myHolder.priseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (greatListener!=null){
                    greatListener.great(position,circle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    public Circle getItem(int postion) {
        return list.get(postion);
    }

    class MyHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView avatar;
        TextView nickname;
        TextView time;
        TextView text;
        RecyclerView gridView;
        GridLayoutManager gridLayoutManager;
        ImageAdapter imageAdapter;
        LinearLayout priseLayout;
        ImageView priseImage;
        TextView priseText;

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
            priseImage = itemView.findViewById(R.id.prise_image);
            priseText = itemView.findViewById(R.id.prise_count);
            priseLayout = itemView.findViewById(R.id.prise_layout);
        }
    }

    private GreatListener greatListener;

    public void setGreatListener(GreatListener greatListener) {
        this.greatListener = greatListener;
    }

    public interface GreatListener{
        void great(int position,Circle circle);
    }
}
