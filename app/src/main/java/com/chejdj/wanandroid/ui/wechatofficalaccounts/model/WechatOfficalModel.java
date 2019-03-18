package com.chejdj.wanandroid.ui.wechatofficalaccounts.model;

import com.chejdj.wanandroid.network.HttpService;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectoryRes;
import com.chejdj.wanandroid.ui.wechatofficalaccounts.contract.WeChatOfficalContractor;

import io.reactivex.Observable;

public class WechatOfficalModel implements WeChatOfficalContractor.Model {
    @Override
    public Observable<PrimaryArticleDirectoryRes> getWechatChapters() {
        return HttpService.getInstance().getWechatArticlChapter();
    }
}
