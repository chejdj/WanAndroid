package com.chejdj.wanandroid.ui.project;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectory;
import com.chejdj.wanandroid.ui.base.WanAndroidBaseFragment;
import com.chejdj.wanandroid.ui.commonarticlelist.CommonArticleListFragment;
import com.chejdj.wanandroid.ui.commonarticlelist.CommonPagerFragmentAdapter;
import com.chejdj.wanandroid.ui.project.contract.ProjectContract;
import com.chejdj.wanandroid.ui.project.presenter.ProjectPresenter;
import com.chejdj.wanandroid.util.NetUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ProjectFragment extends WanAndroidBaseFragment implements ProjectContract.View {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<String> subTitles;
    private List<Fragment> fragmentList;
    private static final int TYPE_COMMON_LIST_FRAGMENT = 2;//代表和CommonArticleListFragment的协议

    @Override
    protected int getLayoutId() {
        if (NetUtils.getNetWorkState() < 0) {
            return R.layout.network_error;
        }
        return R.layout.fragment_project;
    }

    @Override
    protected void initView() {
        subTitles = new ArrayList<>();
        fragmentList = new ArrayList<>();
        presenter = new ProjectPresenter(this);
        ((ProjectPresenter) presenter).getProjectDirectory();
    }

    @Override
    public void updateProjectDirectory(List<PrimaryArticleDirectory> directories) {
        CommonPagerFragmentAdapter adapter = new CommonPagerFragmentAdapter(subTitles, fragmentList, getChildFragmentManager());
        for (PrimaryArticleDirectory projectDirectory : directories) {
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
