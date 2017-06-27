package com.cheng.groupon.domain;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class Comment {
    /**
     * 头像
     */
    String avatar;
    String name;
    String date;
    String rating;
    String price;
    String content;//评论正文
    String []imgs;//配图，最多显示三张

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", rating='" + rating + '\'' +
                ", price='" + price + '\'' +
                ", content='" + content + '\'' +
                ", imgs=" + Arrays.toString(imgs) +
                '}';
    }
}
