package com.cheng.groupon.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cheng.groupon.R;
import com.cheng.groupon.app.MyApp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class VolleyClient {

    private static VolleyClient instance = null;

    ImageLoader imageLoader;

    public static VolleyClient getInstance() {
        if (instance == null) {
            synchronized (VolleyClient.class) {
                if (instance == null)
                    instance = new VolleyClient();
            }
        }
        return instance;
    }


    RequestQueue queue;

    private VolleyClient() {
        queue = Volley.newRequestQueue(MyApp.mContext);
        imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {

            LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / 8)) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    private VolleyClient(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void test() {
        //request对象
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("city", "上海");
        paramMap.put("category", "美食");
        paramMap.put("region", "长宁区");
        paramMap.put("limit", "20");
        paramMap.put("keyword", "泰国菜");
        paramMap.put("format", "json");
        String url = HttpUtil.getURL("http://api.dianping.com/v1/business/find_businesses", paramMap);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("TAG", s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        //请求对象放到队列中
        queue.add(request);
    }

    /**
     * 显示网络中的一幅图片
     *
     * @param url 图片在网络中的地址
     * @param iv  显示图片的控件
     */
    public void loadImage(String url, ImageView iv) {

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv, R.drawable.bucket_no_picture, R.drawable.bucket_no_picture);
        imageLoader.get(url, listener);
    }

    public void getComment(String url, Response.Listener<String> listener) {

        StringRequest request = new StringRequest(url, listener, null);
        
        queue.add(request);
    }
}
