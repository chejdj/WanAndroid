package com.chejdj.wanandroid.ui.home;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.network.bean.article.ArticleData;
import com.chejdj.wanandroid.network.bean.homepage.HomeBanner;
import com.chejdj.wanandroid.ui.base.FragmentManagerLazyLoadFragment;
import com.chejdj.wanandroid.ui.base.WanAndroidBaseFragment;
import com.chejdj.wanandroid.ui.commonarticlelist.CommonArticleAdapter;
import com.chejdj.wanandroid.ui.home.contract.HomeContract;
import com.chejdj.wanandroid.ui.home.presenter.HomePresenter;
import com.chejdj.wanandroid.ui.search.SearchActivity;
import com.chejdj.wanandroid.ui.webviewarticle.WebViewArticleActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeFragment extends FragmentManagerLazyLoadFragment implements HomeContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.networkError)
    RelativeLayout networkError;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private List<Article> articleList;
    private List<HomeBanner> bannerList;
    private Banner homeBanner;
    private int currentPage = 0;
    private int totalPage = 0;
    private CommonArticleAdapter commonArticleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        initData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        FrameLayout headView = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.banner_home, null);
        homeBanner = headView.findViewById(R.id.home_banner);
        headView.removeView(homeBanner);
        homeBanner.setImageLoader(new HomeBannerLoader());
        homeBanner.setImages(bannerList);
        homeBanner.start();

        commonArticleAdapter = new CommonArticleAdapter(R.layout.item_article, articleList);
        commonArticleAdapter.addHeaderView(homeBanner);
        commonArticleAdapter.setEnableLoadMore(true);

        recyclerView.setAdapter(commonArticleAdapter);
        refreshAndloadMore();
    }

    private void initData() {
        articleList = new ArrayList<>();
        bannerList = new ArrayList<>();
        presenter = new HomePresenter(this);
    }

    @Override
    protected void loadData() {
        ((HomePresenter) presenter).start();
    }

    @Override
    protected boolean isDataEmpty() {
        return articleList == null || articleList.size() == 0 || bannerList == null || bannerList.size() == 0;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (homeBanner != null) {
            homeBanner.stopAutoPlay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (homeBanner != null) {
            homeBanner.startAutoPlay();
        }
    }

    private void refreshAndloadMore() {
        commonArticleAdapter.setOnLoadMoreListener(() -> {
            if (currentPage + 1 <= totalPage) {
                ((HomePresenter) presenter).getArticles(currentPage + 1);
            } else {
                commonArticleAdapter.loadMoreEnd();
            }
        }, recyclerView);
        refreshLayout.setOnRefreshListener(() -> ((HomePresenter) presenter).start());
        homeBanner.setOnBannerListener((position) -> {
            HomeBanner banner = bannerList.get(position);
            Article article = new Article();
            article.setTitle(banner.getTitle());
            article.setLink(banner.getUrl());
            article.setPublishTime(0);
            article.setAuthor(banner.getDesc());
            WebViewArticleActivity.startArticleActivity(getActivity(), article, false);
        });
        commonArticleAdapter.setOnItemClickListener((adapter, view, position) -> {
            Article article = articleList.get(position);
            WebViewArticleActivity.startArticleActivity(getActivity(), article, false);
        });
    }

    //获取banner数据
    @Override
    public void showBanner(List<HomeBanner> images) {
        if (networkError.getVisibility() == View.VISIBLE) {
            networkError.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
        if (refreshLayout.getVisibility() == View.GONE) {
            refreshLayout.setVisibility(View.VISIBLE);
        }
        homeBanner.update(images);
    }

    //获取到Articles
    @Override
    public void showArticles(ArticleData articleData) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if (networkError.getVisibility() == View.VISIBLE) {
            networkError.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
        if (refreshLayout.getVisibility() == View.GONE) {
            refreshLayout.setVisibility(View.VISIBLE);
        }
        if (totalPage == 0) {
            totalPage = articleData.getPageCount();
        }
        currentPage = articleData.getCurPage();
        commonArticleAdapter.addData(articleData.getListData());
        commonArticleAdapter.loadMoreComplete();
    }

    @Override
    public void networkError() {
        if (refreshLayout.getVisibility() == View.VISIBLE) {
            refreshLayout.setVisibility(View.GONE);
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
        ((HomePresenter) presenter).start();
    }

    @OnClick(R.id.home_search)
    public void intentToSearchActivity() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }
}
