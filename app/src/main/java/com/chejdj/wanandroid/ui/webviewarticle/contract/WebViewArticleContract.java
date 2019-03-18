package com.chejdj.wanandroid.ui.webviewarticle.contract;

import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.ui.base.WanAndroidBasePresenter;

import io.reactivex.Observable;


public interface WebViewArticleContract {
    interface View {
        void collectState(boolean isCollect);
        void cancelCollectState(boolean isCancelCollect);
    }
    interface Model{
        Observable<Boolean> insertCollectArticle(Article article);
        Observable<Boolean> deleteCollectArticle(String title, String author);
        Observable<Boolean> isExistsCollectArticle(String title, String author);
        Observable<ArticleDataRes> cancelArticle(int articleId);
        Observable<ArticleDataRes> collectArticle(int articleId);
    }
    interface Presenter extends WanAndroidBasePresenter {
        void start(Article article);
        void collect(Article article);
        void cancelCollect(Article article);
        void isCollected(String title, String author);
    }
}
