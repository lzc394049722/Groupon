package com.cheng.groupon.util.service;

import com.cheng.groupon.domain.Business.ResponseBusiness;
import com.cheng.groupon.domain.BusinessRegion.ResponseBusinessRegion;
import com.cheng.groupon.domain.city.City;
import com.cheng.groupon.domain.DailyNewIdList;
import com.cheng.groupon.domain.TuanBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public interface NetService {

    @GET("business/find_businesses")
    Call<String> test(@Query("appkey") String appkey, @Query("sign") String sign, @QueryMap Map<String, String> params);

    @GET("deal/get_daily_new_id_list")
    Call<DailyNewIdList> getDailyNewIdList(@QueryMap Map<String, String> params);

    @GET("deal/get_batch_deals_by_id")
    Call<TuanBean> getGroupOnBatchDeals(@Query("deal_ids") String deal_ids);

    @GET("metadata/get_cities_with_businesses")
    Call<City> getCitys();

    @GET("business/find_businesses")
    Call<ResponseBusiness> getBusiness(@QueryMap Map<String, String> map);

    @GET("metadata/get_regions_with_businesses")
    Call<ResponseBusinessRegion> getDistricts(@QueryMap Map<String, String> params);
}
