package com.cheng.groupon.ui;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Slide;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.cheng.groupon.R;
import com.cheng.groupon.adapter.CommentAdapter;
import com.cheng.groupon.domain.Business.Businesses;
import com.cheng.groupon.domain.Comment;
import com.cheng.groupon.util.CommonUtils;
import com.cheng.groupon.util.HttpUtil;
import com.cheng.groupon.view.CustomerListview;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends Activity {
    @BindView(R.id.iv_business_item)
    ImageView ivHead;
    @BindView(R.id.tv_business_item_name)
    TextView tvBusinessName;
    @BindView(R.id.tv_business_item_avg_price)
    TextView tvPrice;
    @BindView(R.id.tv_business_item_info)
    TextView tvInfo;
    @BindView(R.id.iv_business_item_rating)
    ImageView ivRating;
    @BindView(R.id.tv_business_total_comment)
    TextView tvTotalComment;
    @BindView(R.id.tv_detail_locate)
    TextView tvLocate;
    @BindView(R.id.tv_detail_phone)
    TextView tvPhone;
    @BindView(R.id.lv_detail_comments)
    CustomerListview listview;
    @BindView(R.id.sv_detail)
    ScrollView scrollView;

    Businesses businesses;
    int[] stars = new int[]{R.drawable.star10,
            R.drawable.star20,
            R.drawable.star30,
            R.drawable.star35,
            R.drawable.star40,
            R.drawable.star45,
            R.drawable.star50};

    List<Comment> datas;
    CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide().setDuration(1500).setInterpolator(new AccelerateInterpolator()));
        }
        businesses = (Businesses) getIntent().getSerializableExtra("business");
        Log.d("TAG", businesses.toString());
        ButterKnife.bind(this);
        initView();
        initListView();
    }

    private void initListView() {

    }

    private void initView() {
        HttpUtil.loadImage(businesses.getPhoto_url(), ivHead);

        String name = businesses.getName().substring(0, businesses.getName().indexOf("("));
        if (!TextUtils.isEmpty(businesses.getBranch_name())) {
            name = name + "(" + businesses.getBranch_name() + ")";
        }
        tvBusinessName.setText(name);

        Random rand = new Random();
        int idx = rand.nextInt(7);
        ivRating.setImageResource(stars[idx]);
        int price = rand.nextInt(100) + 50;
        tvPrice.setText("￥" + price + "/人");
        int totalComment = rand.nextInt(200) + 20;
        tvTotalComment.setText(totalComment + "条");

        StringBuilder sb = new StringBuilder();

        for (int j = 0; j < businesses.getRegions().size(); j++) {

            if (j == 0) {
                sb.append(businesses.getRegions().get(j));
            } else {
                sb.append("/").append(businesses.getRegions().get(j));
            }
            if (j == 1) break;
        }
        sb.append("  ");
        for (int j = 0; j < businesses.getCategories().size(); j++) {
            if (j == 0) {
                sb.append(businesses.getCategories().get(j));
            } else {
                sb.append("/").append(businesses.getCategories().get(j));
            }
        }
        tvInfo.setText(sb.toString());

        tvLocate.setText(businesses.getAddress());
        tvPhone.setText(businesses.getTelephone());
    }

    @OnClick(R.id.tv_detail_locate)
    void locate() {
        Intent intent = new Intent(this, FindActivity.class);
        intent.putExtra("business", businesses);
        intent.putExtra("from", "detail");
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        datas = new ArrayList<>();
        adapter = new CommentAdapter(this, datas);
        listview.setAdapter(adapter);
        scrollView.smoothScrollTo(0, 10);

        HttpUtil.getComment(businesses.getReview_list_url(), new HttpUtil.OnResponseListener<Document>() {
            @Override
            public void onResponse(Document document) {
                List<Comment> comments = new ArrayList<Comment>();
                Elements elements = document.select("div[class=comment-list] li[data-id]");
                for (Element e : elements) {
                    Comment comment = new Comment();
                    Element picElement = e.select("div[class=pic] img").get(0);
                    comment.setAvatar(picElement.attr("src"));
                    comment.setName(picElement.attr("title"));

                    Elements perElement = e.select("div[user-info] span[class=comm-per]");
                    if (perElement.size() > 0) {
                        comment.setPrice(perElement.get(0).text().split(" ")[1] + "/人");
                    } else {
                        comment.setPrice("");
                    }

                    Element starElement = e.select("div[class=user-info] span[title]").get(0);

                    comment.setRating(starElement.attr("class").split("-")[3]);

                    Element contentElement = e.select("div[class=J_brief-cont]").get(0);
                    comment.setContent(contentElement.text());

                    Elements photoElements = e.select("div[class=shop-photo] img");
                    int size = photoElements.size();
                    if (size > 3) {
                        size = 3;
                    }
                    String[] pics = new String[size];
                    for (int i = 0; i < size; i++) {
                        pics[i] = photoElements.get(i).attr("src");
                    }
                    comment.setImgs(pics);

                    Element timeElement = e.select("div[class=misc-info] span[class=time]").get(0);
                    comment.setDate(timeElement.text());
                    comments.add(comment);
                }
                Log.i("TAG", "Comments内容: " + comments);
                adapter.addAll(comments, true);
            }
        });

    /*    HttpUtil.getCommentByVolley(businesses.getReview_list_url(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("TAG", "Comments内容: " + s);
            }
        });*/


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Slide().setDuration(1500).setInterpolator(new AccelerateInterpolator()));
        }
    }
}
