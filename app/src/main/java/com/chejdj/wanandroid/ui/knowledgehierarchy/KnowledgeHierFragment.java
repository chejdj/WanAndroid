package com.chejdj.wanandroid.ui.knowledgehierarchy;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectory;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectoryRes;
import com.chejdj.wanandroid.ui.base.WanAndroidBaseFragment;
import com.chejdj.wanandroid.ui.knowledgehierarchy.contract.KnowledgeHierContract;
import com.chejdj.wanandroid.ui.knowledgehierarchy.presenter.KnowledgeHierPresenter;
import com.chejdj.wanandroid.ui.subjectarticle.SubjectArticleActivity;
import com.chejdj.wanandroid.util.NetUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class KnowledgeHierFragment extends WanAndroidBaseFragment implements KnowledgeHierContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<PrimaryArticleDirectory> articleDirectoryList;
    private KonwledgeHierAdapter adapter;

    @Override
    protected int getLayoutId() {
        if(NetUtils.getNetWorkState()<0){
            return R.layout.network_error;
        }
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
        ((KnowledgeHierPresenter) presenter).getDetailKnowledgeHier();
    }

    @Override
    public void updateDetailKnowledgeHier(PrimaryArticleDirectoryRes directory) {
        adapter.addData(directory.getData());
        adapter.loadMoreComplete();
    }
}
