package com.chejdj.wanandroid.ui.search.contract;


import com.chejdj.wanandroid.network.bean.article.ArticleData;
import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.network.bean.hotkey.HotKey;
import com.chejdj.wanandroid.network.bean.hotkey.HotKeyData;
import com.chejdj.wanandroid.ui.base.WanAndroidBasePresenter;

import java.util.List;

import io.reactivex.Observable;

public interface SearchContract {
    interface View{
        void updateHotKeys(List<HotKey> keyList);
        void updateSearchArticles(ArticleData articleData);
        void showHotKeys();
        void networkError();
    }
    interface Presenter extends WanAndroidBasePresenter {
        void getHotKeys();
        void getSearchArticles(int pageNum, String keywords);
    }
    interface Model{
         Observable<HotKeyData> getHotKeysFromIn();
         Observable<ArticleDataRes> getSearchArticleFromIn(int pageNum, String keywords);
    }
}
