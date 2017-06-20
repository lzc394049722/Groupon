package com.cheng.groupon.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheng.groupon.R;
import com.cheng.groupon.domain.TuanBean;
import com.cheng.groupon.util.HttpUtil;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class RflvAdapter extends MyBaseAdapter<TuanBean.Deal> {


    public RflvAdapter(Context mContext, List<TuanBean.Deal> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder vh = null;
        if (view == null) {
            view = inflater.inflate(R.layout.item_deal_layout, parent, false);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();

        }
        TuanBean.Deal deal = getItem(position);
        HttpUtil.displayImage(deal.getImage_url(), vh.ivPic);
        vh.tvTitle.setText(deal.getTitle());
        vh.tvDetail.setText(deal.getDescription());
        vh.tvPrice.setText("¥ " + deal.getCurrent_price());
        Random random = new Random();
        int count = random.nextInt(2000) + 500;
        vh.tvCount.setText("已售" + count);
        //TODO 距离 vh.tvDistance.setText("xxxx");
        if (1 == deal.getRestrictions().getIs_reservation_required()) {
            vh.ivReservation.setVisibility(View.GONE);
        } else {
            vh.ivReservation.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public class ViewHolder {

        @BindView(R.id.iv_item_deal_image)
        ImageView ivPic;
        @BindView(R.id.tv_item_deal_name)
        TextView tvTitle;
        @BindView(R.id.tv_item_deal_detail)
        TextView tvDetail;
        @BindView(R.id.tv_item_deal_distance)
        TextView tvDistance;
        @BindView(R.id.tv_item_deal_price)
        TextView tvPrice;
        @BindView(R.id.tv_item_deal_sellcount)
        TextView tvCount;
        @BindView(R.id.iv_item_status_reservation)
        ImageView ivReservation;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
