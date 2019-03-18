package com.chejdj.wanandroid.ui.home.presenter;

import android.util.Log;

import com.chejdj.wanandroid.db.entity.HomeArticleDB;
import com.chejdj.wanandroid.db.entity.HomeBannerDB;
import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.network.bean.article.ArticleData;
import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.network.bean.article.ArticleTag;
import com.chejdj.wanandroid.network.bean.homepage.HomeBanner;
import com.chejdj.wanandroid.network.bean.homepage.HomeBannerData;
import com.chejdj.wanandroid.ui.home.contract.HomeContract;
import com.chejdj.wanandroid.ui.home.model.HomeModel;
import com.chejdj.wanandroid.util.NetUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.Model model;
    private HomeContract.View view;
    private static final String TAG = "HomePresenter";

    public HomePresenter(HomeContract.View view) {
        this.view = view;
        this.model = new HomeModel();
    }

    public void start() {
        getBannerData();
        getArticles(0);
    }

    @Override
    public void getBannerData() {
        if (NetUtils.getNetWorkState() >= 0) {
            model.getBannerDataFromIn().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext((HomeBannerData homeBannerData) -> {
                        if (view != null && homeBannerData.getErrorCode() == 0) {
                            view.showBanner(homeBannerData.getData());
                        }
                    })
                    .map((HomeBannerData homeBannerData) -> {
                        List<HomeBanner> homeBannerList = homeBannerData.getData();
                        List<HomeBannerDB> homeBannerDBList = new ArrayList<>();
                        for (HomeBanner homeBanner : homeBannerList) {
                            HomeBannerDB homeBannerDB = new HomeBannerDB(homeBanner);
                            homeBannerDBList.add(homeBannerDB);
                        }
                        return homeBannerDBList;
                    })
                    .flatMap((List<HomeBannerDB> homeBannerDBS) -> {
                        return model.updateBannerDataDB(homeBannerDBS);
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
                            Log.e(TAG, "update banner error" + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
        //从数据库当中获取HomeBanner数据
        else {
            model.getBannerDataFromDB()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map((List<HomeBannerDB> homeBannerDBS) -> {
                        List<HomeBanner> bannerList = new ArrayList<>();
                        for (HomeBannerDB db : homeBannerDBS) {
                            HomeBanner banner = new HomeBanner(db);
                            bannerList.add(banner);
                        }
                        return bannerList;
                    })
                    .subscribe(new Observer<List<HomeBanner>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<HomeBanner> homeBanners) {
                            if (view != null) {
                                view.showBanner(homeBanners);
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

    @Override
    public void getArticles(int pageNums) {
        //有网络连接的时候，从网上获取
        if (NetUtils.getNetWorkState() >= 0) {
            model.getArticles(pageNums).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext((ArticleDataRes articleDataRes) -> {
                        if (view != null && articleDataRes.getErrorCode() == 0) {
                            view.showArticles(articleDataRes.getData());
                        }
                    })
                    .filter((ArticleDataRes articleDataRes) -> {
                        return pageNums == 0;
                    })
                    .map((ArticleDataRes articleDataRes) -> {
                        List<HomeArticleDB> homeArticleDBList = new ArrayList<>();
                        List<Article> articleList = articleDataRes.getData().getListData();
                        for (Article article : articleList) {
                            HomeArticleDB homeArticleDB = new HomeArticleDB();
                            changeToHomeArticle(homeArticleDB, article);
                            homeArticleDBList.add(homeArticleDB);
                        }
                        return homeArticleDBList;
                    })
                    .flatMap((List<HomeArticleDB> articleList) -> {
                        return model.updateHomeArticleDB(articleList);
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
                            Log.e(TAG, e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
        //从数据库中获取
        else {
            model.getHomeArticleDB()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<HomeArticleDB>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<HomeArticleDB> articleList) {
                            ArticleData data = new ArticleData();
                            data.setPageCount(0);
                            data.setCurPage(0);
                            for (HomeArticleDB homeArticleDB : articleList) {
                                Article article = new Article();
                                changeToArticle(article, homeArticleDB);
                                data.getListData().add(article);
                            }
                            if (view != null) {
                                view.showArticles(data);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "get data from db : " + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    private void changeToHomeArticle(HomeArticleDB homeArticleDB, Article article) {
        homeArticleDB.setTitle(article.getTitle());
        homeArticleDB.setAuthor(article.getAuthor());
        homeArticleDB.setDesc(article.getDesc());
        homeArticleDB.setSuperChapterName(article.getSuperChapterName());
        homeArticleDB.setChapterName(article.getChapterName());
        homeArticleDB.setLink(article.getLink());
        homeArticleDB.setTags(article.getTags() == null || article.getTags().size() == 0 ? "分类" : article.getTags().get(0).getName());
        homeArticleDB.setPublishTime(article.getPublishTime());
    }

    private void changeToArticle(Article article, HomeArticleDB homeArticleDB) {
        article.setTitle(homeArticleDB.getTitle());
        article.setAuthor(homeArticleDB.getAuthor());
        article.setPublishTime(homeArticleDB.getPublishTime());
        article.setLink(homeArticleDB.getLink());
        article.setSuperChapterName(homeArticleDB.getSuperChapterName());
        article.setChapterName(homeArticleDB.getChapterName());
        ArticleTag articleTag = new ArticleTag();
        articleTag.setName(homeArticleDB.getTags());
        article.setTags(Collections.singletonList(articleTag));
    }


    @Override
    public void destory() {
        view = null;
        System.gc();
    }
}
