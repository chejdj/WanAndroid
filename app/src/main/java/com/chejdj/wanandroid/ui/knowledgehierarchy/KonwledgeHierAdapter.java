package com.chejdj.wanandroid.ui.knowledgehierarchy;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectory;
import com.chejdj.wanandroid.network.bean.knowledgesystem.SecondaryArticleDirectory;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class KonwledgeHierAdapter extends BaseQuickAdapter<PrimaryArticleDirectory, KonwledgeHierAdapter.KnowledgeHierViewHodler> {

    public KonwledgeHierAdapter(int layoutResId, @Nullable List<PrimaryArticleDirectory> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(KnowledgeHierViewHodler helper, PrimaryArticleDirectory item) {
        helper.title.setText(item.getName());
        helper.updateTagsFlowLayout(item.getChildren());
    }

    static class KnowledgeHierViewHodler extends BaseViewHolder {
        private final List<SecondaryArticleDirectory> secondaryArticleDirectoryList;
        private final TextView title;
        private final TagFlowLayout tagFlowLayout;

        public KnowledgeHierViewHodler(View view) {
            super(view);
            secondaryArticleDirectoryList = new ArrayList<>();
            title = view.findViewById(R.id.title);
            tagFlowLayout = view.findViewById(R.id.flowLayout_tags);
            tagFlowLayout.setAdapter(new TagAdapter<SecondaryArticleDirectory>(secondaryArticleDirectoryList) {
                @Override
                public View getView(FlowLayout parent, int position, SecondaryArticleDirectory o) {
                    TextView tv = (TextView) LayoutInflater.from(view.getContext()).inflate(R.layout.item_flow_tags, tagFlowLayout, false);
                    tv.setText(o.getName());
                    return tv;
                }
            });
        }


        public void updateTagsFlowLayout(List<SecondaryArticleDirectory> data) {
            secondaryArticleDirectoryList.clear();
            secondaryArticleDirectoryList.addAll(data);
            tagFlowLayout.getAdapter().notifyDataChanged();
        }
    }
}
