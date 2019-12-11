package com.chejdj.wanandroid.ui.subjectarticle;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.network.bean.knowledgesystem.SecondaryArticleDirectory;
import com.chejdj.wanandroid.ui.base.WanAndroidBaseActivity;
import com.chejdj.wanandroid.ui.commonarticlelist.CommonPagerFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//用于显示体系下，或者某个项目的专题文章
public class SubjectArticleActivity extends WanAndroidBaseActivity {
    private static final String SUB_TITLE = "title";
    private static final String DETAIL_DIRECTORY = "directory";
    private static final int TYPE_COMMON_LIST_FRAGMENT = 1;//代表和CommonArticleListFragment的协议
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.title)
    TextView titleTx;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    private List<SecondaryArticleDirectory> secondaryArticleDirectoryList;
    private List<Integer> cidNumbers;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_subject_article;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(SUB_TITLE);
        secondaryArticleDirectoryList = intent.getParcelableArrayListExtra(DETAIL_DIRECTORY);

        titleTx.setText(title);
        List<String> subTitles = new ArrayList<>();
        cidNumbers = new ArrayList<>();
        for (SecondaryArticleDirectory directory : secondaryArticleDirectoryList) {
            String directoryName = directory.getName();
            subTitles.add(directoryName);
            cidNumbers.add(directory.getId());
            tabLayout.addTab(tabLayout.newTab().setText(directoryName));
        }
        CommonPagerFragmentAdapter adapter = new CommonPagerFragmentAdapter(subTitles, cidNumbers, TYPE_COMMON_LIST_FRAGMENT, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }

    @OnClick(R.id.back)
    public void back() {
        secondaryArticleDirectoryList = null;
        finish();
    }

    public static void startSubArticleActivity(Context context, String title, ArrayList<SecondaryArticleDirectory> detailData) {
        Intent intent = new Intent(context, SubjectArticleActivity.class);
        intent.putExtra(SUB_TITLE, title);
        intent.putParcelableArrayListExtra(DETAIL_DIRECTORY, detailData);
        context.startActivity(intent);
    }
}
