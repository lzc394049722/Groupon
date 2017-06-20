package com.cheng.groupon.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class MyApp extends Application {

    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
