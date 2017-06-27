package com.cheng.groupon.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cheng.groupon.R;
import com.cheng.groupon.adapter.BusinessAdapter;
import com.cheng.groupon.domain.Business.Businesses;
import com.cheng.groupon.domain.Business.ResponseBusiness;
import com.cheng.groupon.domain.BusinessRegion.DistrictsBean;
import com.cheng.groupon.domain.BusinessRegion.ResponseBusinessRegion;
import com.cheng.groupon.domain.city.City;
import com.cheng.groupon.util.CommonUtils;
import com.cheng.groupon.util.HttpUtil;
import com.cheng.groupon.util.SPUtils;
import com.cheng.groupon.view.MyBanner;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessActivity extends Activity {
    @BindView(R.id.tv_title_bar_left)
    TextView tvTitleLeft;
    @BindView(R.id.tv_title_bar_right)
    TextView tvTitleRight;
    @BindView(R.id.iv_title_bar_search)
    ImageView ivTitleSearch;
    @BindView(R.id.ptrv_business_list)
    PullToRefreshListView ptrlListView;
    @BindView(R.id.iv_business_loading)
    ImageView ivLoading;
    @BindView(R.id.district_layout)
    View districtLayout;
    @BindView(R.id.lv_business_select_left)
    ListView leftListView;
    @BindView(R.id.lv_business_select_right)
    ListView rightListView;
    @BindView(R.id.tv_business_textview1)
    TextView tvRegion;

    List<String> leftDatas;
    List<String> rightDatas;

    ArrayAdapter<String> leftAdapter;
    ArrayAdapter<String> rightAdapter;

    ListView listView;
    List<Businesses> datas;
    private BusinessAdapter adapter;
    private SPUtils spUtils;
    private String city;
    private List<DistrictsBean> districts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        ButterKnife.bind(this);
        city = getIntent().getStringExtra("city");
        getWindow().setEnterTransition(new Explode().setDuration(1000));
        initView();
    }

    private void initView() {
        initTitleBar();
        initListView();
    }

    private void initListView() {
        listView = ptrlListView.getRefreshableView();
        datas = new ArrayList<>();
        adapter = new BusinessAdapter(this, datas);
        spUtils = new SPUtils(this);
        if (!spUtils.isCloseBanner()) {
            final MyBanner banner = new MyBanner(this, null);
            banner.setOnCloseBannerListener(new MyBanner.OnCloseBannerListener() {
                @Override
                public void onClose() {
                    spUtils.setCloseBanner(true);
                    listView.removeHeaderView(banner);
                }
            });
            listView.addHeaderView(banner);
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = 0;
                if(spUtils.isCloseBanner()){
                    i = position-1;
                }else {
                    i = position-2;//有头部，加上refresh，一共两个
                }
                Businesses item = adapter.getItem(i);
                Intent intent = new Intent(BusinessActivity.this, DetailActivity.class);
                intent.putExtra("business",item);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(BusinessActivity.this).toBundle());
            }
        });
        AnimationDrawable d = (AnimationDrawable) ivLoading.getDrawable();
        d.start();
        listView.setEmptyView(ivLoading);
        //初始化左右的listview
        leftDatas = new ArrayList<String>();
        leftAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, leftDatas);
        leftListView.setAdapter(leftAdapter);

        rightDatas = new ArrayList<String>();
        rightAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rightDatas);
        rightListView.setAdapter(rightAdapter);
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DistrictsBean districtsBean = districts.get(position);
                List<String> neighborhoods = new ArrayList<>(districtsBean.getNeighborhoods());
                neighborhoods.add(0, "全部" + districtsBean.getDistrict_name());
                rightDatas.clear();
                rightDatas.addAll(neighborhoods);
                rightAdapter.notifyDataSetChanged();

            }
        });

        rightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String region = rightDatas.get(position);
                if (position == 0) {
                    region = region.substring(2, region.length());
                }
                tvRegion.setText(region);
                districtLayout.setVisibility(View.INVISIBLE);
                adapter.removeAll();
                HttpUtil.getBusiness(city, region, new Callback<ResponseBusiness>() {
                    @Override
                    public void onResponse(Call<ResponseBusiness> call, Response<ResponseBusiness> response) {
                        List<Businesses> list = response.body().getBusinesses();
                        adapter.addAll(list, true);
                    }

                    @Override
                    public void onFailure(Call<ResponseBusiness> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        HttpUtil.getBusiness(city, null, new Callback<ResponseBusiness>() {
            @Override
            public void onResponse(Call<ResponseBusiness> call, Response<ResponseBusiness> response) {
                List<Businesses> list = response.body().getBusinesses();
                adapter.addAll(list, true);
            }

            @Override
            public void onFailure(Call<ResponseBusiness> call, Throwable t) {

            }
        });
        HttpUtil.getDistricts(city, new Callback<ResponseBusinessRegion>() {
            @Override
            public void onResponse(Call<ResponseBusinessRegion> call, Response<ResponseBusinessRegion> response) {
                districts = response.body().getCities().get(0).getDistricts();
                List<String> districtNames = new ArrayList<String>();
                for (int i = 0; i < districts.size(); i++) {
                    DistrictsBean districtsBean = districts.get(i);
                    districtNames.add(districtsBean.getDistrict_name());
                }

                leftDatas.clear();
                leftDatas.addAll(districtNames);
                leftAdapter.notifyDataSetChanged();
                leftListView.setSelection(0);

                List<String> neighborhoods = districts.get(0).getNeighborhoods();

                String districtName = districts.get(0).getDistrict_name();
                neighborhoods.add(0, "全部" + districtName);

                rightDatas.clear();
                rightDatas.addAll(neighborhoods);
                rightAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBusinessRegion> call, Throwable t) {

            }
        });
    }

    private void initTitleBar() {
        tvTitleLeft.setText("商户");
        tvTitleRight.setText("闪惠团购");
        ivTitleSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getWindow().setExitTransition(new Explode().setDuration(1000));
    }

    @OnClick(R.id.tv_business_textview1)
    public void showDistricts(View view) {
        if (districtLayout.getVisibility() != View.VISIBLE) {
            districtLayout.setVisibility(View.VISIBLE);
        } else {
            districtLayout.setVisibility(View.INVISIBLE);
        }
    }
}
