package com.chejdj.wanandroid.ui.commonarticlelist;

import android.support.annotation.Nullable;
import android.support.v4.text.HtmlCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.util.StringUtil;

import java.util.List;

import static android.support.v4.text.HtmlCompat.FROM_HTML_MODE_LEGACY;


//全局通用的Adpater
public class CommonArticleAdapter extends BaseQuickAdapter<Article, CommonArticleAdapter.CommonArticleHolder> {
    private static final String TAG_PROJECT = "项目";

    public CommonArticleAdapter(int layoutResId, @Nullable List<Article> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(CommonArticleHolder helper, Article item) {
        String title = HtmlCompat.fromHtml(item.getTitle(), FROM_HTML_MODE_LEGACY).toString();
        String author = item.getAuthor();
        String description = item.getDesc();
        if (description != null && description.length() > 0) {
            description = HtmlCompat.fromHtml(description, FROM_HTML_MODE_LEGACY).toString();
        }
        String category = item.getSuperChapterName() + "/" + item.getChapterName();
        String tags = item.getTags() == null || item.getTags().size() == 0 ? "分类" : item.getTags().get(0).getName();
        String time = StringUtil.timeToString(item.getPublishTime());
        if (tags.equals(TAG_PROJECT) && !StringUtil.isEmpty(item.getEnvelopePic())) {
            helper.projectImageView.setVisibility(View.VISIBLE);
            Glide.with(helper.itemView).load(item.getEnvelopePic()).into(helper.projectImageView);
        } else {
            if (helper.projectImageView.getVisibility() == View.VISIBLE) {
                helper.projectImageView.setVisibility(View.GONE);
            }
        }
        if (description != null && description.length() > 0) {
            helper.articleDescription.setVisibility(View.VISIBLE);
            helper.articleDescription.setText(description);
        } else {
            helper.articleDescription.setVisibility(View.GONE);
        }
        helper.articleTitle.setText(title);
        helper.articleAuthor.setText(author);
        helper.articleCategory.setText(category);
        helper.articleTags.setText(tags);
        helper.articleTime.setText(time);
    }

    class CommonArticleHolder extends BaseViewHolder {
        private final TextView articleTitle;
        private final TextView articleDescription;
        private final TextView articleTime;
        private final TextView articleAuthor;
        private final TextView articleTags;
        private final TextView articleCategory;
        private final ImageView projectImageView;

        public CommonArticleHolder(View view) {
            super(view);
            articleTitle = view.findViewById(R.id.title);
            articleDescription = view.findViewById(R.id.description);
            articleTime = view.findViewById(R.id.time);
            articleAuthor = view.findViewById(R.id.author);
            articleTags = view.findViewById(R.id.tags);
            articleCategory = view.findViewById(R.id.category);
            projectImageView = view.findViewById(R.id.project_image);
        }
    }
}
