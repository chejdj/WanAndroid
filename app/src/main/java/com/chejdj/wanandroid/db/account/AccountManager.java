package com.chejdj.wanandroid.db.account;


import com.chejdj.wanandroid.db.account.entity.Account;
import com.chejdj.wanandroid.util.SharedPreferenceUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//账号类目前只是存储了名字
public class AccountManager {
    private Account currentAccount;
    private static final String PREF_NAME = "account";
    private static final String USERNAME = "username";

    private AccountManager() {

    }

    static class WrapperInstance {
        private static AccountManager INSTANCE = new AccountManager();
    }

    public static AccountManager getInstance() {
        return WrapperInstance.INSTANCE;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public boolean isLogin() {
        return currentAccount != null;
    }

    public void init() {
        setCurrentAccountFromDB();
    }


    private void setCurrentAccountFromDB() {
        this.getAccountFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Account>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Account account) {
                        AccountManager.getInstance().setCurrentAccount(account);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void clearAccount() {
        Observable<Boolean> observable = Observable.create(emitter -> {
            boolean isSuccess = SharedPreferenceUtil.removeData(PREF_NAME, USERNAME);
            emitter.onNext(isSuccess);
            emitter.onComplete();
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            AccountManager.getInstance().setCurrentAccount(null);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public synchronized void setCurrentAccount(Account account) {
        this.currentAccount = account;
    }

    private Observable<Account> getAccountFromDB() {
        return Observable.create(emitter -> {
            String name = SharedPreferenceUtil.LoadStringData(PREF_NAME, USERNAME);
            Account account = null;
            if (name != null) {
                account = new Account(name);
            }
            emitter.onNext(account);
            emitter.onComplete();
        });
    }

    public void addAccountToDB(String username) {
        Observable<String> observable = Observable.create(emitter -> {
            boolean isSuccess = SharedPreferenceUtil.putData(PREF_NAME, USERNAME, username);
            if (isSuccess) {
                emitter.onNext(username);
            } else {
                emitter.onNext(null);
            }
            emitter.onComplete();
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String username) {
                        if (username != null) {
                            Account account = new Account(username);
                            AccountManager.getInstance().setCurrentAccount(account);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
