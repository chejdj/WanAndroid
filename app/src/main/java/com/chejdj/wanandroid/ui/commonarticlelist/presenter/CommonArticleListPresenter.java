package com.chejdj.wanandroid.ui.commonarticlelist.presenter;

import android.util.Log;

import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.ui.commonarticlelist.contract.CommonArticleListContract;
import com.chejdj.wanandroid.ui.commonarticlelist.model.CommonArticleListModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommonArticleListPresenter implements CommonArticleListContract.Presenter {
    private static final String TAG="CommonArticlePresenter";
    private CommonArticleListContract.View view;
    private CommonArticleListContract.Model model;

    public CommonArticleListPresenter(CommonArticleListContract.View view) {
        this.view = view;
        this.model = new CommonArticleListModel();
    }

    @Override
    public void getArticlesFromKnowledges(int pageNum, int cid) {
        model.getArticlesFromKnowledges(pageNum, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleDataRes>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleDataRes articleDataRes) {
                        if (view != null) {
                            if (articleDataRes.getErrorCode() == 0) {
                                view.updateArticles(articleDataRes.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,"getArticlesFromKnowledges : "+e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void getArticlesFromProject(int pageNum, int cid) {
        model.getArticlesFromProject(pageNum, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleDataRes>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleDataRes articleDataRes) {
                        if (view != null) {
                            if (articleDataRes.getErrorCode() == 0) {
                                view.updateArticles(articleDataRes.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,"getArticlesFromProject: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void getArticlesFromWechatChapter(int pageNum, int cid) {
        model.getArticlesFromWechatChapter(pageNum, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleDataRes>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleDataRes articleDataRes) {
                        if (view != null) {
                            if (articleDataRes.getErrorCode() == 0) {
                                view.updateArticles(articleDataRes.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG," getArticlesFromWechatChapter"+e.getMessage());
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
