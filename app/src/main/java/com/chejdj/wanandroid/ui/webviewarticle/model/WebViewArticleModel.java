package com.chejdj.wanandroid.ui.webviewarticle.model;

import com.chejdj.wanandroid.db.tablemanager.CollectArticleTableManager;
import com.chejdj.wanandroid.network.HttpService;
import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.ui.webviewarticle.contract.WebViewArticleContract;

import io.reactivex.Observable;


public class WebViewArticleModel implements WebViewArticleContract.Model {
    @Override
    public Observable<Boolean> insertCollectArticle(Article article) {
        return CollectArticleTableManager.getInstance().insertCollectArticle(article);
    }

    @Override
    public Observable<Boolean> deleteCollectArticle(String title, String author) {
        return CollectArticleTableManager.getInstance().deleteCollectArticle(title, author);
    }

    @Override
    public Observable<Boolean> isExistsCollectArticle(String title, String author) {
        return CollectArticleTableManager.getInstance().isExists(title, author);
    }

    @Override
    public Observable<ArticleDataRes> cancelArticle(int articleId) {
        return HttpService.getInstance().cancelCollectArticle(articleId);
    }

    @Override
    public Observable<ArticleDataRes> collectArticle(int articleId) {
        return HttpService.getInstance().collectWanAndroidArticle(articleId);
    }

}
