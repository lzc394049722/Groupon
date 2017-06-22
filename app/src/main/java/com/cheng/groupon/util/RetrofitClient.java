package com.cheng.groupon.util;

import android.util.Log;

import com.cheng.groupon.C;
import com.cheng.groupon.domain.city.City;
import com.cheng.groupon.domain.DailyNewIdList;
import com.cheng.groupon.domain.TuanBean;

import com.cheng.groupon.util.service.NetService;


import java.io.IOException;
import java.util.HashMap;

import java.util.Map;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class RetrofitClient {

    private final OkHttpClient okHttpClient;
    private Retrofit mRetrofit;
    private NetService netService;
    private static RetrofitClient instance = null;

    public static RetrofitClient getInstance() {

        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null)
                    instance = new RetrofitClient();
            }
        }
        return instance;
    }

    private RetrofitClient() {
        okHttpClient = new OkHttpClient.Builder().addInterceptor(new MyIntercepter()).build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(C.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        netService = mRetrofit.create(NetService.class);
    }

    public void test() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("city", "北京");
        paramMap.put("category", "美食");
        paramMap.put("limit", "10");
        paramMap.put("keyword", "泰国菜");
        Call<String> call = netService.test(C.APP_KEY, HttpUtil.getSign(C.APP_KEY, C.APP_SECRET, paramMap), paramMap);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String strText = response.body();
                Log.i("TAG", strText);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.i("TAG", "retrofit:ERROR-->" + "请求失败");
            }
        });
    }

    /**
     * 必选参数city，date
     *
     * @param params
     */
    public void getDailyNewIdList(Map<String, String> params, Callback<DailyNewIdList> callback) {


        Call<DailyNewIdList> call = netService.getDailyNewIdList(params);
        call.enqueue(callback);
    }

    public void getGroupOnBatchDeals(String deal_ids, Callback<TuanBean> callback) {


        Map<String, String> params = new HashMap<>();
        params.put("deal_ids", deal_ids);
        Call<TuanBean> call = netService.getGroupOnBatchDeals(deal_ids);
        call.enqueue(callback);
    }

    public class MyIntercepter implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            Request request = chain.request();

            HttpUrl url = request.url();

            Map<String, String> params = new HashMap<>();


            Set<String> set = url.queryParameterNames();

            for (String key : set) {
                params.put(key, url.queryParameter(key));
            }


            String sign = HttpUtil.getSign(C.APP_KEY, C.APP_SECRET, params);
            //字符串形式的http://baseurl/deal/get_daily_new_id_list?city=xxx&date=xxx
            String urlString = url.toString();
            Log.d("TAG", "原始请求路径------> " + urlString);

            StringBuilder sb = new StringBuilder(urlString);
            if (set.size() == 0) {
                sb.append("?");
            } else {
                sb.append("&");
            }
            sb.append("appkey=").append(C.APP_KEY);
            sb.append("&").append("sign=").append(sign);
            Log.d("TAG", "新的请求路径------> " + sb.toString());
            Request build = new Request.Builder().url(sb.toString()).build();
            return chain.proceed(build);
        }
    }


    public void getAllCities(Callback<City> callback) {

        Call<City> call = netService.getCitys();
        call.enqueue(callback);
    }

}
