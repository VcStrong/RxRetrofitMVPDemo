package com.dingtao.rrmmp.fragment;

import android.content.Context;
import android.net.Uri;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.dingtao.rrmmp.main.R;
import com.dingtao.rrmmp.main.R2;
import com.dingtao.rrmmp.adapter.CommodityAdpater;
import com.dingtao.common.bean.Banner;
import com.dingtao.common.bean.shop.HomeList;
import com.dingtao.common.core.DataCall;
import com.dingtao.common.core.WDFragment;
import com.dingtao.common.core.exception.ApiException;
import com.dingtao.rrmmp.presenter.BannerPresenter;
import com.dingtao.rrmmp.presenter.HomeListPresenter;
import com.dingtao.common.util.recyclerview.SpacingItemDecoration;
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
public class HomeFragment extends WDFragment {

    @BindView(R2.id.banner)
    MZBannerView mMZBanner;

    @BindView(R2.id.hot_list)
    RecyclerView mHotList;

    @BindView(R2.id.fashion_list)
    RecyclerView mFashionList;

    @BindView(R2.id.life_list)
    RecyclerView mLifeList;

    private CommodityAdpater mHotAdapter, mFashionAdapter, mLifeAdapter;

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

        mHotAdapter = new CommodityAdpater(getContext(), CommodityAdpater.HOT_TYPE);
        mFashionAdapter = new CommodityAdpater(getContext(), CommodityAdpater.FASHION_TYPE);
        mLifeAdapter = new CommodityAdpater(getContext(), CommodityAdpater.HOT_TYPE);

        mHotList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mFashionList.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mLifeList.setLayoutManager(new GridLayoutManager(getContext(), 2));

        int space = getResources().getDimensionPixelSize(R.dimen.dip_10);

        mHotList.addItemDecoration(new SpacingItemDecoration(space));
        mFashionList.addItemDecoration(new SpacingItemDecoration(space));
        mLifeList.addItemDecoration(new SpacingItemDecoration(space));

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
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
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
    class MyBanner implements DataCall<List<Banner>> {

        @Override
        public void success(List<Banner> data,Object...args) {
            mMZBanner.setIndicatorVisible(false);
            mMZBanner.setPages(data, new MZHolderCreator<BannerViewHolder>() {
                @Override
                public BannerViewHolder createViewHolder() {
                    return new BannerViewHolder();
                }
            });
            mMZBanner.start();
        }

        @Override
        public void fail(ApiException e,Object...args) {

        }
    }

    /**
     * @author dingtao
     * @date 2019/1/3 9:23 AM
     * banner回调接口
     */
    class HomeCall implements DataCall<HomeList> {

        @Override
        public void success(HomeList data, Object... args) {
            //添加列表并刷新
            mHotAdapter.addAll(data.getRxxp().getCommodityList());
            mFashionAdapter.addAll(data.getMlss().getCommodityList());
            mLifeAdapter.addAll(data.getPzsh().getCommodityList());
            mHotAdapter.notifyDataSetChanged();
            mFashionAdapter.notifyDataSetChanged();
            mLifeAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e, Object... args) {

        }
    }

}
