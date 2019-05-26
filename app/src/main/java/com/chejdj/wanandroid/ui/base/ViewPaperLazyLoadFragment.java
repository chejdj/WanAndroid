package com.chejdj.wanandroid.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

public abstract class ViewPaperLazyLoadFragment extends WanAndroidBaseFragment {
    private boolean isViewCreated;
    private boolean isVisible;
    private boolean isDataLoad;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreated = true;
        tryLoadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser) {
            tryLoadData();
        }
    }

    private void dispatchChildLoadData() {
        FragmentManager fragmentManager = getFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null || fragments.isEmpty()) {
            return;
        }
        for (Fragment fragment : fragments) {
            if (fragment instanceof ViewPaperLazyLoadFragment && fragment.isVisible()) {
                ((ViewPaperLazyLoadFragment) fragment).tryLoadData();
            }
        }
    }

    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        return fragment==null ||(fragment != null && fragment.isVisible());
    }

    private void tryLoadData() {
        if (isViewCreated && isParentVisible() && isVisible && !isDataLoad) {
            loadData();
            isDataLoad = true;
            dispatchChildLoadData();
        }
    }

    protected abstract void loadData();
}
