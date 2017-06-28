package com.cheng.groupon.ui;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.groupon.R;
import com.cheng.groupon.adapter.RflvAdapter;
import com.cheng.groupon.domain.TuanBean;
import com.cheng.groupon.util.CommonUtils;
import com.cheng.groupon.util.HttpUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    List<TuanBean.Deal> datas;
    RflvAdapter adapter;


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

        Drawable background = ResourcesCompat.getDrawable(getResources(), R.drawable.selector_footer1, null);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        background.setBounds(0, 0, size, size);
        rb_01.setCompoundDrawables(null, background, null, null);

        Drawable dra2 = ResourcesCompat.getDrawable(getResources(), R.drawable.selector_footer2, null);
        dra2.setBounds(0, 0, size, size);
        rb_02.setCompoundDrawables(null, dra2, null, null);

        Drawable dra3 = ResourcesCompat.getDrawable(getResources(), R.drawable.selector_footer3, null);
        dra3.setBounds(0, 0, size, size);
        rb_03.setCompoundDrawables(null, dra3, null, null);

        Drawable dra4 = ResourcesCompat.getDrawable(getResources(), R.drawable.selector_footer4, null);
        dra4.setBounds(0, 0, size, size);
        rb_04.setCompoundDrawables(null, dra4, null, null);

    }


    private void initListView() {
        listView = ptrlListView.getRefreshableView();
        datas = new ArrayList<>();
        adapter = new RflvAdapter(this, datas);

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
        listView.setAdapter(adapter);

        initViewPager(headViewPager);
        ptrlListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                refresh();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // Log.i("TAG:", "第一个参数：" + firstVisibleItem);
                if (firstVisibleItem == 0) {
                    cityContainer.setVisibility(View.VISIBLE);
                    ivAdd.setVisibility(View.VISIBLE);
                } else {
                    if (ivAdd.getVisibility() != View.GONE) {
                        Animator animator = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            animator = ViewAnimationUtils.createCircularReveal(layoutSearch, layoutSearch.getWidth() / 2,
                                    layoutSearch.getHeight() / 2, 0, (float) Math.hypot(layoutSearch.getWidth(), layoutSearch.getHeight()));
                            animator.setDuration(200L);
                            animator.setInterpolator(new AccelerateInterpolator());
                            animator.start();
                        }
                        cityContainer.setVisibility(View.GONE);
                        ivAdd.setVisibility(View.GONE);
                    }
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
                if (position % 3 == 0) {
                    View viewById = view.findViewById(R.id.ll_main_food_view);
                    viewById.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, BusinessActivity.class);
                            intent.putExtra("city", tvCity.getText().toString());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                            } else {
                                startActivity(intent);
                            }
                        }
                    });
                }
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

    @OnClick(R.id.rb_03)
    void find() {
        Intent intent = new Intent(this, FindActivity.class);
        intent.putExtra("from", "main");
        startActivity(intent);
    }

    private void refresh() {

        //  HttpUtil.testHttpUrlConnection();
        // HttpUtil.testVolley();
        // HttpUtil.testRetrofit();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HttpUtil.getDailyNewIdList(tvCity.getText().toString(), new Callback<TuanBean>() {
                    @Override
                    public void onResponse(Call<TuanBean> call, Response<TuanBean> response) {
                        if (response != null) {
                            List<TuanBean.Deal> deals = response.body().getDeals();
                            adapter.addAll(deals, true);

                        } else
                            //今日无新增团购内容
                            Toast.makeText(MainActivity.this, "今日无新增团购内容", Toast.LENGTH_SHORT).show();
                        ptrlListView.onRefreshComplete();
                    }

                    @Override
                    public void onFailure(Call<TuanBean> call, Throwable t) {
                        ptrlListView.onRefreshComplete();
                    }
                });
            }
        }, 1000);

    }

    @OnClick(R.id.ll_header_left_container)
    void jumpToCity() {
        startActivityForResult(new Intent(this, CityActivity.class), 101);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == 1) {
            String city = data.getStringExtra("city");
            if (!TextUtils.isEmpty(city)) {
                tvCity.setText(city);
                refresh();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
