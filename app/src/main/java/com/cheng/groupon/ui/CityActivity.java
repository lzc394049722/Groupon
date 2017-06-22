package com.cheng.groupon.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cheng.groupon.R;
import com.cheng.groupon.adapter.CityAdapter;
import com.cheng.groupon.app.MyApp;
import com.cheng.groupon.domain.city.City;
import com.cheng.groupon.domain.city.CitynameBean;
import com.cheng.groupon.util.DBUtil;
import com.cheng.groupon.util.HttpUtil;
import com.cheng.groupon.util.PinYinUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityActivity extends Activity {

    List<CitynameBean> cities;
    @BindView(R.id.rv_city_list)
    RecyclerView recyclerView;
    CityAdapter adapter;
    DBUtil dbUtil;
    @BindView(R.id.ll_city_search)
    LinearLayout layoutSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);
        dbUtil = DBUtil.getInstance(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        cities = new ArrayList<>();
        adapter = new CityAdapter(this, cities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        View headerView = LayoutInflater.from(this).inflate(R.layout.include_city_header, recyclerView, false);
        adapter.addHeadView(headerView);
        adapter.setOnItemClickListener(new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                String cityName = cities.get(position).getCityName();
                Intent intent = new Intent();
                intent.putExtra("city", cityName);
                setResult(1, intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        //缓存中有数据
        if (MyApp.cities != null) {
            adapter.addAll(true, MyApp.cities);
            return;
        }
        //磁盘中有数据
        List<CitynameBean> allCityName = dbUtil.getAllCityName();
        if (allCityName != null && allCityName.size() > 0) {
            adapter.addAll(true, allCityName);
            return;
        }

        HttpUtil.getAllCities(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                List<String> list = response.body().getCities();
                final List<CitynameBean> citynameBeanList = new ArrayList<CitynameBean>();
                for (String name : list) {

                    if (!name.equals("全国") && !name.equals("其它城市") && !name.equals("点评实验室")) {
                        CitynameBean citynameBean = new CitynameBean();
                        citynameBean.setCityName(name);
                        citynameBean.setPyName(PinYinUtil.getPinYin(name));
                        citynameBean.setLetter(PinYinUtil.getLetter(name));
                        //  Log.d("TAG", "创建的CitynameBean内容： " + citynameBean);
                        citynameBeanList.add(citynameBean);
                    }
                }

                Collections.sort(citynameBeanList, new Comparator<CitynameBean>() {
                    @Override
                    public int compare(CitynameBean o1, CitynameBean o2) {
                        return o1.getPyName().compareTo(o2.getPyName());
                    }
                });

                adapter.addAll(true, citynameBeanList);

                MyApp.cities = citynameBeanList;
                //向数据库中写入城市数据
                dbUtil.insetAll(citynameBeanList);

            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.ll_city_search)
    void search() {
        startActivityForResult(new Intent(this, SearchCityActivity.class), 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == 1) {
            setResult(1, data);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
