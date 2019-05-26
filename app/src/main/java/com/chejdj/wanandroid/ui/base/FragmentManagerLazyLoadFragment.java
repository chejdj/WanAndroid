package com.chejdj.wanandroid.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

public abstract class FragmentManagerLazyLoadFragment extends WanAndroidBaseFragment {
    private boolean isLoadData;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            tryLoadData();
        }
    }

    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        return fragment==null ||(fragment != null && fragment.isVisible());
    }


    private void tryLoadData() {
        if (isParentVisible() && !isLoadData) {
            loadData();
            isLoadData=true;
            dispatchChildLoadData();
        }
    }

    private void dispatchChildLoadData() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments==null || fragments.isEmpty()) {
            return;
        }
        for (Fragment fragment : fragments) {
            if (fragment instanceof FragmentManagerLazyLoadFragment && fragment.isVisible()) {
                ((FragmentManagerLazyLoadFragment) fragment).tryLoadData();
            }
        }
    }

    protected abstract void loadData();
}
