package com.cheng.groupon.ui;

import android.Manifest;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.cheng.groupon.R;
import com.cheng.groupon.app.MyApp;
import com.cheng.groupon.domain.Business.Businesses;
import com.cheng.groupon.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindActivity extends Activity {

    Businesses businesses;
    @BindView(R.id.bmapView)
    MapView mMapView;
    BaiduMap map;

    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();
    private static final int BAIDU_READ_PHONE_STATE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        businesses = (Businesses) getIntent().getSerializableExtra("business");
        ButterKnife.bind(this);
        map = mMapView.getMap();
        //更改地图的比例尺
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(17);
        map.animateMapStatus(msu);

        if ("main".equals(getIntent().getStringExtra("from"))) {

            showMyLocation();
        } else
            showAddress();

    }

    private void showMyLocation() {
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 0;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }

    private void showAddress() {

        final GeoCoder geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                if (geoCodeResult == null && geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    CommonUtils.toast("地图错误");
                } else {
                    LatLng location = geoCodeResult.getLocation();
                    MyApp.myLocate = location;
                    MarkerOptions option = new MarkerOptions();
                    option.position(location);
                    option.icon(BitmapDescriptorFactory.fromResource(R.drawable.home_scen_icon_locate));
                    map.addOverlay(option);

                    //移动中心点
                    MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(location);
                    map.animateMapStatus(msu);

                    //添加一个信息窗
                    //infowindow第三个参数是偏移量
                    TextView tv = new TextView(FindActivity.this);
                    tv.setText(businesses.getAddress());
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                    tv.setTextColor(Color.BLUE);
                    map.showInfoWindow(new InfoWindow(tv, location, -50));
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

            }
        });
        //发起地理编码查询
        GeoCodeOption option = new GeoCodeOption();
        option.address(businesses.getAddress());
        option.city(businesses.getCity());
        geoCoder.geocode(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stop();
            mLocationClient.unRegisterLocationListener(myListener);
            mLocationClient = null;
        }
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            int type = location.getLocType();
            double lng = -1;
            double lat = -1;
            if (type == 61 || type == 65 || type == 66 || type == 161) {
                lat = location.getLatitude();
                lng = location.getLongitude();
            } else {
                //失败的话就手动定位
                lng = 113.349617;
                lat = 23.136572;
            }

            LatLng latLng = new LatLng(lat, lng);
            MyApp.myLocate = latLng;
            MarkerOptions option = new MarkerOptions();
            option.position(latLng);
            option.icon(BitmapDescriptorFactory.fromResource(R.drawable.home_scen_icon_locate));
            map.addOverlay(option);

            //移动中心点
            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
            map.animateMapStatus(msu);
            //添加一个信息窗
            //infowindow第三个参数是偏移量
            TextView tv = new TextView(FindActivity.this);
            tv.setText("我在这");
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tv.setTextColor(Color.YELLOW);
            map.showInfoWindow(new InfoWindow(tv, latLng, -50));

            mLocationClient.stop();
            mLocationClient.unRegisterLocationListener(this);
            mLocationClient = null;
        }
    }
}
