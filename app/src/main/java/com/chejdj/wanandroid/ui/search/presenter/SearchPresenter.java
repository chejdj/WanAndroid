package com.chejdj.wanandroid.ui.search.presenter;

import android.util.Log;

import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.network.bean.hotkey.HotKeyData;
import com.chejdj.wanandroid.ui.search.contract.SearchContract;
import com.chejdj.wanandroid.ui.search.model.SearchModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.Model model;
    private SearchContract.View view;
    public static final String TAG = "SearchPresenter";

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
        model = new SearchModel();
    }

    @Override
    public void getHotKeys() {
        model.getHotKeysFromIn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotKeyData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HotKeyData hotKeyData) {
                        if (view != null) {
                            if (hotKeyData.getErrorCode() == 0 && hotKeyData.getData() != null) {

                                view.updateHotKeys(hotKeyData.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "getHotKeys() errors : " + e.getMessage());
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
    public void getSearchArticles(int pageNum, String keywords) {
        model.getSearchArticleFromIn(pageNum, keywords)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleDataRes>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleDataRes articleDataRes) {
                        if (view != null) {
                            if (articleDataRes.getErrorCode() == 0 && articleDataRes.getData() != null) {
                                view.updateSearchArticles(articleDataRes.getData());
                            } else {
                                view.showHotKeys();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "getSearchArticles() errors : " + e.getMessage());
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
