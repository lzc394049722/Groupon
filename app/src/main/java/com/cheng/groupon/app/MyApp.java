package com.cheng.groupon.app;

import android.app.Application;
import android.content.Context;

import com.cheng.groupon.domain.city.CitynameBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class MyApp extends Application {

    public static Context mContext;
    public  static List<CitynameBean> cities;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
