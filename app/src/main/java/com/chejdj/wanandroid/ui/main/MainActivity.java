package com.chejdj.wanandroid.ui.main;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.ui.base.WanAndroidBaseActivty;
import com.chejdj.wanandroid.ui.home.HomeFragment;
import com.chejdj.wanandroid.ui.knowledgehierarchy.KnowledgeHierFragment;
import com.chejdj.wanandroid.ui.me.MeFragment;
import com.chejdj.wanandroid.ui.project.ProjectFragment;
import com.chejdj.wanandroid.ui.wechatofficalaccounts.WechatOfficalFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends WanAndroidBaseActivty {

    @BindView(R.id.navigationView)
    BottomNavigationView bottomNavigationView;
    private List<Fragment> fragmentList;
    private int lastFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initFragments();
        lastFragment = 0;
        getSupportFragmentManager().beginTransaction().add(R.id.mainContainer, fragmentList.get(0))
                .add(R.id.mainContainer, fragmentList.get(1))
                .add(R.id.mainContainer, fragmentList.get(2))
                .add(R.id.mainContainer, fragmentList.get(3))
                .add(R.id.mainContainer, fragmentList.get(4))
                .hide(fragmentList.get(0))
                .hide(fragmentList.get(1))
                .hide(fragmentList.get(2))
                .hide(fragmentList.get(3))
                .hide(fragmentList.get(4))
                .show(fragmentList.get(lastFragment)).commit();
        bottomNavigationView.setLabelVisibilityMode(1);
        bottomNavigationView.setOnNavigationItemSelectedListener((menuItem) -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_home:
                    if (lastFragment != 0) {
                        switchFragment(lastFragment, 0);
                        lastFragment = 0;
                    }
                    break;
                case R.id.menu_knowledge_hierarchy:
                    if (lastFragment != 1) {
                        switchFragment(lastFragment, 1);
                        lastFragment = 1;
                    }
                    break;
                case R.id.meun_wechat_sub:
                    if (lastFragment != 2) {
                        switchFragment(lastFragment, 2);
                        lastFragment = 2;
                    }
                    break;
                case R.id.menu_project:
                    if (lastFragment != 3) {
                        switchFragment(lastFragment, 3);
                        lastFragment = 3;
                    }
                    break;
                case R.id.menu_me:
                    if (lastFragment != 4) {
                        switchFragment(lastFragment, 4);
                        lastFragment = 4;
                    }
                    break;
                default:
                    return true;
            }
            return true;
        });
    }

    private void initFragments() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new KnowledgeHierFragment());
        fragmentList.add(new WechatOfficalFragment());
        fragmentList.add(new ProjectFragment());
        fragmentList.add(new MeFragment());
    }

    private void switchFragment(int lastFragment, int currentIndex) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragmentList.get(lastFragment)).show(fragmentList.get(currentIndex)).commitAllowingStateLoss();
    }
}
