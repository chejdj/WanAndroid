package com.chejdj.wanandroid.ui.me.presenter;

import android.util.Log;

import com.chejdj.wanandroid.db.entity.CollectArticleDB;
import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.network.bean.article.ArticleData;
import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.ui.me.contract.MeContract;
import com.chejdj.wanandroid.ui.me.model.MeModel;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MePresneter implements MeContract.Presenter {
    private MeContract.View view;
    private MeContract.Model model;
    private static final String TAG = "MePresenter";

    public MePresneter(MeContract.View view) {
        this.view = view;
        this.model = new MeModel();
    }

    @Override
    public void loadCollectArticleFromIn(int pageNum) {
        model.getCollectArticlesFromIn(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((RxFragment) view).bindToLifecycle())
                .doOnNext((ArticleDataRes articleDataRes) -> {
                    if (view != null) {
                        view.showCollectArticle(articleDataRes.getData());
                    }
                })
                .map((ArticleDataRes articleDataRes) -> {
                    List<Article> articleList = articleDataRes.getData().getListData();
                    List<CollectArticleDB> collectArticleDBS = new ArrayList<>();
                    for (Article article : articleList) {
                        CollectArticleDB db = new CollectArticleDB(article);
                        collectArticleDBS.add(db);
                    }
                    return collectArticleDBS;
                })
                .flatMap((List<CollectArticleDB> collectArticleDBS) -> model.updateCollectArticleDB(collectArticleDBS))
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "loadCollectArticleFromIn() errors" + e.getMessage());
                        loadCollectArticleFromDB();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadCollectArticleFromDB() {
        model.getCollectArticleFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((RxFragment) view).bindToLifecycle())
                .subscribe(new Observer<List<CollectArticleDB>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CollectArticleDB> collectArticleDBS) {
                        ArticleData articleData = new ArticleData();
                        articleData.setPageCount(0);
                        articleData.setCurPage(0);
                        for (CollectArticleDB db : collectArticleDBS) {
                            Article article = new Article(db);
                            articleData.getListData().add(article);
                        }
                        if (view != null) {
                            view.showCollectArticle(articleData);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "loadCollectArticleFromDB() errors :" + e.getMessage());
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
