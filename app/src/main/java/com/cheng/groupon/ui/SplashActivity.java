package com.cheng.groupon.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cheng.groupon.C;
import com.cheng.groupon.R;
import com.cheng.groupon.util.CommonUtils;
import com.cheng.groupon.util.SPUtils;

public class SplashActivity extends Activity {

    SPUtils spUtils;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //界面停留几秒钟
        spUtils = new SPUtils(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //根据是否是第一次使用进行相应的界面跳转
                if (spUtils.isFirst()) {
                    //新手指导页
                    CommonUtils.startActivity(SplashActivity.this, GuideActivity.class);
                    spUtils.setFirst(false);
                } else {
                    //向主页面跳转
                    CommonUtils.startActivity(SplashActivity.this, MainActivity.class);
                }
                finish();
            }
        }, 1500);

    }
}
