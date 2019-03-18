package com.chejdj.wanandroid.ui.wechatofficalaccounts;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectory;
import com.chejdj.wanandroid.ui.base.WanAndroidBaseFragment;
import com.chejdj.wanandroid.ui.commonarticlelist.CommonArticleListFragment;
import com.chejdj.wanandroid.ui.commonarticlelist.CommonPagerFragmentAdapter;
import com.chejdj.wanandroid.ui.wechatofficalaccounts.contract.WeChatOfficalContractor;
import com.chejdj.wanandroid.ui.wechatofficalaccounts.presenter.WechatOfficalPresenter;
import com.chejdj.wanandroid.util.NetUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class WechatOfficalFragment extends WanAndroidBaseFragment implements WeChatOfficalContractor.View {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    private List<String> subTitles;
    private List<Fragment> fragmentList;
    private static final int TYPE_COMMON_LIST_FRAGMENT = 3;//代表和CommonArticleListFragment的协议

    @Override
    protected int getLayoutId() {
        if (NetUtils.getNetWorkState() < 0) {
            return R.layout.network_error;
        }
        return R.layout.fragment_wechat_sub;
    }

    @Override
    protected void initView() {
        subTitles = new ArrayList<>();
        fragmentList = new ArrayList<>();
        presenter = new WechatOfficalPresenter(this);
        ((WechatOfficalPresenter) presenter).getWechatChapters();
    }


    @Override
    public void updateWechatChapter(List<PrimaryArticleDirectory> data) {
        CommonPagerFragmentAdapter adapter = new CommonPagerFragmentAdapter(subTitles, fragmentList, getChildFragmentManager());
        for (PrimaryArticleDirectory projectDirectory : data) {
            subTitles.add(projectDirectory.getName());
            tabLayout.addTab(tabLayout.newTab().setText(projectDirectory.getName()));
            fragmentList.add(CommonArticleListFragment.getInstance(TYPE_COMMON_LIST_FRAGMENT, projectDirectory.getId()));
        }
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        adapter.notifyDataSetChanged();
    }
}
