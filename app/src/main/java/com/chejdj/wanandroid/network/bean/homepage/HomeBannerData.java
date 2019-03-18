package com.chejdj.wanandroid.network.bean.homepage;

import java.util.List;

public class HomeBannerData {
    private List<HomeBanner> data;
    private int errorCode;
    private String errorMsg;

    public List<HomeBanner> getData() {
        return data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
