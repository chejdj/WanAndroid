package com.chejdj.wanandroid.ui.webviewarticle.presenter;

import android.util.Log;

import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.ui.webviewarticle.contract.WebViewArticleContract;
import com.chejdj.wanandroid.ui.webviewarticle.model.WebViewArticleModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class WebViewArticlePresenter implements WebViewArticleContract.Presenter {
    private static final String TAG = "WebViewArticlePresenter";
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
                .doOnNext((ArticleDataRes articleDataRes) -> {
                    if (articleDataRes.getErrorCode() == 0) {
                        if (view != null) {
                            view.collectState(true);
                        }
                    }
                })
                .flatMap((ArticleDataRes articleDataRes) -> {
                    return model.insertCollectArticle(article);
                })
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "insert article to DB error :" + e.getMessage());
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
                .doOnNext((ArticleDataRes articleDataRes) -> {
                    if (articleDataRes.getErrorCode() == 0 && view != null) {
                        view.cancelCollectState(true);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String message = throwable.getMessage();
                        Log.e(TAG, throwable.getMessage());
                    }
                })
                .flatMap((ArticleDataRes articleDataRes) -> {
                    return model.deleteCollectArticle(article.getTitle(), article.getAuthor());
                })
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "delete collect article from db error: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void isCollected(String title, String author) {
        model.isExistsCollectArticle(title, author)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (view != null) {
                            if (aBoolean) {
                                view.collectState(aBoolean);
                            } else {
                                view.cancelCollectState(aBoolean);
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
    public void destory() {
        view = null;
        System.gc();
    }
}
