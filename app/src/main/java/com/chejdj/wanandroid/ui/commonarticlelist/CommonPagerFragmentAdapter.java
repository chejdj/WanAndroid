package com.chejdj.wanandroid.ui.commonarticlelist;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class CommonPagerFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> subTitles;

    public CommonPagerFragmentAdapter(List<String> subTitles, List<Fragment> fragments, FragmentManager fm) {
        super(fm);
        this.subTitles = subTitles;
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return subTitles.get(position);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
