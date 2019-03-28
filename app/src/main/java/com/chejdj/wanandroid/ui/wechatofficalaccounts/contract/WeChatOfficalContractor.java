package com.chejdj.wanandroid.ui.wechatofficalaccounts.contract;


import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectory;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectoryRes;
import com.chejdj.wanandroid.ui.base.WanAndroidBasePresenter;

import java.util.List;

import io.reactivex.Observable;


public interface WeChatOfficalContractor {
    interface View{
        void updateWechatChapter(List<PrimaryArticleDirectory> data);
        void networkError();
    }
    interface Model{
        Observable<PrimaryArticleDirectoryRes> getWechatChapters();
    }
    interface Presenter extends WanAndroidBasePresenter {
        void getWechatChapters();
    }
}
