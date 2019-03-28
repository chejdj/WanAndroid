package com.chejdj.wanandroid.ui.wechatofficalaccounts.presenter;

import android.util.Log;

import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectoryRes;
import com.chejdj.wanandroid.ui.wechatofficalaccounts.contract.WeChatOfficalContractor;
import com.chejdj.wanandroid.ui.wechatofficalaccounts.model.WechatOfficalModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WechatOfficalPresenter implements WeChatOfficalContractor.Presenter {
    private WeChatOfficalContractor.View view;
    private WeChatOfficalContractor.Model model;
    private static final String TAG = "WechatOfficalPresenter";

    public WechatOfficalPresenter(WeChatOfficalContractor.View view) {
        this.view = view;
        this.model = new WechatOfficalModel();
    }

    @Override
    public void getWechatChapters() {
        model.getWechatChapters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PrimaryArticleDirectoryRes>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PrimaryArticleDirectoryRes primaryArticleDirectoryRes) {
                        if (view != null) {
                            if (primaryArticleDirectoryRes.getErrorCode() == 0) {
                                view.updateWechatChapter(primaryArticleDirectoryRes.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "getWechatChapters() errors: " + e.getMessage());
                        if (view != null) {
                            view.networkError();
                        }
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
