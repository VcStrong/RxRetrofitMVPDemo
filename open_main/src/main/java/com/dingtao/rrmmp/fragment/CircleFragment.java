package com.dingtao.rrmmp.fragment;

import android.content.Intent;
import androidx.recyclerview.widget.GridLayoutManager;

import com.dingtao.rrmmp.main.R;
import com.dingtao.rrmmp.main.R2;
import com.dingtao.rrmmp.activity.AddCircleActivity;
import com.dingtao.rrmmp.adapter.CircleAdpater;
import com.dingtao.common.bean.Circle;
import com.dingtao.common.core.DataCall;
import com.dingtao.common.core.WDFragment;
import com.dingtao.common.core.exception.ApiException;
import com.dingtao.rrmmp.presenter.CirclePresenter;
import com.dingtao.rrmmp.presenter.GreatPresenter;
import com.dingtao.common.util.UIUtils;
import com.dingtao.common.util.recyclerview.SpacingItemDecoration;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author dingtao
 * @date 2019/1/2 10:28
 * qq:1940870847
 */
public class CircleFragment extends WDFragment implements XRecyclerView.LoadingListener, CircleAdpater.GreatListener {

    @BindView(R2.id.circle_list)
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

        mCircleList.setLayoutManager(new GridLayoutManager(getContext(), 1));

        int space = getResources().getDimensionPixelSize(R.dimen.dip_20);

        mCircleList.addItemDecoration(new SpacingItemDecoration(space));

        mCircleAdapter.setGreatListener(this);

        mCircleList.setAdapter(mCircleAdapter);
        mCircleList.setLoadingListener(this);

        // 设置数据
        circlePresenter = new CirclePresenter(new CircleCall());

        mCircleList.refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (addCircle) {//publish new message,so you have to refresh
            addCircle = false;
            mCircleList.refresh();
        }
    }

    @Override
    public void onRefresh() {
        if (circlePresenter.isRunning()) {
            mCircleList.refreshComplete();
            return;
        }
        circlePresenter.reqeust(true, LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId());
    }

    @Override
    public void onLoadMore() {
        if (circlePresenter.isRunning()) {
            mCircleList.loadMoreComplete();
            return;
        }
        circlePresenter.reqeust(false, LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId());
    }

    @OnClick(R2.id.add_circle)
    public void addCircle() {
        Intent intent = new Intent(getContext(), AddCircleActivity.class);
        startActivity(intent);
    }

    @Override
    public void great(int position, Circle circle) {
        GreatPresenter greatPresenter = new GreatPresenter(new GreatCall());
        greatPresenter.reqeust(LOGIN_USER.getUserId() + "", LOGIN_USER.getSessionId(), circle.getId(), position, circle);
    }

    /**
     * @author dingtao
     * @date 2019/1/3 9:23 AM
     * banner回调接口
     */
    class CircleCall implements DataCall<List<Circle>> {

        @Override
        public void success(List<Circle> data, Object... args) {
            mCircleList.refreshComplete();
            mCircleList.loadMoreComplete();
            //添加列表并刷新
            if (circlePresenter.getPage() == 1) {
                mCircleAdapter.clear();
            }
            mCircleAdapter.addAll(data);
            mCircleAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e, Object... args) {
            mCircleList.refreshComplete();
            mCircleList.loadMoreComplete();
        }
    }

    class GreatCall implements DataCall<Object> {

        @Override
        public void success(Object data, Object... args) {
            int position = (int) args[3];
            UIUtils.showToastSafe("点击" + position + "    adapter条目：" + mCircleAdapter.getItemCount()
                    + "    recycler条目：" + mCircleList.getChildCount());
            Circle circle = (Circle) args[4];
            Circle nowCircle = mCircleAdapter.getItem(position);
            if (nowCircle.getId() == circle.getId()) {
                nowCircle.setGreatNum(nowCircle.getGreatNum() + 1);
                nowCircle.setWhetherGreat(1);
                mCircleAdapter.notifyItemChanged(position + 1, 0);
            }
        }

        @Override
        public void fail(ApiException e, Object... args) {

        }
    }

}
