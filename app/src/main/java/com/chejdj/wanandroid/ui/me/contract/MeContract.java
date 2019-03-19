package com.chejdj.wanandroid.ui.me.contract;

import com.chejdj.wanandroid.db.entity.CollectArticleDB;
import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.network.bean.article.ArticleData;
import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.ui.base.WanAndroidBasePresenter;

import java.util.List;

import io.reactivex.Observable;


public interface MeContract {
    interface View{
        void showCollectArticle(ArticleData articleData);
    }
    interface Presenter extends WanAndroidBasePresenter {
        void loadCollectArticleFromIn(int pageNum);
    }
    interface Model{
        Observable<List<CollectArticleDB>> getCollectArticleFromDB();
        Observable<ArticleDataRes> getCollectArticlesFromIn(int pageNum);
        Observable<Boolean> updateCollectArticleDB(List<CollectArticleDB> data);
        Observable<Boolean> insertCollectArticle(Article article);
        Observable<Boolean> deleteCollectArticle(String title,String author);
    }
}
