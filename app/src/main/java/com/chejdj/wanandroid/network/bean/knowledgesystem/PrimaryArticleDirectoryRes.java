package com.chejdj.wanandroid.network.bean.knowledgesystem;

import java.util.List;


public class PrimaryArticleDirectoryRes {
    private List<PrimaryArticleDirectory> data;
    private int errorCode;
    private String errorMsg;

    public List<PrimaryArticleDirectory> getData() {
        return data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
