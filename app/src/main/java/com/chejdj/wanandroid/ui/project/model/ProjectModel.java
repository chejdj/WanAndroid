package com.chejdj.wanandroid.ui.project.model;

import com.chejdj.wanandroid.network.HttpService;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectoryRes;
import com.chejdj.wanandroid.ui.project.contract.ProjectContract;

import io.reactivex.Observable;


public class ProjectModel implements ProjectContract.Model {
    @Override
    public Observable<PrimaryArticleDirectoryRes> getProjectDirectoryFromIn() {
        return HttpService.getInstance().getProjectTreeData();
    }
}
