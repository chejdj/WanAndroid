package com.chejdj.wanandroid.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class PagerFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public PagerFragmentAdapter(List<Fragment> fragments, FragmentManager fm) {
        super(fm);
        this.fragmentList = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList == null ? null : fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }
}
