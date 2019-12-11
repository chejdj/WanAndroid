package com.chejdj.wanandroid.ui.commonarticlelist;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.network.bean.article.ArticleData;
import com.chejdj.wanandroid.ui.base.ViewPaperLazyLoadFragment;
import com.chejdj.wanandroid.ui.commonarticlelist.contract.CommonArticleListContract;
import com.chejdj.wanandroid.ui.commonarticlelist.presenter.CommonArticleListPresenter;
import com.chejdj.wanandroid.ui.webviewarticle.WebViewArticleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CommonArticleListFragment extends ViewPaperLazyLoadFragment implements CommonArticleListContract.View {
    private static final String CID = "director_cid";
    private static final String TYPE = "type";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.noData)
    LinearLayout noDataLayout;
    private int currentPage;
    private int totalPage;
    private int type;
    private int cid;
    private List<Article> articleList;
    private CommonArticleAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article_list;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        type = bundle.getInt(TYPE, 0);
        cid = bundle.getInt(CID, 0);
        if (type == 0 || cid == 0) {
            return;
        }
        articleList = new ArrayList<>();
        presenter = new CommonArticleListPresenter(this);
        adapter = new CommonArticleAdapter(R.layout.item_article, articleList);
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(() -> {
            if (currentPage + 1 <= totalPage) {
                startPresenterGetData(currentPage + 1);
            } else {
                adapter.loadMoreEnd();
            }
        }, recyclerView);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            WebViewArticleActivity.startArticleActivity(getActivity(), articleList.get(position), false);
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            currentPage = 0;
            startPresenterGetData(currentPage);
        });
    }

    @Override
    protected void loadData() {
        startPresenterGetData(currentPage);
    }

    private void startPresenterGetData(int pageNum) {
        if (presenter != null) {
            switch (type) {
                case 1:
                    ((CommonArticleListPresenter) presenter).getArticlesFromKnowledges(pageNum, cid);
                    break;
                case 2:
                    ((CommonArticleListPresenter) presenter).getArticlesFromProject(pageNum, cid);
                    break;
                case 3:
                    ((CommonArticleListPresenter) presenter).getArticlesFromWechatChapter(pageNum, cid);
                    break;
                default:
                    Log.e("common", "error type");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        swipeRefreshLayout.setOnRefreshListener(null);
    }

    /*
         cid:代表某个分类的代号
         type 代表协议
         1： 代表的是 知识体系下的文章
         2： 代表的是 项目体系下的文章
         */
    public static CommonArticleListFragment getInstance(int type, int cid) {
        CommonArticleListFragment fragment = new CommonArticleListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CID, cid);
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void updateArticles(ArticleData data) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (totalPage == 0) {
            totalPage = data.getPageCount();
        }
        if (data.getListData().isEmpty() && noDataLayout.getVisibility() == View.GONE) {
            noDataLayout.setVisibility(View.VISIBLE);
        } else if (noDataLayout.getVisibility() == View.GONE) {
            if (noDataLayout.getVisibility() == View.VISIBLE) {
                noDataLayout.setVisibility(View.GONE);
            }
            currentPage = data.getCurPage();
            adapter.addData(data.getListData());
            adapter.loadMoreComplete();
        }
    }

    @Override
    public void loadDataFail() {
        if (noDataLayout.getVisibility() == View.GONE) {
            noDataLayout.setVisibility(View.VISIBLE);
        }
    }
}
