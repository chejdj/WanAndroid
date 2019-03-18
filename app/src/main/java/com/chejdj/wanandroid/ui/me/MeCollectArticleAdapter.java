package com.chejdj.wanandroid.ui.me;

import android.support.annotation.Nullable;
import android.support.v4.text.HtmlCompat;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.util.StringUtil;

import java.util.List;



import static android.support.v4.text.HtmlCompat.FROM_HTML_MODE_LEGACY;

public class MeCollectArticleAdapter extends BaseQuickAdapter<Article, MeCollectArticleAdapter.MeCollectArticleHolder> {
    public MeCollectArticleAdapter(int layoutResId, @Nullable List<Article> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MeCollectArticleHolder helper, Article item) {
        helper.title.setText(HtmlCompat.fromHtml(item.getTitle(), FROM_HTML_MODE_LEGACY));
        helper.time.setText(StringUtil.timeToString(item.getPublishTime()));
        helper.author.setText(item.getAuthor());
    }

    static class MeCollectArticleHolder extends BaseViewHolder {
        private final TextView title;
        private final TextView time;
        private final TextView author;

        public MeCollectArticleHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            time = view.findViewById(R.id.time);
            author = view.findViewById(R.id.author);
        }
    }
}
