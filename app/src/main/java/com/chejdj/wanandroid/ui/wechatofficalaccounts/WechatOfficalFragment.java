package com.chejdj.wanandroid.ui.wechatofficalaccounts;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectory;
import com.chejdj.wanandroid.ui.base.FragmentManagerLazyLoadFragment;
import com.chejdj.wanandroid.ui.commonarticlelist.CommonPagerFragmentAdapter;
import com.chejdj.wanandroid.ui.wechatofficalaccounts.contract.WeChatOfficalContractor;
import com.chejdj.wanandroid.ui.wechatofficalaccounts.presenter.WechatOfficalPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class WechatOfficalFragment extends FragmentManagerLazyLoadFragment implements WeChatOfficalContractor.View {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.networkError)
    RelativeLayout networkError;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private List<String> subTitles;
    private List<Integer> cidNumbers;
    private static final int TYPE_COMMON_LIST_FRAGMENT = 3;//代表和CommonArticleListFragment的协议

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wechat_sub;
    }

    @Override
    protected void initView() {
        subTitles = new ArrayList<>();
        cidNumbers = new ArrayList<>();
        presenter = new WechatOfficalPresenter(this);
    }

    @Override
    protected void loadData() {
        ((WechatOfficalPresenter) presenter).getWechatChapters();
    }

    @Override
    protected boolean isDataEmpty() {
        return subTitles == null || subTitles.size() == 0 || cidNumbers == null || cidNumbers.size() == 0;
    }


    @Override
    public void updateWechatChapter(List<PrimaryArticleDirectory> data) {
        if (viewPager.getVisibility() == View.GONE) {
            viewPager.setVisibility(View.VISIBLE);
        }
        if (tabLayout.getVisibility() == View.GONE) {
            tabLayout.setVisibility(View.VISIBLE);
        }
        if (networkError.getVisibility() == View.VISIBLE) {
            networkError.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }

        for (PrimaryArticleDirectory projectDirectory : data) {
            subTitles.add(projectDirectory.getName());
            tabLayout.addTab(tabLayout.newTab().setText(projectDirectory.getName()));
            cidNumbers.add(projectDirectory.getId());
        }
        CommonPagerFragmentAdapter adapter = new CommonPagerFragmentAdapter(subTitles, cidNumbers, TYPE_COMMON_LIST_FRAGMENT, getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void networkError() {
        if (viewPager.getVisibility() == View.VISIBLE) {
            viewPager.setVisibility(View.GONE);
        }
        if (tabLayout.getVisibility() == View.VISIBLE) {
            tabLayout.setVisibility(View.GONE);
        }
        if (networkError.getVisibility() == View.GONE) {
            networkError.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.reloadBtn)
    public void reloadData() {
        if (progressBar.getVisibility() == View.GONE) {
            progressBar.setVisibility(View.VISIBLE);
        }
        ((WechatOfficalPresenter) presenter).getWechatChapters();
    }
}
