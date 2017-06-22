package com.cheng.groupon.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.cheng.groupon.R;
import com.cheng.groupon.app.MyApp;
import com.cheng.groupon.domain.city.CitynameBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class SearchCityActivity extends Activity {

    @BindView(R.id.lv_search_activity)
    ListView listView;
    @BindView(R.id.et_search_city)
    EditText editText;
    List<String> cities;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        ButterKnife.bind(this);
        initListView();
    }

    private void initListView() {
        cities = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cityName = cities.get(position);
                Intent intent = new Intent();
                intent.putExtra("city", cityName);
                setResult(1, intent);
                finish();
            }
        });
    }

    @OnTextChanged(R.id.et_search_city)
    void search(Editable editable) {
        if (editable.length() == 0) {
            cities.clear();
            adapter.notifyDataSetChanged();
        } else {
            //必须转成大写
            searchCities(editable.toString().toUpperCase());
        }
    }

    private void searchCities(String s) {

        ArrayList<String> temp = new ArrayList<>();
        //中文
        if (s.matches("[\\u4e00-\\u9fff]+")) {
            for (CitynameBean c : MyApp.cities) {
                if (c.getCityName().contains(s)) {
                    temp.add(c.getCityName());
                }
            }
        } else {//拼音
            for (CitynameBean c : MyApp.cities) {
                if (c.getPyName().contains(s)) {
                    temp.add(c.getCityName());
                }
            }
        }
        if (temp.size() > 0) {
            cities.clear();
            cities.addAll(temp);
            adapter.notifyDataSetChanged();
        }

    }

}
