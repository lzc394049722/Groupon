package com.cheng.groupon.domain.BusinessRegion;

import java.util.List;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class DistrictsBean {

    /**
     * district_name : 卢湾区
     * neighborhoods : ["淮海路","打浦桥","新天地","瑞金宾馆区"]
     */

    private String district_name;
    private List<String> neighborhoods;

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public List<String> getNeighborhoods() {
        return neighborhoods;
    }

    public void setNeighborhoods(List<String> neighborhoods) {
        this.neighborhoods = neighborhoods;
    }

    @Override
    public String toString() {
        return "DistrictsBean{" +
                "district_name='" + district_name + '\'' +
                ", neighborhoods=" + neighborhoods +
                '}';
    }
}

