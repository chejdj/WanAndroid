package com.chejdj.wanandroid.ui.commonarticlelist.model;

import com.chejdj.wanandroid.network.HttpService;
import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.ui.commonarticlelist.contract.CommonArticleListContract;

import io.reactivex.Observable;


public class CommonArticleListModel implements CommonArticleListContract.Model {
    @Override
    public Observable<ArticleDataRes> getArticlesFromKnowledges(int pageNum, int cid) {
        return HttpService.getInstance().getKnowledgeTreeDetailArticleData(pageNum, cid);
    }

    @Override
    public Observable<ArticleDataRes> getArticlesFromProject(int pageNum, int cid) {
        return HttpService.getInstance().getProectTreeDetailArticleData(pageNum, cid);
    }

    @Override
    public Observable<ArticleDataRes> getArticlesFromWechatChapter(int pageNum, int cid) {
        return HttpService.getInstance().getWechatChapterArticles(cid,pageNum);
    }
}
