package com.dingtao.topdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dingtao.topdemo.adapter.MyAdapter;
import com.dingtao.topdemo.bean.Car;
import com.gavin.com.library.PowerfulStickyDecoration;
import com.gavin.com.library.listener.PowerGroupListener;

/**
 * @author dingtao
 * @date 2019/1/9 22:04
 * qq:1940870847
 */
public class TopListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyAdapter myAdapter;
    TextView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mTitle = findViewById(R.id.title);

        mRecyclerView = findViewById(R.id.list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter();

        myAdapter.addItem(new Car("1","速腾","100","大众汽车"));
        myAdapter.addItem(new Car("2","迈腾","100","大众汽车"));
        myAdapter.addItem(new Car("3","帕赛特","100","大众汽车"));
        myAdapter.addItem(new Car("4","捷达","100","大众汽车"));
        myAdapter.addItem(new Car("5","辉腾","100","大众汽车"));
        myAdapter.addItem(new Car("20","S级","200","奔驰汽车"));
        myAdapter.addItem(new Car("21","E级","200","奔驰汽车"));
        myAdapter.addItem(new Car("22","C级","200","奔驰汽车"));
        myAdapter.addItem(new Car("51","A3","300","奥迪汽车"));
        myAdapter.addItem(new Car("52","A4L","300","奥迪汽车"));
        myAdapter.addItem(new Car("53","A6L","300","奥迪汽车"));

        mRecyclerView.setAdapter(myAdapter);
        PowerfulStickyDecoration decoration = PowerfulStickyDecoration.Builder
                .init(new PowerGroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //获取组名，用于判断是否是同一组
                        return myAdapter.getItem(position).getBrandId();
                    }

                    @Override
                    public View getGroupView(int position) {
                        //获取自定定义的组View
                            View view = getLayoutInflater()
                                    .inflate(R.layout.list_title, null, false);
                            ((TextView) view.findViewById(R.id.title))
                                    .setText(myAdapter.getItem(position).getBrand());
                            return view;
                    }
                })
                .setGroupHeight(getResources().getDimensionPixelSize(R.dimen.dip_40))//设置高度
                .build();
        mRecyclerView.addItemDecoration(decoration);
    }
}
