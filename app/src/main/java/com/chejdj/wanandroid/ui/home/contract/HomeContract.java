package com.chejdj.wanandroid.ui.home.contract;

import com.chejdj.wanandroid.db.entity.HomeArticleDB;
import com.chejdj.wanandroid.db.entity.HomeBannerDB;
import com.chejdj.wanandroid.network.bean.article.ArticleData;
import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.network.bean.homepage.HomeBanner;
import com.chejdj.wanandroid.network.bean.homepage.HomeBannerData;
import com.chejdj.wanandroid.ui.base.WanAndroidBasePresenter;

import java.util.List;

import io.reactivex.Observable;


public interface HomeContract {
    interface View {
        void showBanner(List<HomeBanner> images);

        void showArticles(ArticleData articleData);
        void networkError();
    }

    interface Presenter extends WanAndroidBasePresenter {
        void getBannerData();
        void getArticles(int pageNums);
    }

    interface Model {
        Observable<HomeBannerData> getBannerDataFromIn();

        Observable<Boolean> updateBannerDataDB(List<HomeBannerDB> data);

        Observable<List<HomeBannerDB>> getBannerDataFromDB();

        Observable<ArticleDataRes> getArticles(int pageNums);

        Observable<Boolean> updateHomeArticleDB(List<HomeArticleDB> articleList);

        Observable<List<HomeArticleDB>> getHomeArticleDB();
    }


}
