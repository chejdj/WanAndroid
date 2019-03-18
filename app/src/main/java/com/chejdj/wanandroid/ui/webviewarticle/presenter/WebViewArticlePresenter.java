package com.chejdj.wanandroid.ui.webviewarticle.presenter;

import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.ui.webviewarticle.contract.WebViewArticleContract;
import com.chejdj.wanandroid.ui.webviewarticle.model.WebViewArticleModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class WebViewArticlePresenter implements WebViewArticleContract.Presenter {
    private WebViewArticleContract.View view;
    private WebViewArticleContract.Model model;

    public WebViewArticlePresenter(WebViewArticleContract.View view) {
        this.view = view;
        this.model = new WebViewArticleModel();
    }

    @Override
    public void start(Article article) {
        isCollected(article.getTitle(), article.getAuthor());
    }

    @Override
    public void collect(Article article) {
        model.collectArticle(article.getId())
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
                                view.collectState(true);
                            }
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

    @Override
    public void cancelCollect(Article article) {
        model.cancelArticle(article.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleDataRes>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleDataRes articleData) {
                        if (view != null) {
                            if (articleData.getErrorCode() == 0) {
                                view.cancelCollectState(true);
                            }
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

    @Override
    public void isCollected(String title, String author) {
        //TODO
    }

    @Override
    public void destory() {
        view = null;
        System.gc();
    }
}
