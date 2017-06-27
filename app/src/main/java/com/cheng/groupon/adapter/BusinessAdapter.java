package com.cheng.groupon.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheng.groupon.R;
import com.cheng.groupon.domain.Business.Businesses;
import com.cheng.groupon.util.HttpUtil;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class BusinessAdapter extends MyBaseAdapter<Businesses> {

    public BusinessAdapter(Context mContext, List<Businesses> datas) {
        super(mContext, datas);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder vh;
        if (view == null) {
            view = inflater.inflate(R.layout.item_business_layout, parent, false);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        Businesses item = getItem(position);

        HttpUtil.loadImage(item.getPhoto_url(), vh.ivPic);

        String name = item.getName().substring(0, item.getName().indexOf("("));
        if (!TextUtils.isEmpty(item.getBranch_name())) {
            name = name + "(" + item.getBranch_name() + ")";
        }
        vh.tvName.setText(name);

        int[] stars = new int[]{R.drawable.star10,
                R.drawable.star20,
                R.drawable.star30,
                R.drawable.star35,
                R.drawable.star40,
                R.drawable.star45,
                R.drawable.star50};
        Random rand = new Random();
        int idx = rand.nextInt(7);
        vh.ivRating.setImageResource(stars[idx]);

        int price = rand.nextInt(100) + 50;

        vh.tvPrice.setText("￥" + price + "/人");

        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < item.getRegions().size(); j++) {

            if (j == 0) {
                sb.append(item.getRegions().get(j));
            } else {
                sb.append("/").append(item.getRegions().get(j));
            }
            if (j == 1) break;
        }
        vh.tvlocate.setText(sb.toString());
        sb = new StringBuilder();
        for (int j = 0; j < item.getCategories().size(); j++) {
            if (j == 0) {
                sb.append(item.getCategories().get(j));
            } else {
                sb.append("/").append(item.getCategories().get(j));
            }
        }

        vh.tvInfo.setText(sb.toString());


        return view;
    }

    public class ViewHolder {

        @BindView(R.id.iv_business_item)
        ImageView ivPic;
        @BindView(R.id.tv_business_item_name)
        TextView tvName;
        @BindView(R.id.iv_business_item_rating)
        ImageView ivRating;
        @BindView(R.id.tv_business_item_avg_price)
        TextView tvPrice;
        @BindView(R.id.tv_business_item_info)
        TextView tvInfo;
        @BindView(R.id.tv_business_item_distance)
        TextView tvDistance;
        @BindView(R.id.tv_item_business_locate)
        TextView tvlocate;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }


    }
}
