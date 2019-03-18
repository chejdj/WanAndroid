package com.chejdj.wanandroid.ui.commonarticlelist.contract;

import com.chejdj.wanandroid.network.bean.article.ArticleData;
import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.ui.base.WanAndroidBasePresenter;

import io.reactivex.Observable;


public interface CommonArticleListContract {
    interface View {
        void updateArticles(ArticleData data);
    }

    interface Presenter extends WanAndroidBasePresenter {
        void getArticlesFromKnowledges(int pageNum, int cid);

        void getArticlesFromProject(int pageNum, int cid);
        void getArticlesFromWechatChapter(int pageNum, int cid);
    }

    interface Model {
        Observable<ArticleDataRes> getArticlesFromKnowledges(int pageNum, int cid);

        Observable<ArticleDataRes> getArticlesFromProject(int pageNum, int cid);
        Observable<ArticleDataRes> getArticlesFromWechatChapter(int pageNum, int cid);
    }

}
