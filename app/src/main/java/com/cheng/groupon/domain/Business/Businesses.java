package com.cheng.groupon.domain.Business;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class Businesses implements Serializable{

    /**
     * business_id : 6073901
     * name : 拉亚汉堡经典餐厅
     * branch_name : 宜山路店
     * address : 徐汇区中山西路2006号
     * telephone : 021-64371252
     * city : 上海
     * regions : ["徐汇区","徐家汇","光启城"]
     * categories : ["西式简餐"]
     * latitude : 31.182503
     * longitude : 121.42582
     * avg_rating : 5
     * rating_img_url : http://i1.dpfile.com/s/i/app/api/32_5star.png
     * rating_s_img_url : http://i1.dpfile.com/s/i/app/api/16_5star.png
     * product_grade : 5
     * decoration_grade : 5
     * service_grade : 5
     * product_score : 9.1
     * decoration_score : 9
     * service_score : 9
     * avg_price : 83
     * review_count : 4324
     * review_list_url : http://lite.m.dianping.com/VQDbxhG80B
     * distance : 179
     * business_url : http://dpurl.cn/p/u8T-rWWMud
     * photo_url : http://i3.dpfile.com/pc/6216d48d7b410f80fe8a14d81d54a8f0(700x700)/thumb.jpg
     * s_photo_url : http://i2.dpfile.com/pc/6216d48d7b410f80fe8a14d81d54a8f0(278x200)/thumb.jpg
     * photo_count : 4195
     * photo_list_url : http://lite.m.dianping.com/7KMnaLWT1j
     * has_coupon : 0
     * coupon_id : 0
     * coupon_description :
     * coupon_url :
     * has_deal : 1
     * deal_count : 4
     * deals : [{"id":"1-6013503","description":"拉亚汉堡经典餐厅/费尼汉堡/堤诺比萨!仅售127元，价值150元代金券，提供免费wifi，可累积使用！无需预约，多地区多店通用!","url":"http://lite.m.dianping.com/7fAwIAX6Bi"},{"id":"1-6021687","description":"拉亚汉堡经典餐厅/费尼餐厅/堤诺比萨!仅售88元，价值100元代金券，提供免费wifi，可累积使用！多地区多店通用！\u201c贺森邦集团进入中国市场满三周年，拉亚汉堡经典餐厅、费尼餐厅、堤诺比萨推出代金券品牌共享活动\u201d！","url":"http://lite.m.dianping.com/W4b-QHZzrC"},{"id":"1-6021721","description":"拉亚汉堡经典餐厅/费尼汉堡/堤诺比萨!仅售45元，价值50元代金券，提供免费wifi，可累积使用！多地区多店通用！\u201c贺森邦集团进入中国市场满三周年，拉亚汉堡经典餐厅、费尼餐厅、堤诺比萨推出代金券品牌共享活动\u201d！","url":"http://lite.m.dianping.com/HWrxnmmeY3"},{"id":"1-6271889","description":"拉亚汉堡经典餐厅!仅售127元，价值150元代金券！无需预约，提供免费wifi！（另有其他不同售价套餐可选！）","url":"http://lite.m.dianping.com/uxn34lrZaU"}]
     * has_online_reservation : 1
     * online_reservation_url : http://dpurl.cn/p/pcl5DYmma9
     */

    private int business_id;
    private String name;
    private String branch_name;
    private String address;
    private String telephone;
    private String city;
    private double latitude;
    private double longitude;
    private int avg_rating;
    private String rating_img_url;
    private String rating_s_img_url;
    private int product_grade;
    private int decoration_grade;
    private int service_grade;
    private double product_score;
    private int decoration_score;
    private int service_score;
    private int avg_price;
    private int review_count;
    private String review_list_url;
    private int distance;
    private String business_url;
    private String photo_url;
    private String s_photo_url;
    private int photo_count;
    private String photo_list_url;
    private int has_coupon;
    private int coupon_id;
    private String coupon_description;
    private String coupon_url;
    private int has_deal;
    private int deal_count;
    private int has_online_reservation;
    private String online_reservation_url;
    private List<String> regions;
    private List<String> categories;
    private List<DealsBean> deals;

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(int avg_rating) {
        this.avg_rating = avg_rating;
    }

    public String getRating_img_url() {
        return rating_img_url;
    }

    public void setRating_img_url(String rating_img_url) {
        this.rating_img_url = rating_img_url;
    }

    public String getRating_s_img_url() {
        return rating_s_img_url;
    }

    public void setRating_s_img_url(String rating_s_img_url) {
        this.rating_s_img_url = rating_s_img_url;
    }

    public int getProduct_grade() {
        return product_grade;
    }

    public void setProduct_grade(int product_grade) {
        this.product_grade = product_grade;
    }

    public int getDecoration_grade() {
        return decoration_grade;
    }

    public void setDecoration_grade(int decoration_grade) {
        this.decoration_grade = decoration_grade;
    }

    public int getService_grade() {
        return service_grade;
    }

    public void setService_grade(int service_grade) {
        this.service_grade = service_grade;
    }

    public double getProduct_score() {
        return product_score;
    }

    public void setProduct_score(double product_score) {
        this.product_score = product_score;
    }

    public int getDecoration_score() {
        return decoration_score;
    }

    public void setDecoration_score(int decoration_score) {
        this.decoration_score = decoration_score;
    }

    public int getService_score() {
        return service_score;
    }

    public void setService_score(int service_score) {
        this.service_score = service_score;
    }

    public int getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(int avg_price) {
        this.avg_price = avg_price;
    }

    public int getReview_count() {
        return review_count;
    }

    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }

    public String getReview_list_url() {
        return review_list_url;
    }

    public void setReview_list_url(String review_list_url) {
        this.review_list_url = review_list_url;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getBusiness_url() {
        return business_url;
    }

    public void setBusiness_url(String business_url) {
        this.business_url = business_url;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getS_photo_url() {
        return s_photo_url;
    }

    public void setS_photo_url(String s_photo_url) {
        this.s_photo_url = s_photo_url;
    }

    public int getPhoto_count() {
        return photo_count;
    }

    public void setPhoto_count(int photo_count) {
        this.photo_count = photo_count;
    }

    public String getPhoto_list_url() {
        return photo_list_url;
    }

    public void setPhoto_list_url(String photo_list_url) {
        this.photo_list_url = photo_list_url;
    }

    public int getHas_coupon() {
        return has_coupon;
    }

    public void setHas_coupon(int has_coupon) {
        this.has_coupon = has_coupon;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_description() {
        return coupon_description;
    }

    public void setCoupon_description(String coupon_description) {
        this.coupon_description = coupon_description;
    }

    public String getCoupon_url() {
        return coupon_url;
    }

    public void setCoupon_url(String coupon_url) {
        this.coupon_url = coupon_url;
    }

    public int getHas_deal() {
        return has_deal;
    }

    public void setHas_deal(int has_deal) {
        this.has_deal = has_deal;
    }

    public int getDeal_count() {
        return deal_count;
    }

    public void setDeal_count(int deal_count) {
        this.deal_count = deal_count;
    }

    public int getHas_online_reservation() {
        return has_online_reservation;
    }

    public void setHas_online_reservation(int has_online_reservation) {
        this.has_online_reservation = has_online_reservation;
    }

    public String getOnline_reservation_url() {
        return online_reservation_url;
    }

    public void setOnline_reservation_url(String online_reservation_url) {
        this.online_reservation_url = online_reservation_url;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<DealsBean> getDeals() {
        return deals;
    }

    public void setDeals(List<DealsBean> deals) {
        this.deals = deals;
    }

    public static class DealsBean implements Serializable{
        /**
         * id : 1-6013503
         * description : 拉亚汉堡经典餐厅/费尼汉堡/堤诺比萨!仅售127元，价值150元代金券，提供免费wifi，可累积使用！无需预约，多地区多店通用!
         * url : http://lite.m.dianping.com/7fAwIAX6Bi
         */

        private String id;
        private String description;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public String toString() {
        return "Businesses{" +
                "business_id=" + business_id +
                ", name='" + name + '\'' +
                ", branch_name='" + branch_name + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", city='" + city + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", avg_rating=" + avg_rating +
                ", rating_img_url='" + rating_img_url + '\'' +
                ", rating_s_img_url='" + rating_s_img_url + '\'' +
                ", product_grade=" + product_grade +
                ", decoration_grade=" + decoration_grade +
                ", service_grade=" + service_grade +
                ", product_score=" + product_score +
                ", decoration_score=" + decoration_score +
                ", service_score=" + service_score +
                ", avg_price=" + avg_price +
                ", review_count=" + review_count +
                ", review_list_url='" + review_list_url + '\'' +
                ", distance=" + distance +
                ", business_url='" + business_url + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", s_photo_url='" + s_photo_url + '\'' +
                ", photo_count=" + photo_count +
                ", photo_list_url='" + photo_list_url + '\'' +
                ", has_coupon=" + has_coupon +
                ", coupon_id=" + coupon_id +
                ", coupon_description='" + coupon_description + '\'' +
                ", coupon_url='" + coupon_url + '\'' +
                ", has_deal=" + has_deal +
                ", deal_count=" + deal_count +
                ", has_online_reservation=" + has_online_reservation +
                ", online_reservation_url='" + online_reservation_url + '\'' +
                ", regions=" + regions +
                ", categories=" + categories +
                ", deals=" + deals +
                '}';
    }
}

