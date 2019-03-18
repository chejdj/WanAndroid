package com.chejdj.wanandroid.ui.project.presenter;

import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectoryRes;
import com.chejdj.wanandroid.ui.project.contract.ProjectContract;
import com.chejdj.wanandroid.ui.project.model.ProjectModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ProjectPresenter implements ProjectContract.Presenter {
    private ProjectContract.View view;
    private ProjectContract.Model model;

    public ProjectPresenter(ProjectContract.View view) {
        this.view = view;
        this.model = new ProjectModel();
    }

    @Override
    public void getProjectDirectory() {
        model.getProjectDirectoryFromIn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PrimaryArticleDirectoryRes>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PrimaryArticleDirectoryRes directoryData) {
                        if (view != null) {
                            if (directoryData.getErrorCode() == 0) {
                                view.updateProjectDirectory(directoryData.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
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
