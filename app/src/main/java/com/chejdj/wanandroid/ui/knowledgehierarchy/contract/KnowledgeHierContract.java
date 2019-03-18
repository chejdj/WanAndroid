package com.chejdj.wanandroid.ui.knowledgehierarchy.contract;

import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectoryRes;
import com.chejdj.wanandroid.ui.base.WanAndroidBasePresenter;

import io.reactivex.Observable;


public interface KnowledgeHierContract {
    interface View{
        void updateDetailKnowledgeHier(PrimaryArticleDirectoryRes directory);
    }
    interface Presenter extends WanAndroidBasePresenter {
        void getDetailKnowledgeHier();
    }
    interface Model{
        Observable<PrimaryArticleDirectoryRes> getDetailKnowledgeHierFromIn();
    }
}
