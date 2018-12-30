package com.dingtao.rrmmp.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.dingtao.rrmmp.R;
import com.dingtao.rrmmp.core.WDActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author dingtao
 * @date 2018/12/29 16:29
 * qq:1940870847
 */
public class WelcomeActivity extends WDActivity {

    @BindView(R.id.seek_text)
    TextView seekText;

    private int count = 3;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            count--;
            seekText.setText("跳过"+count+"s");
            if (count==0){
                if (LOGIN_USER!=null){
                    intent(MainActivity.class);
                }else{
                    intent(LoginActivity.class);
                }
                finish();
            }else{//消息不能复用，必须新建
               handler.sendEmptyMessageDelayed(1,1000);
            }
        }
    };

    @Override
    protected void initView() {
        seekText.setText("跳过"+count+"s");
        handler.sendEmptyMessageDelayed(1,1000);
    }

    @OnClick(R.id.seek_text)
    public void seek(){
        handler.removeMessages(1);
        if (LOGIN_USER!=null){
            intent(MainActivity.class);
        }else{
            intent(LoginActivity.class);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void destoryData() {

    }
}
