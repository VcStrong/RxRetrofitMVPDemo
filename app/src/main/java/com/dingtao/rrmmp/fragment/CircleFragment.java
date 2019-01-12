package com.dingtao.rrmmp.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;

import com.dingtao.rrmmp.R;
import com.dingtao.rrmmp.activity.AddCircleActivity;
import com.dingtao.rrmmp.adapter.CircleAdpater;
import com.dingtao.rrmmp.bean.Circle;
import com.dingtao.rrmmp.bean.Result;
import com.dingtao.rrmmp.core.DataCall;
import com.dingtao.rrmmp.core.WDFragment;
import com.dingtao.rrmmp.core.exception.ApiException;
import com.dingtao.rrmmp.presenter.CirclePresenter;
import com.dingtao.rrmmp.util.recyclerview.SpacingItemDecoration;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author dingtao
 * @date 2019/1/2 10:28
 * qq:1940870847
 */
public class CircleFragment extends WDFragment implements XRecyclerView.LoadingListener {

    @BindView(R.id.circle_list)
    XRecyclerView mCircleList;

    public static boolean addCircle;//如果添加成功，则为true

    private CircleAdpater mCircleAdapter;

    CirclePresenter circlePresenter;

    @Override
    public String getPageName() {
        return "圈子Fragment";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_circle;
    }

    @Override
    protected void initView() {

        mCircleAdapter = new CircleAdpater(getContext());

        mCircleList.setLayoutManager(new GridLayoutManager(getContext(),1));

        int space = getResources().getDimensionPixelSize(R.dimen.dip_20);

        mCircleList.addItemDecoration(new SpacingItemDecoration(space));

        mCircleList.setAdapter(mCircleAdapter);
        mCircleList.setLoadingListener(this);

        // 设置数据
        circlePresenter = new CirclePresenter(new CircleCall());

        mCircleList.refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (addCircle){//publish new message,so you have to refresh
            addCircle = false;
            mCircleList.refresh();
        }
    }

    @Override
    public void onRefresh() {
        if (circlePresenter.isRunning()){
            mCircleList.refreshComplete();
            return;
        }
        circlePresenter.reqeust(true,LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId());
    }

    @Override
    public void onLoadMore() {
        if (circlePresenter.isRunning()){
            mCircleList.loadMoreComplete();
            return;
        }
        circlePresenter.reqeust(false,LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId());
    }

    @OnClick(R.id.add_circle)
    public void addCircle(){
        Intent intent = new Intent(getContext(),AddCircleActivity.class);
        startActivity(intent);
    }

    /**
     * @author dingtao
     * @date 2019/1/3 9:23 AM
     * banner回调接口
     */
    class CircleCall implements DataCall<Result<List<Circle>>>{

        @Override
        public void success(Result<List<Circle>> data) {
            mCircleList.refreshComplete();
            mCircleList.loadMoreComplete();
            if (data.getStatus().equals("0000")){
                //添加列表并刷新
                if (circlePresenter.getPage()==1){
                    mCircleAdapter.clear();
                }
                mCircleAdapter.addAll(data.getResult());
                mCircleAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            mCircleList.refreshComplete();
            mCircleList.loadMoreComplete();
        }
    }

}
