package com.chejdj.wanandroid.ui.project;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectory;
import com.chejdj.wanandroid.ui.base.FragmentManagerLazyLoadFragment;
import com.chejdj.wanandroid.ui.commonarticlelist.CommonPagerFragmentAdapter;
import com.chejdj.wanandroid.ui.project.contract.ProjectContract;
import com.chejdj.wanandroid.ui.project.presenter.ProjectPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class ProjectFragment extends FragmentManagerLazyLoadFragment implements ProjectContract.View {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.networkError)
    RelativeLayout networkError;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private List<String> subTitles;
    private List<Integer> cidNumbers;
    private static final int TYPE_COMMON_LIST_FRAGMENT = 2;//代表和CommonArticleListFragment的协议

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView() {
        subTitles = new ArrayList<>();
        cidNumbers = new ArrayList<>();
        presenter = new ProjectPresenter(this);
    }

    @Override
    protected void loadData() {
        ((ProjectPresenter) presenter).getProjectDirectory();
    }

    @Override
    public void updateProjectDirectory(List<PrimaryArticleDirectory> directories) {
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

        for (PrimaryArticleDirectory projectDirectory : directories) {
            subTitles.add(projectDirectory.getName());
            cidNumbers.add(projectDirectory.getId());
            tabLayout.addTab(tabLayout.newTab().setText(projectDirectory.getName()));
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
        ((ProjectPresenter) presenter).getProjectDirectory();
    }
}
