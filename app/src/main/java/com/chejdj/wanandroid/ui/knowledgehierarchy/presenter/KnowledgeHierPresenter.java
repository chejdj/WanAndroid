package com.chejdj.wanandroid.ui.knowledgehierarchy.presenter;

import android.util.Log;

import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectoryRes;
import com.chejdj.wanandroid.ui.knowledgehierarchy.contract.KnowledgeHierContract;
import com.chejdj.wanandroid.ui.knowledgehierarchy.model.KnowledgeHierModel;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class KnowledgeHierPresenter implements KnowledgeHierContract.Presenter {
    private KnowledgeHierContract.View view;
    private KnowledgeHierContract.Model model;
    private static final String TAG = "KnowledgeHierPresenter";

    public KnowledgeHierPresenter(KnowledgeHierContract.View view) {
        this.view = view;
        this.model = new KnowledgeHierModel();
    }

    @Override
    public void getDetailKnowledgeHier() {
        model.getDetailKnowledgeHierFromIn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((RxFragment) view).bindToLifecycle())
                .subscribe(new Observer<PrimaryArticleDirectoryRes>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PrimaryArticleDirectoryRes primaryArticleDirectoryRes) {
                        if (view != null) {
                            //errorCode 为-1代表错误
                            if (primaryArticleDirectoryRes.getErrorCode() >= 0) {
                                view.updateDetailKnowledgeHier(primaryArticleDirectoryRes);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "getDetailKnowledgeHier() errors : " + e.getMessage());
                        if (view != null) {
                            view.networkError();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void destory() {
        view = null;
        System.gc();
    }
}
