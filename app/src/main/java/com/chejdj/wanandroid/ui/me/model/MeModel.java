package com.chejdj.wanandroid.ui.me.model;

import com.chejdj.wanandroid.db.entity.CollectArticleDB;
import com.chejdj.wanandroid.db.tablemanager.CollectArticleTableManager;
import com.chejdj.wanandroid.network.HttpService;
import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.ui.me.contract.MeContract;

import java.util.List;

import io.reactivex.Observable;


public class MeModel implements MeContract.Model {
    @Override
    public Observable<List<CollectArticleDB>> getCollectArticleFromDB() {
        return CollectArticleTableManager.getInstance().getAllCollectArticle();
    }

    @Override
    public Observable<ArticleDataRes> getCollectArticlesFromIn(int pageNum) {
        return HttpService.getInstance().getCollectedArticles(pageNum);
    }

    @Override
    public Observable<Boolean> updateCollectArticleDB(List<CollectArticleDB> data) {
        return CollectArticleTableManager.getInstance().updateCollectArticleDB(data);
    }

}
