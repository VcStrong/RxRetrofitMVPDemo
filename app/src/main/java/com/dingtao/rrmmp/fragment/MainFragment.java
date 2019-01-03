package com.dingtao.rrmmp.fragment;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.dingtao.rrmmp.R;
import com.dingtao.rrmmp.adapter.CommodityAdpater;
import com.dingtao.rrmmp.bean.Banner;
import com.dingtao.rrmmp.bean.Result;
import com.dingtao.rrmmp.bean.shop.HomeList;
import com.dingtao.rrmmp.core.DataCall;
import com.dingtao.rrmmp.core.WDFragment;
import com.dingtao.rrmmp.core.exception.ApiException;
import com.dingtao.rrmmp.presenter.BannerPresenter;
import com.dingtao.rrmmp.presenter.HomeListPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * @author dingtao
 * @date 2019/1/2 10:28
 * qq:1940870847
 */
public class MainFragment extends WDFragment {

    @BindView(R.id.banner)
    MZBannerView mMZBanner;

    @BindView(R.id.hot_list)
    RecyclerView mHotList;

    @BindView(R.id.fashion_list)
    RecyclerView mFashionList;

    @BindView(R.id.life_list)
    RecyclerView mLifeList;

    private CommodityAdpater mHotAdapter,mFashionAdapter,mLifeAdapter;

    BannerPresenter bannerPresenter;
    HomeListPresenter homeListPresenter;

    @Override
    public String getPageName() {
        return "首页Fragment";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_main;
    }

    @Override
    protected void initView() {

        mHotAdapter = new CommodityAdpater(getContext(),CommodityAdpater.HOT_TYPE);
        mFashionAdapter = new CommodityAdpater(getContext(),CommodityAdpater.FASHION_TYPE);
        mLifeAdapter = new CommodityAdpater(getContext(),CommodityAdpater.HOT_TYPE);

        mHotList.setLayoutManager(new GridLayoutManager(getContext(),3));
        mFashionList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mLifeList.setLayoutManager(new GridLayoutManager(getContext(),2));

        mHotList.setAdapter(mHotAdapter);
        mFashionList.setAdapter(mFashionAdapter);
        mLifeList.setAdapter(mLifeAdapter);


        // 设置数据
        bannerPresenter = new BannerPresenter(new MyBanner());
        bannerPresenter.reqeust();

        homeListPresenter = new HomeListPresenter(new HomeCall());
        homeListPresenter.reqeust();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();
    }

    /**
     * @author dingtao
     * @date 2019/1/3 9:21 AM
     * banner
     */
    class BannerViewHolder implements MZViewHolder<Banner> {
        private SimpleDraweeView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            mImageView = view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Banner data) {
            // 数据绑定
            mImageView.setImageURI(Uri.parse(data.getImageUrl()));
        }
    }

    /**
     * @author dingtao
     * @date 2019/1/3 9:23 AM
     * banner回调接口
     */
    class MyBanner implements DataCall<Result<List<Banner>>>{

        @Override
        public void success(Result<List<Banner>> data) {
            if (data.getStatus().equals("0000")){
                mMZBanner.setIndicatorVisible(false);
                mMZBanner.setPages(data.getResult(), new MZHolderCreator<BannerViewHolder>() {
                    @Override
                    public BannerViewHolder createViewHolder() {
                        return new BannerViewHolder();
                    }
                });
                mMZBanner.start();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    /**
     * @author dingtao
     * @date 2019/1/3 9:23 AM
     * banner回调接口
     */
    class HomeCall implements DataCall<Result<HomeList>>{

        @Override
        public void success(Result<HomeList> data) {
            if (data.getStatus().equals("0000")){
                //添加列表并刷新
                mHotAdapter.addAll(data.getResult().getRxxp().getCommodityList());
                mFashionAdapter.addAll(data.getResult().getMlss().getCommodityList());
                mLifeAdapter.addAll(data.getResult().getPzsh().getCommodityList());
                mHotAdapter.notifyDataSetChanged();
                mFashionAdapter.notifyDataSetChanged();
                mLifeAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

}
