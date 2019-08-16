package com.dingtao.rrmmp.activity;

import android.app.Activity;
import android.content.Intent;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dingtao.common.util.Constant;
import com.dingtao.rrmmp.main.R;
import com.dingtao.rrmmp.main.R2;
import com.dingtao.rrmmp.adapter.ImageAdapter;
import com.dingtao.common.bean.Circle;
import com.dingtao.common.core.DataCall;
import com.dingtao.common.core.WDActivity;
import com.dingtao.common.core.exception.ApiException;
import com.dingtao.rrmmp.fragment.CircleFragment;
import com.dingtao.common.util.StringUtils;
import com.dingtao.common.util.UIUtils;
import com.dingtao.rrmmp.presenter.PublishCirclePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author dingtao
 * @date 2019/1/11 00:22
 * qq:1940870847
 */
@Route(path = Constant.ACTIVITY_URL_ADD_CIRCLE)
public class AddCircleActivity extends WDActivity implements DataCall<Circle> {

    @BindView(R2.id.bo_text)
    EditText mText;

//    @BindView(R2.id.bo_image)
//    ImageView mImage;

    PublishCirclePresenter presenter;

    @BindView(R2.id.bo_image_list)
    RecyclerView mImageList;

    ImageAdapter mImageAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_circle;
    }

    @Override
    protected void initView() {
        mImageAdapter = new ImageAdapter();
        mImageAdapter.setSign(1);
        mImageAdapter.add(R.drawable.mask_01);
        mImageList.setLayoutManager(new GridLayoutManager(this,3));
        mImageList.setAdapter(mImageAdapter);

        presenter = new PublishCirclePresenter(this);
    }

    @Override
    protected void destoryData() {

    }

    @OnClick(R2.id.back)
    public void back(){
        finish();
    }

    @OnClick(R2.id.send)
    public void publish(){
        presenter.reqeust(LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId(),
                1,mText.getText().toString(),mImageAdapter.getList());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {//resultcode是setResult里面设置的code值
            String filePath = getFilePath(null,requestCode,data);
            if (!StringUtils.isEmpty(filePath)) {
                mImageAdapter.add(filePath);
                mImageAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void success(Circle data,Object...args) {
        CircleFragment.addCircle = true;
        finish();
    }

    @Override
    public void fail(ApiException e,Object...args) {
        UIUtils.showToastSafe(e.getCode()+"  "+e.getDisplayMessage());
    }
}
