package com.chejdj.wanandroid.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chejdj.wanandroid.R;

import butterknife.ButterKnife;

public abstract class WanAndroidBaseFragment extends Fragment {
    public WanAndroidBasePresenter presenter = null;
    private static final String TAG = "WanAndroidBaseFragment";
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destory();
            presenter = null;
            System.gc();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView();
}
