package com.chejdj.wanandroid.ui.login.model;

import com.chejdj.wanandroid.network.HttpService;
import com.chejdj.wanandroid.network.bean.login.LoginStateBean;
import com.chejdj.wanandroid.ui.login.contract.LoginContract;

import io.reactivex.Observable;

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<LoginStateBean> login(String username, String password) {
        return HttpService.getInstance().userLogin(username, password);
    }

    @Override
    public Observable<LoginStateBean> register(String username, String password) {
        return HttpService.getInstance().userRegister(username, password, password);
    }
}
