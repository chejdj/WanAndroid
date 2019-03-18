package com.chejdj.wanandroid.ui.login.presenter;

import com.chejdj.wanandroid.db.account.AccountManager;
import com.chejdj.wanandroid.db.account.entity.Account;
import com.chejdj.wanandroid.network.bean.login.LoginStateBean;
import com.chejdj.wanandroid.ui.login.contract.LoginContract;
import com.chejdj.wanandroid.ui.login.model.LoginModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private LoginContract.Model model;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.model = new LoginModel();
    }

    @Override
    public void login(String username, String password) {
        model.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginStateBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginStateBean state) {
                        //转成Json格式
                        if (view != null) {
                            if (state.getErrorCode() == -1) {
                                view.loginFail(state.getErrorMsg());
                            } else {
                                Account account = new Account(state.getData().getUsername());
                                AccountManager.getInstance().setCurrentAccount(account);
                                view.loginSuccess();
                                AccountManager.getInstance().addAccountToDB(state.getData().getUsername());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.loginFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void register(String username, String password) {
        model.register(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginStateBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginStateBean state) {
                        if (view != null) {
                            if (state.getErrorCode() == -1) {
                                view.loginFail(state.getErrorMsg());
                            } else {
                                Account account = new Account(state.getData().getUsername());
                                AccountManager.getInstance().setCurrentAccount(account);
                                AccountManager.getInstance().addAccountToDB(state.getData().getUsername());
                                view.loginSuccess();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.loginFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void destory() {
        view = null;
        System.gc();
    }
}
