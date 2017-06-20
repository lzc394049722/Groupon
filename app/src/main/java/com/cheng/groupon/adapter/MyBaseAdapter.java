package com.cheng.groupon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    protected List<T> mDatas;
    protected LayoutInflater inflater;
    protected Context mContext;

    public MyBaseAdapter(Context mContext,List<T> datas) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(T t) {
        mDatas.add(t);
        notifyDataSetChanged();
    }

    public void addAll(List<T> datas, boolean isClear) {
        if (isClear)
            mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

}
