package com.chejdj.wanandroid.ui.login.contract;

import com.chejdj.wanandroid.network.bean.login.LoginStateBean;
import com.chejdj.wanandroid.ui.base.WanAndroidBasePresenter;

import io.reactivex.Observable;

public interface LoginContract {
    interface View {
        void loginSuccess();

        void loginFail(String msg);
    }

    interface Presenter extends WanAndroidBasePresenter {
        void login(String username, String password);

        void register(String username, String password);
    }

    interface Model {
        Observable<LoginStateBean> login(String username, String password);

        Observable<LoginStateBean> register(String username, String password);
    }
}
