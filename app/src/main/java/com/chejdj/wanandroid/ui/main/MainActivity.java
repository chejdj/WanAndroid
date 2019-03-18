package com.chejdj.wanandroid.ui.main;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.ui.base.WanAndroidMvpBaseActivty;
import com.chejdj.wanandroid.ui.home.HomeFragment;
import com.chejdj.wanandroid.ui.knowledgehierarchy.KnowledgeHierFragment;
import com.chejdj.wanandroid.ui.me.MeFragment;
import com.chejdj.wanandroid.ui.project.ProjectFragment;
import com.chejdj.wanandroid.ui.wechatofficalaccounts.WechatOfficalFragment;
import com.chejdj.wanandroid.ui.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends WanAndroidMvpBaseActivty {

    @BindView(R.id.navigationView)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    private int currentPosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager.setIsScroll(false);
        viewPager.setAdapter(new PagerFragmentAdapter(getFragments(),getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        bottomNavigationView.setLabelVisibilityMode(1);
        bottomNavigationView.setOnNavigationItemSelectedListener((menuItem) -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_home:
                    if (currentPosition != 0) {
                        currentPosition = 0;
                        viewPager.setCurrentItem(currentPosition);
                    }
                    break;
                case R.id.menu_knowledge_hierarchy:
                    if (currentPosition != 1) {
                        currentPosition = 1;
                        viewPager.setCurrentItem(currentPosition);
                    }
                    break;
                case R.id.meun_wechat_sub:
                    if(currentPosition !=2){
                        currentPosition=2;
                        viewPager.setCurrentItem(currentPosition);
                    }
                    break;
                case R.id.menu_project:
                    if (currentPosition != 3) {
                        currentPosition = 3;
                        viewPager.setCurrentItem(currentPosition);
                    }
                    break;
                case R.id.menu_me:
                    if (currentPosition != 4) {
                        currentPosition = 4;
                        viewPager.setCurrentItem(currentPosition);
                    }
                    break;
                default:
                    return true;
            }
            return true;
        });
        viewPager.setCurrentItem(currentPosition);
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new KnowledgeHierFragment());
        fragmentList.add(new WechatOfficalFragment());
        fragmentList.add(new ProjectFragment());
        fragmentList.add(new MeFragment());
        return fragmentList;
    }

}
