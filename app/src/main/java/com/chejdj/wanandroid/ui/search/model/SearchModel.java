package com.chejdj.wanandroid.ui.search.model;

import com.chejdj.wanandroid.network.HttpService;
import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.network.bean.hotkey.HotKeyData;
import com.chejdj.wanandroid.ui.search.contract.SearchContract;

import io.reactivex.Observable;

public class SearchModel implements SearchContract.Model {
    @Override
    public Observable<HotKeyData> getHotKeysFromIn() {
        return HttpService.getInstance().getSearchHotKey();
    }

    @Override
    public Observable<ArticleDataRes> getSearchArticleFromIn(int pageNum, String keywords) {
        return HttpService.getInstance().getSearchArticles(pageNum,keywords);
    }
}
