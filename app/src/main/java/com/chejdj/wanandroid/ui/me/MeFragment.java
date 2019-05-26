package com.chejdj.wanandroid.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.db.account.AccountManager;
import com.chejdj.wanandroid.db.account.entity.Account;
import com.chejdj.wanandroid.event.LoginSuccessEvent;
import com.chejdj.wanandroid.event.UnCollectArticleSucEvent;
import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.network.bean.article.ArticleData;
import com.chejdj.wanandroid.ui.base.FragmentManagerLazyLoadFragment;
import com.chejdj.wanandroid.ui.login.LoginActivity;
import com.chejdj.wanandroid.ui.me.contract.MeContract;
import com.chejdj.wanandroid.ui.me.presenter.MePresneter;
import com.chejdj.wanandroid.ui.webviewarticle.WebViewArticleActivity;
import com.chejdj.wanandroid.util.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MeFragment extends FragmentManagerLazyLoadFragment implements MeContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.picture)
    ImageView picture;
    @BindView(R.id.me_warning)
    TextView warningTx;
    @BindView(R.id.toolbar)
    CollapsingToolbarLayout toolbar;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    private List<Article> listData;
    private MeCollectArticleAdapter adapter;
    private int currentPage = 0;
    private int totalPage = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void initView() {
        listData = new ArrayList<>();
        currentPage = 0;
        if (!AccountManager.getInstance().isLogin()) {
            scrollView.setVisibility(View.VISIBLE);
            warningTx.setText(StringUtil.getString(getContext(), R.string.logout_collect_warning));
            recyclerView.setVisibility(View.GONE);
        } else {
            if (scrollView.getVisibility() == View.VISIBLE) {
                scrollView.setVisibility(View.GONE);
            }
            if (recyclerView.getVisibility() == View.GONE) {
                recyclerView.setVisibility(View.VISIBLE);
            }

            toolbar.setTitle(AccountManager.getInstance().getCurrentAccount().getUsername());
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);
            adapter = new MeCollectArticleAdapter(R.layout.item_collect_article, listData);
            adapter.setEnableLoadMore(true);
            adapter.setOnItemClickListener((adatper, view, position) -> {
                WebViewArticleActivity.startArticleActivity(getActivity(), listData.get(position), true);
            });
            adapter.setOnLoadMoreListener(() -> {
                if (currentPage + 1 <= totalPage) {
                    ((MePresneter) presenter).loadCollectArticleFromIn(currentPage + 1);
                } else {
                    adapter.loadMoreEnd();
                }
            }, recyclerView);
            recyclerView.setAdapter(adapter);
            presenter = new MePresneter(this);
        }

    }

    @Override
    protected void loadData() {
        if (AccountManager.getInstance().isLogin()) {
            ((MePresneter) presenter).loadCollectArticleFromIn(currentPage);
        }
    }

    @OnClick(R.id.picture)
    public void loginActivity() {
        if (!AccountManager.getInstance().isLogin()) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        } else {
            //弹窗提示退出
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.Theme_MaterialComponents_Light_Dialog_Alert)
                    .setCancelable(true)
                    .setNegativeButton("OK", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        toolbar.setTitle("Login");
                        scrollView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        AccountManager.getInstance().clearAccount();
                    })
                    .setPositiveButton("No No No", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    })
                    .setMessage(StringUtil.getString(getContext(), R.string.logout_warning)).create();
            alertDialog.show();
        }
    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    private void refreshData() {
        if (presenter != null) {
            currentPage = 0;
            listData.clear();
            adapter.notifyDataSetChanged();
            ((MePresneter) presenter).loadCollectArticleFromIn(currentPage);
        }
    }

    @Override
    public void showCollectArticle(ArticleData articleData) {
        currentPage = articleData.getCurPage();
        if (totalPage == 0) {
            totalPage = articleData.getPageCount();
        }
        if ((articleData.getListData() == null || articleData.getListData().size() == 0)) {
            if (scrollView.getVisibility() == View.GONE) {
                scrollView.setVisibility(View.VISIBLE);
            }
            warningTx.setText(StringUtil.getString(getContext(), R.string.no_collected_article));
        } else {
            if (scrollView.getVisibility() == View.VISIBLE) {
                scrollView.setVisibility(View.GONE);
            }
        }
        listData.addAll(articleData.getListData());
        adapter.loadMoreComplete();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuccessfulEvent(LoginSuccessEvent event) {
        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void needRefreshView(UnCollectArticleSucEvent event) {
        refreshData();
    }

}
