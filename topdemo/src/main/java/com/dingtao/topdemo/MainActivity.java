package com.dingtao.topdemo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dingtao.topdemo.bean.Banner;
import com.dingtao.topdemo.core.DTActivity;
import com.dingtao.topdemo.http.ApiException;
import com.dingtao.topdemo.http.IRequest;
import com.dingtao.topdemo.http.NetWorkManager;
import com.dingtao.topdemo.presenter.BasePresenter;
import com.dingtao.topdemo.presenter.DataCall;
import com.dingtao.topdemo.service.MyIntentService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends DTActivity {

    @BindView(R.id.text)
    private TextView mHtmlText;

    @BindView(R.id.edittext)
    private EditText mEdit;

    @Override
    protected void initView() {
        mEdit.setFilters(new InputFilter[]{new SpaceFilter()});
        initTextView();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    /**
     * 禁止输入空格
     *
     * @return
     */
    public class SpaceFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            //返回null表示接收输入的字符,返回空字符串表示不接受输入的字符
            if (source.equals(" "))
                return "";
            return null;
        }
    }

    public static void main(String[] args) {
        String[] strings = new String[]{"E", "F"};
        String floor = "E";

        for (int j = 2; j < 3; j++) {
            for (int i = 1; i < 23; i++) {
                if (i<10){
                    System.out.println(floor + "-" + j +0+i+1);
                    System.out.println(floor + "-" + j +0+i+2);
                }else {
                    System.out.println(floor + "-" + j +i+1);
                    System.out.println(floor + "-" + j +i+2);
                }
            }
        }
    }

    @OnClick({R.id.top,R.id.intentservice,R.id.login,R.id.retrofit})
    public void onClick(View v) {
        IRequest iRequest = NetWorkManager.getInstance().getRequest();
        switch (v.getId()) {
            case R.id.top:
                intent(TopListActivity.class);
                break;
            case R.id.retrofit:

                new BasePresenter(new DataCall<List<Banner>>() {
                    @Override
                    public void success(List<Banner> data) {
                        Toast.makeText(MainActivity.this, "" + data.size(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void fail(ApiException e) {
                        Toast.makeText(getBaseContext(), e.getCode() + e.getDisplayMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }) {
                }.request(iRequest.bannerShow());
                break;
            case R.id.login:
                new BasePresenter(new DataCall() {
                    @Override
                    public void success(Object data) {
                        Toast.makeText(MainActivity.this, "" + data, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void fail(ApiException e) {
                        Toast.makeText(getBaseContext(), e.getCode() + e.getDisplayMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }) {
                }.request(iRequest.login("1212123", "1324234"));
                break;
            case R.id.intentservice:
                Intent intent = new Intent(this,MyIntentService.class);
                startService(intent);
                break;
        }
    }

    /**
     * TextView
     */
    private void initTextView() {
//        String content = "我看<a href=\"http://topic_this/\">点一点</a>哈哈<a href=\"woqu\">提示</a>";// 了解一下href属性，不过这里属性值可以随便写
        String str = "从配角到明星####大力水手90岁#，" +
                "让我们#大力水手90岁#回忆####大力水手90岁#一下暗送菠菜，" +
                "一说起这个#菠菜#就想起#赵本山#####。";
        String content = toClickStr(str);
        mHtmlText.setText(Html.fromHtml(content));
        mHtmlText.setMovementMethod(LinkMovementMethod.getInstance());// 必须移除方法
        mHtmlText.setClickable(false);// 设置不可点击
        CharSequence text = mHtmlText.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable) mHtmlText.getText();
            URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans();// should clear old spans
            for (URLSpan url : urls) {
                MyURLSpan myURLSpan = new MyURLSpan(url.getURL());
                style.setSpan(myURLSpan, sp.getSpanStart(url),
                        sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            mHtmlText.setText(style);
        }
    }

    private static String toClickStr(String str) {
        HashSet<String> strings = getMatchers("#([^#]+?)#", str);
        for (String sign : strings) {
            str = str.replaceAll(sign, "<a href=\'" + sign + "\'>" + sign + "</a>");
        }

        return str;
    }

    private static HashSet<String> getMatchers(String regex, String source) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        HashSet<String> set = new HashSet<>();
        while (matcher.find()) {
            set.add(matcher.group());
        }
        return set;
    }


    /**
     * 处理点击或者更改字体颜色等
     *
     * @author dingtao
     */
    private class MyURLSpan extends ClickableSpan {
        private String mUrl;

        MyURLSpan(String url) {
            mUrl = url;
        }

        @Override
        public void onClick(View v) {
            mUrl = mUrl.toLowerCase();
            Toast.makeText(getBaseContext(), mUrl, Toast.LENGTH_LONG).show();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.BLUE);
            ds.setUnderlineText(true);
        }
    }
}
