package com.chejdj.wanandroid.ui.knowledgehierarchy.model;

import com.chejdj.wanandroid.network.HttpService;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectoryRes;
import com.chejdj.wanandroid.ui.knowledgehierarchy.contract.KnowledgeHierContract;

import io.reactivex.Observable;

public class KnowledgeHierModel implements KnowledgeHierContract.Model {
    @Override
    public Observable<PrimaryArticleDirectoryRes> getDetailKnowledgeHierFromIn() {
        return HttpService.getInstance().getKnowledgeTreeData();
    }
}
