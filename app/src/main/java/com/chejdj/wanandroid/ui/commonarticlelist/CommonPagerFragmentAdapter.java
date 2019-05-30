package com.chejdj.wanandroid.ui.commonarticlelist;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class CommonPagerFragmentAdapter extends FragmentStatePagerAdapter {
    private List<String> subTitles;
    private List<Integer> cidNumbers;
    private int type;

    public CommonPagerFragmentAdapter(List<String> subTitles, List<Integer> cidNumbers, int type, FragmentManager fm) {
        super(fm);
        this.subTitles = subTitles;
        this.cidNumbers = cidNumbers;
        this.type = type;
    }

    @Override
    public Fragment getItem(int i) {
        return CommonArticleListFragment.getInstance(type, cidNumbers.get(i));
    }

    @Override
    public int getCount() {
        return subTitles == null ? 0 : subTitles.size();
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
