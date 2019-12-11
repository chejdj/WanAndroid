package com.chejdj.wanandroid.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chejdj.wanandroid.WanAndroidApplication;
import com.chejdj.wanandroid.util.DisplayUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

public abstract class WanAndroidBaseActivity extends RxAppCompatActivity {
    public WanAndroidBasePresenter presenter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        DisplayUtils.setCustomDensity(this, WanAndroidApplication.getMyApplication());
        initBind();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destory();
            presenter = null;
            System.gc();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    private void initBind() {
        ButterKnife.bind(this);
    }

}
