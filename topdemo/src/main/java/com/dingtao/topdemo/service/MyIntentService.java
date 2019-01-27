package com.dingtao.topdemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.logging.Handler;

/**
 * @author dingtao
 * @date 2019/1/18 15:31
 * qq:1940870847
 */
public class MyIntentService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public MyIntentService() {
        super("Test");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("dt", "IntentService的onHandleIntent方法线程：" + Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("DemoLog", "DownloadIntentService -> onStartCommand, Thread: " + Thread.currentThread().getName() + " , startId: " + startId);
        Toast.makeText(this.getBaseContext(), "哈哈", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    public static void main(String[] args) {
        System.out.println("1"=="1");
        String a="1";
        String b="1";
        System.out.println(a==b);

        String c = new String("1");
        String d = new String("1");
        //hashcode 哈希码
        System.out.println(c.equals(d));

        long price =  9223372036854775807L;
        System.out.println(price+"    max:"+Long.MAX_VALUE);

        BigDecimal bigDecimal = new BigDecimal(9223372036854775807L);
        bigDecimal = bigDecimal.add(new BigDecimal(9223372036854775807L));

        System.out.println(bigDecimal);
    }
}
