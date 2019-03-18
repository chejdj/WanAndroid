package com.chejdj.wanandroid.network.bean.login;

public class LoginStateBean {
    private LoginDataBean data;
    private int errorCode;
    private String errorMsg;

    public LoginStateBean() {
    }

    public LoginDataBean getData() {
        return data;
    }

    public void setData(LoginDataBean data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
