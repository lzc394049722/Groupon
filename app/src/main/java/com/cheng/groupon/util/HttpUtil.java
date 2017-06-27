package com.cheng.groupon.util;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.cheng.groupon.C;
import com.cheng.groupon.R;
import com.cheng.groupon.app.MyApp;
import com.cheng.groupon.domain.Business.ResponseBusiness;
import com.cheng.groupon.domain.BusinessRegion.ResponseBusinessRegion;
import com.cheng.groupon.domain.city.City;
import com.cheng.groupon.domain.DailyNewIdList;
import com.cheng.groupon.domain.TuanBean;
import com.squareup.picasso.Picasso;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Administrator on 2017/6/19 0019.
 */


public class HttpUtil {
//"http://api.dianping.com/v1/business/find_businesses"
//App Key：49814079
//App Secret：90e3438a41d646848033b6b9d461ed54

    /**
     * @param url
     * @param params
     * @return
     */
    public static String getURL(String url, Map<String, String> params) {

        String result;
        String sign = getSign(C.APP_KEY, C.APP_SECRET, params);
        String query = getQuery(C.APP_KEY, C.APP_SECRET, sign, params);
        result = url + "?" + query;
        return result;
    }


    /**
     * @param appKey
     * @param appSecret
     * @param params
     * @return
     */
    public static String getQuery(String appKey, String appSecret, String sign, Map<String, String> params) {
        try {
            // 添加签名
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("appkey=").append(appKey).append("&sign=").append(sign);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                stringBuilder.append('&').append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), "utf-8"));
            }
            String queryString = stringBuilder.toString();
            return queryString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("使用了不正确的解码名称！");
        }

    }


    /**
     * 获取签名
     *
     * @param appKey
     * @param appSecret
     * @param params
     */
    public static String getSign(String appKey, String appSecret, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();

        // 对参数名进行字典排序
        String[] keyArray = params.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        // 拼接有序的参数名-值串
        stringBuilder.append(appKey);
        for (String key : keyArray) {
            stringBuilder.append(key).append(params.get(key));
        }
        String codes = stringBuilder.append(appSecret).toString();
        // SHA-1签名
        // For Android
        String sign = new String(Hex.encodeHex(DigestUtils.sha(codes))).toUpperCase();

        return sign;
    }


    public static void testHttpUrlConnection() {

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("city", "上海");
        paramMap.put("category", "美食");
        paramMap.put("region", "长宁区");
        paramMap.put("limit", "20");
        paramMap.put("keyword", "泰国菜");
        paramMap.put("format", "json");
        final String url = getURL("http://api.dianping.com/v1/business/find_businesses", paramMap);

        new Thread() {
            @Override
            public void run() {
                try {
                    URL u = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int responseCode = conn.getResponseCode();

                    if (responseCode == 200) {
                        InputStream inputStream = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                        reader.close();

                        Log.i("TAG", sb.toString());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public static void testVolley() {

        VolleyClient.getInstance().test();
    }

    public static void testRetrofit() {

        RetrofitClient.getInstance().test();
    }


    public static void getDailyNewIdList(String city, final Callback<TuanBean> mCallback) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(new Date());
        Map<String, String> params = new HashMap<>();
        params.put("city", city);
        params.put("date", format);

        RetrofitClient.getInstance().getDailyNewIdList(params, new Callback<DailyNewIdList>() {
            @Override
            public void onResponse(Call<DailyNewIdList> call, retrofit2.Response<DailyNewIdList> response) {

                List<String> id_list = response.body().getId_list();
                getGroupOnBatchDeals(id_list, mCallback);


            }

            @Override
            public void onFailure(Call<DailyNewIdList> call, Throwable t) {

            }
        });
    }

    private static void getGroupOnBatchDeals(List<String> id_list, Callback<TuanBean> callback) {

        String idList = getIdListStr(id_list);
        if (idList != null && idList.length() > 0)
            RetrofitClient.getInstance().getGroupOnBatchDeals(idList, callback);
        else
            callback.onResponse(null, null);
    }

    private static String getIdListStr(List<String> id_list) {

        int size = id_list.size();
        if (size == 0) return null;
        if (size > 40) size = 40;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(id_list.get(i)).append(",");
        }
        String substring = sb.substring(0, sb.length() - 1);
        return substring;
    }

    public static void loadImage(String url, ImageView iv) {
        VolleyClient.getInstance().loadImage(url, iv);
    }

    public static void displayImage(String url, ImageView iv) {
        Picasso.with(MyApp.mContext)
                .load(url)
                .placeholder(R.drawable.wedding_photo_default)
                .error(R.drawable.wedding_photo_error)
                .into(iv);
    }

    public static void getAllCities(Callback<City> callback) {
        RetrofitClient.getInstance().getAllCities(callback);
    }

    public static void getBusiness(String city, String region, Callback<ResponseBusiness> callback) {

        Map<String, String> params = new HashMap<>();
        params.put("city", city);
        params.put("category", "美食");
        if (!TextUtils.isEmpty(region)) {
            params.put("region", region);
        }
        RetrofitClient.getInstance().getBusiness(params, callback);
    }

    public static void getDistricts(String city, Callback<ResponseBusinessRegion> callback) {

        Map<String, String> params = new HashMap<>();
        params.put("city", city);
        RetrofitClient.getInstance().getDistricts(params, callback);
    }

    public static void getComment(final String url, final OnResponseListener<Document> listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Document document = Jsoup.connect(url).get();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onResponse(document);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public interface OnResponseListener<T> {
        void onResponse(T t);
    }

}