package com.cheng.groupon.ui;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cheng.groupon.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @BindView(R.id.ptrl_main)
    PullToRefreshListView ptrlListView;
    @BindView(R.id.rg_main)
    RadioGroup radioGroup;
    @BindView(R.id.rb_02)
    RadioButton rb_02;
    @BindView(R.id.rb_03)
    RadioButton rb_03;
    @BindView(R.id.rb_04)
    RadioButton rb_04;
    @BindView(R.id.rb_01)
    RadioButton rb_01;
    @BindView(R.id.ll_header_left_container)
    LinearLayout cityContainer;
    @BindView(R.id.tv_header_main_city)
    TextView tvCity;
    @BindView(R.id.iv_main_add)
    ImageView ivAdd;
    @BindView(R.id.layout_main_menu)
    LinearLayout layoutMenu;
    @BindView(R.id.ll_header_main_search_bar)
    LinearLayout layoutSearch;


    ListView listView;
    List<String> datas;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        initListView();
        initRadioButton();
    }

    private void initRadioButton() {
        Drawable background = getResources().getDrawable(R.drawable.selector_footer1);
        background.setBounds(0, 0, 50, 50);
        rb_01.setCompoundDrawables(null, background, null, null);

        Drawable dra2 = getResources().getDrawable(R.drawable.selector_footer2);
        dra2.setBounds(0, 0, 50, 50);
        rb_02.setCompoundDrawables(null, dra2, null, null);

        Drawable dra3 = getResources().getDrawable(R.drawable.selector_footer3);
        dra3.setBounds(0, 0, 50, 50);
        rb_03.setCompoundDrawables(null, dra3, null, null);

        Drawable dra4 = getResources().getDrawable(R.drawable.selector_footer4);
        dra4.setBounds(0, 0, 50, 50);
        rb_04.setCompoundDrawables(null, dra4, null, null);

    }


    private void initListView() {

        listView = ptrlListView.getRefreshableView();
        datas = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);
        ptrlListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        datas.add(0, "13");
                        adapter.notifyDataSetChanged();
                        ptrlListView.onRefreshComplete();
                    }
                }, 1500);
            }
        });

        LayoutInflater inflater = LayoutInflater.from(this);
        View headViewPager = inflater.inflate(R.layout.head_root_view, listView, false);
        View headerListSquare = inflater.inflate(R.layout.header_list_square, listView, false);
        View headerListAds = inflater.inflate(R.layout.header_list_ads, listView, false);
        View headerListCategories = inflater.inflate(R.layout.header_list_categories, listView, false);
        View headerListRecommend = inflater.inflate(R.layout.header_list_recommend, listView, false);
        listView.addHeaderView(headViewPager);
        listView.addHeaderView(headerListSquare);
        listView.addHeaderView(headerListAds);
        listView.addHeaderView(headerListCategories);
        listView.addHeaderView(headerListRecommend);

        initViewPager(headViewPager);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
               // Log.i("TAG:", "第一个参数：" + firstVisibleItem);
                if(firstVisibleItem==0){
                    cityContainer.setVisibility(View.VISIBLE);
                    ivAdd.setVisibility(View.VISIBLE);
                }else {
                    cityContainer.setVisibility(View.GONE);
                    ivAdd.setVisibility(View.GONE);
                }
            }
        });


    }

    private void initViewPager(final View headViewPager) {
        ViewPager viewPager = (ViewPager) headViewPager.findViewById(R.id.vp_main);

        PagerAdapter adapter = new PagerAdapter() {

            int[] res = new int[]{R.layout.headview1, R.layout.headview2, R.layout.headview3};

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = LayoutInflater.from(MainActivity.this).inflate(res[position % 3], container, false);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        viewPager.setAdapter(adapter);

        final ImageView iv01 = (ImageView) headViewPager.findViewById(R.id.iv_header_list_icons_indicator1);
        final ImageView iv02 = (ImageView) headViewPager.findViewById(R.id.iv_header_list_icons_indicator2);
        final ImageView iv03 = (ImageView) headViewPager.findViewById(R.id.iv_header_list_icons_indicator3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                iv01.setImageResource(R.drawable.banner_dot);
                iv02.setImageResource(R.drawable.banner_dot);
                iv03.setImageResource(R.drawable.banner_dot);
                switch (position % 3) {
                    case 0:
                        iv01.setImageResource(R.drawable.banner_dot_pressed);
                        break;
                    case 1:
                        iv02.setImageResource(R.drawable.banner_dot_pressed);
                        break;
                    case 2:
                        iv03.setImageResource(R.drawable.banner_dot_pressed);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        datas.add("aa");
        datas.add("aa");
        datas.add("abn");
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.ll_header_left_container)
    void jumpToCity() {
        // TODO: 2017/6/16 0016
    }

    @OnClick(R.id.iv_main_add)
    void toggleMenu() {
        int visibility = layoutMenu.getVisibility();
        if (visibility == View.VISIBLE) {
            layoutMenu.setVisibility(View.INVISIBLE);
        } else {
            layoutMenu.setVisibility(View.VISIBLE);
        }
    }
}
