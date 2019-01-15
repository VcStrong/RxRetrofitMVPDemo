package com.dingtao.rrmmp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.dingtao.rrmmp.R;
import com.dingtao.rrmmp.adapter.ImageAdapter;
import com.dingtao.rrmmp.bean.Result;
import com.dingtao.rrmmp.core.DataCall;
import com.dingtao.rrmmp.core.WDActivity;
import com.dingtao.rrmmp.core.exception.ApiException;
import com.dingtao.rrmmp.fragment.CircleFragment;
import com.dingtao.rrmmp.presenter.PublishCirclePresenter;
import com.dingtao.rrmmp.util.DateUtils;
import com.dingtao.rrmmp.util.FileUtils;
import com.dingtao.rrmmp.util.StringUtils;
import com.dingtao.rrmmp.util.UIUtils;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * @author dingtao
 * @date 2019/1/11 00:22
 * qq:1940870847
 */
public class AddCircleActivity extends WDActivity implements DataCall<Result> {

    @BindView(R.id.bo_text)
    EditText mText;

//    @BindView(R.id.bo_image)
//    ImageView mImage;

    PublishCirclePresenter presenter;

    @BindView(R.id.bo_image_list)
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

    @OnClick(R.id.back)
    public void back(){
        finish();
    }

    @OnClick(R.id.send)
    public void publish(){
        presenter.reqeust(LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId(),
                1,mText.getText().toString(),mImageAdapter.getList());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//resultcode是setResult里面设置的code值
            String filePath = getFilePath(null,requestCode,data);
            if (!StringUtils.isEmpty(filePath)) {
                mImageAdapter.add(filePath);
                mImageAdapter.notifyDataSetChanged();
//                Bitmap bitmap = UIUtils.decodeFile(new File(filePath),200);
//                mImage.setImageBitmap(bitmap);
            }
        }

    }

    @Override
    public void success(Result data) {
        if (data.getStatus().equals("0000")){
            CircleFragment.addCircle = true;
            finish();
        }else{
            UIUtils.showToastSafe(data.getStatus()+"  "+data.getMessage());
        }
    }

    @Override
    public void fail(ApiException e) {
        UIUtils.showToastSafe(e.getCode()+"  "+e.getDisplayMessage());
    }
}
