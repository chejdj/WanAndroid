package com.chejdj.wanandroid.network.bean.hotkey;

import java.util.List;

public class HotKeyData {
    private List<HotKey> data;
    private int errorCode;
    private String errorMsg;

    public List<HotKey> getData() {
        return data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
