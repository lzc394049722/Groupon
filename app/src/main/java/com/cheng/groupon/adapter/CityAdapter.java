package com.cheng.groupon.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.cheng.groupon.R;
import com.cheng.groupon.domain.city.CitynameBean;

import java.util.EmptyStackException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> implements SectionIndexer {

    private Context mContext;
    private LayoutInflater inflater;
    private List<CitynameBean> cities;
    private View headView;
    private static final int HEAD = 0;
    private static final int ITEM = 1;

    public CityAdapter(Context mContext, List<CitynameBean> datas) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        cities = datas;
    }

    public void addAll(boolean isClear, List<CitynameBean> datas) {
        if (isClear)
            cities.clear();
        cities.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HEAD) {
            return new ViewHolder(headView);
        }

        View view = inflater.inflate(R.layout.item_city_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityAdapter.ViewHolder holder, final int position) {

        if (headView != null && position == 0) {
            return;
        }

        final int dataIndex = getDataIndex(position);
        CitynameBean citynameBean = cities.get(dataIndex);
        holder.tvName.setText(citynameBean.getCityName());
        holder.tvLable.setText(citynameBean.getLetter() + "");
        if (dataIndex == getPositionForSection(getSectionForPosition(dataIndex))) {
            holder.tvLable.setVisibility(View.VISIBLE);
        } else {
            holder.tvLable.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, dataIndex);
            }
        });
    }

    public View getHeaderView() {
        return headView;
    }

    private int getDataIndex(int position) {
        return headView == null ? position : position - 1;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    private OnItemClickListener listener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return cities.size();
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {

        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getLetter() == sectionIndex) {
                return i;
            }
        }
        //找不到时返回
        //此值该如何返回？？反正就是不存在的值
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return cities.get(position).getLetter();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.tv_item_city_lable)
        TextView tvLable;
        @Nullable
        @BindView(R.id.tv_item_city_name)
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (headView != null) {
            //有头部时处理
            if (position == 0)
                //头部布局
                return HEAD;
            else return ITEM;
        } else
            //无头部处理
            return ITEM;
    }

    public void addHeadView(View headView) {
        if (this.headView == null) {
            this.headView = headView;
            notifyItemChanged(0);
        } else {
            throw new RuntimeException("不允许添加多个头部");
        }
    }


}
