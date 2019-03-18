package com.chejdj.wanandroid.network.bean.article;

public class ArticleDataRes {
    private ArticleData data;
    private int errorCode;
    private String errorMsg;

    public ArticleData getData() {
        return data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
