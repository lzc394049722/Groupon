package com.cheng.groupon.app;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.cheng.groupon.domain.city.CitynameBean;
import com.cheng.groupon.util.SPUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class MyApp extends Application {

    public static Context mContext;
    public static List<CitynameBean> cities;

    public static LatLng myLocate;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        SPUtils spUtil = new SPUtils(this);
        spUtil.setCloseBanner(false);
        SDKInitializer.initialize(this);
    }
}
