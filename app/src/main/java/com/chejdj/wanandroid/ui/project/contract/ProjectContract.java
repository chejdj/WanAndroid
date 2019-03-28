package com.chejdj.wanandroid.ui.project.contract;

import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectory;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectoryRes;
import com.chejdj.wanandroid.ui.base.WanAndroidBasePresenter;

import java.util.List;

import io.reactivex.Observable;

public interface ProjectContract {
    interface View{
        void updateProjectDirectory(List<PrimaryArticleDirectory> directories);
        void networkError();
    }
    interface Presenter extends WanAndroidBasePresenter {
        void getProjectDirectory();
    }
    interface Model{
        Observable<PrimaryArticleDirectoryRes> getProjectDirectoryFromIn();
    }
}
