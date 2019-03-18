package com.chejdj.wanandroid.db.entity;

import com.chejdj.wanandroid.network.bean.homepage.HomeBanner;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class HomeBannerDB {
    @Id
    private long id;
    private String desc;
    private String imagePath;
    private int isVisible;
    private int order;
    private String title;
    private int type;
    private String url;
    public HomeBannerDB(){
    }
    public HomeBannerDB(HomeBanner banner) {
        this.desc=banner.getDesc();
        this.imagePath=banner.getImagePath();
        this.isVisible=banner.getIsVisible();
        this.order=banner.getOrder();
        this.title=banner.getTitle();
        this.type=banner.getType();
        this.url=banner.getUrl();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
