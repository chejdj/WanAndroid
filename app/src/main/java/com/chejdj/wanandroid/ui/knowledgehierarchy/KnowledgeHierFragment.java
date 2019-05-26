package com.chejdj.wanandroid.ui.knowledgehierarchy;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectory;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectoryRes;
import com.chejdj.wanandroid.ui.base.FragmentManagerLazyLoadFragment;
import com.chejdj.wanandroid.ui.base.WanAndroidBaseFragment;
import com.chejdj.wanandroid.ui.knowledgehierarchy.contract.KnowledgeHierContract;
import com.chejdj.wanandroid.ui.knowledgehierarchy.presenter.KnowledgeHierPresenter;
import com.chejdj.wanandroid.ui.subjectarticle.SubjectArticleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class KnowledgeHierFragment extends FragmentManagerLazyLoadFragment implements KnowledgeHierContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.networkError)
    RelativeLayout networkError;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private List<PrimaryArticleDirectory> articleDirectoryList;
    private KonwledgeHierAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_hier;
    }

    @Override
    protected void initView() {
        articleDirectoryList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new KonwledgeHierAdapter(R.layout.item_knowledge_hier, articleDirectoryList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            SubjectArticleActivity.startSubArticleActivity(getActivity(), articleDirectoryList.get(position).getName(), (ArrayList) articleDirectoryList.get(position).getChildren());
        });
        presenter = new KnowledgeHierPresenter(this);
    }

    @Override
    protected void loadData() {
        ((KnowledgeHierPresenter) presenter).getDetailKnowledgeHier();
    }

    @Override
    public void updateDetailKnowledgeHier(PrimaryArticleDirectoryRes directory) {
        if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (networkError.getVisibility() == View.VISIBLE) {
            networkError.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
        adapter.addData(directory.getData());
        adapter.loadMoreComplete();
    }

    @Override
    public void networkError() {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
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
        ((KnowledgeHierPresenter) presenter).getDetailKnowledgeHier();
    }
}
