package com.dingtao.common.core;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dingtao.common.bean.UserInfo;
import com.dingtao.common.core.db.DaoMaster;
import com.dingtao.common.core.db.UserInfoDao;
import com.dingtao.common.util.LogUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author dingtao
 * @date 2018/12/29 14:00
 * qq:1940870847
 */
public abstract class WDActivity extends AppCompatActivity {

    public final static int PHOTO = 0;// 相册选取
    public final static int CAMERA = 1;// 拍照
    public Dialog mLoadDialog;// 加载框

    /**
     * 记录处于前台的Activity
     */
    private static WDActivity mForegroundActivity = null;

    public UserInfo LOGIN_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //查询登录用户，方便每个页面使用
        UserInfoDao userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos != null && userInfos.size() > 0) {
            LOGIN_USER = userInfos.get(0);//读取第一项
        }
        //打印堆栈ID
        LogUtils.e("getTaskId = " + getTaskId());
        initLoad();
        setContentView(getLayoutId());
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);//绑定布局
        initView();
    }

    /**
     * 设置layoutId
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 清除数据
     */
    protected abstract void destoryData();

    /**
     * @param mActivity 传送Activity的
     */
    public void intent(Class mActivity) {
        Intent intent = new Intent(this, mActivity);
        startActivity(intent);
    }

    /**
     * @param path 传送Activity的
     */
    public void intentByRouter(String path) {
        ARouter.getInstance().build(path)
                .navigation(this);
    }

    /**
     * @param mActivity 传送Activity的
     * @param bundle
     */
    public void intent(Class mActivity, Bundle bundle) {
        Intent intent = new Intent(this, mActivity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * @param path 传送Activity的
     * @param bundle
     */
    public void intentByRouter(String path, Bundle bundle) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .navigation(this);
    }

    /**
     * 初始化加载框
     */
    private void initLoad() {
        mLoadDialog = new ProgressDialog(this);// 加载框
        mLoadDialog.setCanceledOnTouchOutside(false);
        mLoadDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (mLoadDialog.isShowing() && keyCode == KeyEvent.KEYCODE_BACK) {
                    cancelLoadDialog();//加载消失的同时
                    mLoadDialog.cancel();
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destoryData();
    }

    //取消操作：请求或者其他
    public void cancelLoadDialog() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mForegroundActivity = this;
    }

    /**
     * 获取当前处于前台的activity
     */
    public static WDActivity getForegroundActivity() {
        return mForegroundActivity;
    }

    /**
     * 得到图片的路径
     *
     * @param fileName
     * @param requestCode
     * @param data
     * @return
     */
    public String getFilePath(String fileName, int requestCode, Intent data) {
        if (requestCode == CAMERA) {
            return fileName;
        } else if (requestCode == PHOTO) {
            Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor
                    .getString(actual_image_column_index);
            // 4.0以上平台会自动关闭cursor,所以加上版本判断,OK
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                actualimagecursor.close();
            return img_path;
        }
        return null;
    }
}
