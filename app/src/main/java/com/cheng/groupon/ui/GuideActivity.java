package com.cheng.groupon.ui;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.cheng.groupon.R;
import com.cheng.groupon.adapter.MyPagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends FragmentActivity {

    private MyPagerAdapter adapter;
    @BindView(R.id.vp_guide_content)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initViewPager();
        setIndicatorStylr();
        initEvent();
    }

    private void initEvent() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // TODO: 2017/6/15 0015
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    indicator.setVisibility(View.GONE);
                } else {
                    indicator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
             // TODO: 2017/6/15 0015
            }
        });
    }

    private void setIndicatorStylr() {
        /**
         * lhdpi   120px/1 inch
         * mhdpi   160px/1 inch
         * hhdpi   240px/1 inch
         * xhhdpi   320px/1 inch
         * xxhdpi   480px/1 inch
         *
         * dp绝对单位 160dp = 1inch
         */

        //另一种获取屏幕密度
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

        final float density = getResources().getDisplayMetrics().density;//密度
        indicator.setRadius(4 * density);
        indicator.setPageColor(0xFFCCCCCC);
        indicator.setFillColor(0xFFF5744C);
    }

    private void initViewPager() {
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
    }
}
