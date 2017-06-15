package com.cheng.groupon.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cheng.groupon.fragment.GuideFragment1;
import com.cheng.groupon.fragment.GuideFragment2;
import com.cheng.groupon.fragment.GuideFragment3;
import com.cheng.groupon.fragment.GuideFragment4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<Fragment>();
        fragments.add(new GuideFragment1());
        fragments.add(new GuideFragment2());
        fragments.add(new GuideFragment3());
        fragments.add(new GuideFragment4());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
