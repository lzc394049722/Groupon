package com.cheng.groupon.domain.BusinessRegion;

import java.util.List;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class ResponseBusinessRegion {

    /**
     * status : OK
     * cities : [{"city_name":"上海","districts":[{"district_name":"卢湾区","neighborhoods":["淮海路","打浦桥","新天地","瑞金宾馆区"]},{"district_name":"徐汇区","neighborhoods":["徐家汇","万体馆","衡山路","复兴西路/丁香花园","肇嘉浜路沿线","音乐学院","龙华","漕河泾/田林","上海南站"]},"......"]}]
     */

    private String status;
    private List<CitiesBean> cities;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CitiesBean> getCities() {
        return cities;
    }

    public void setCities(List<CitiesBean> cities) {
        this.cities = cities;
    }


}
