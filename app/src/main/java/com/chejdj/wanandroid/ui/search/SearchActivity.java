package com.chejdj.wanandroid.ui.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.network.bean.article.ArticleData;
import com.chejdj.wanandroid.network.bean.hotkey.HotKey;
import com.chejdj.wanandroid.ui.base.WanAndroidMvpBaseActivty;
import com.chejdj.wanandroid.ui.commonarticlelist.CommonArticleAdapter;
import com.chejdj.wanandroid.ui.search.contract.SearchContract;
import com.chejdj.wanandroid.ui.search.presenter.SearchPresenter;
import com.chejdj.wanandroid.ui.webviewarticle.WebViewArticleActivity;
import com.chejdj.wanandroid.util.NetUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends WanAndroidMvpBaseActivty implements SearchContract.View {
    @BindView(R.id.searchTx)
    EditText searchTx;
    @BindView(R.id.hot_searchTx)
    TextView hot_searchTx;
    @BindView(R.id.flowLayout_tags)
    TagFlowLayout tagFlowLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<HotKey> hotKeyList;
    private List<Article> articleList;
    private CommonArticleAdapter adapter;
    private int currentPage = 0;
    private int totalPage = 0;
    private String currentKeywords;
    private LayoutInflater mInflater;

    @Override
    protected int getLayoutId() {
        if (NetUtils.getNetWorkState() < 0) {
            return R.layout.network_error;
        }
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        hotKeyList = new ArrayList<>();
        articleList = new ArrayList<>();
        mInflater = LayoutInflater.from(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new CommonArticleAdapter(R.layout.item_article, articleList);
        adapter.setEnableLoadMore(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        presenter = new SearchPresenter(this);
        ((SearchPresenter) presenter).getHotKeys();

        setListener();
    }

    private void setListener() {

        adapter.setOnLoadMoreListener(() -> {
            if (currentPage + 1 <= totalPage) {
                ((SearchPresenter) presenter).getSearchArticles(currentPage + 1, currentKeywords);
            } else {
                adapter.loadMoreEnd();
            }
        }, recyclerView);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            Article article = articleList.get(position);
            WebViewArticleActivity.startArticleActivity(this, article, false);
        });

        searchTx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String editText = editable.toString();
                if (editText == null || editText.length() == 0) {
                    showHotKeys();
                } else {
                    currentPage = 0;
                    currentKeywords = editable.toString();
                    ((SearchPresenter) presenter).getSearchArticles(currentPage, currentKeywords);
                }
            }
        });
        tagFlowLayout.setAdapter(new TagAdapter<HotKey>(hotKeyList) {
            @Override
            public View getView(FlowLayout parent, int position, HotKey o) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_tags, tagFlowLayout, false);
                tv.setText(o.getName());
                return tv;
            }
        });
        tagFlowLayout.setOnTagClickListener((view, position, parent) -> {
            currentKeywords = hotKeyList.get(position).getName();
            ((SearchPresenter) presenter).getSearchArticles(currentPage, currentKeywords);
            return true;
        });


    }


    @Override
    public void updateHotKeys(List<HotKey> keyList) {
        hotKeyList.addAll(keyList);
        tagFlowLayout.getAdapter().notifyDataChanged();
    }

    @Override
    public void updateSearchArticles(ArticleData articleData) {
        if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (tagFlowLayout.getVisibility() == View.VISIBLE) {
            tagFlowLayout.setVisibility(View.GONE);
        }
        if (hot_searchTx.getVisibility() == View.VISIBLE) {
            hot_searchTx.setVisibility(View.GONE);
        }
        if (currentPage == 0) {
            adapter.replaceData(articleData.getListData());
        } else {
            adapter.addData(articleData.getListData());
        }
        currentPage = articleData.getCurPage();
        totalPage = articleData.getPageCount();
        adapter.loadMoreComplete();
    }

    @Override
    public void showHotKeys() {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
            currentPage = 0;
        }
        if (tagFlowLayout.getVisibility() == View.GONE) {
            tagFlowLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }
}
