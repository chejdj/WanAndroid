package com.chejdj.wanandroid.ui.home.model;

import com.chejdj.wanandroid.db.entity.HomeArticleDB;
import com.chejdj.wanandroid.db.entity.HomeBannerDB;
import com.chejdj.wanandroid.db.tablemanager.HomeArticleTableManager;
import com.chejdj.wanandroid.db.tablemanager.HomeBannerTableManager;
import com.chejdj.wanandroid.network.HttpService;
import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.network.bean.homepage.HomeBannerData;
import com.chejdj.wanandroid.ui.home.contract.HomeContract;

import java.util.List;

import io.reactivex.Observable;


public class HomeModel implements HomeContract.Model {
    @Override
    public Observable<HomeBannerData> getBannerDataFromIn() {
        return HttpService.getInstance().getHomeBannerData();
    }

    @Override
    public Observable<Boolean> updateBannerDataDB(List<HomeBannerDB> data) {
        return HomeBannerTableManager.getInstance().updateHomeArticleDB(data);
    }

    @Override
    public Observable<List<HomeBannerDB>> getBannerDataFromDB() {
        return HomeBannerTableManager.getInstance().queryHomeBannerAll();
    }

    @Override
    public Observable<ArticleDataRes> getArticles(int pageNums) {
        return HttpService.getInstance().getHomeArticlesData(pageNums);
    }

    @Override
    public Observable<Boolean> updateHomeArticleDB(List<HomeArticleDB> data) {
        return HomeArticleTableManager.getInstance().updateHomeArticleDB(data);
    }

    @Override
    public Observable<List<HomeArticleDB>> getHomeArticleDB() {
        return HomeArticleTableManager.getInstance().queryHomeArticleAll();
    }
}
