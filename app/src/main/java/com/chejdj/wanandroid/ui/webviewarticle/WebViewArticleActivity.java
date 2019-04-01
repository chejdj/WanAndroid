package com.chejdj.wanandroid.ui.webviewarticle;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.text.HtmlCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.db.account.AccountManager;
import com.chejdj.wanandroid.event.UnCollectArticleSucEvent;
import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.ui.base.WanAndroidBaseActivty;
import com.chejdj.wanandroid.ui.webviewarticle.contract.WebViewArticleContract;
import com.chejdj.wanandroid.ui.webviewarticle.presenter.WebViewArticlePresenter;
import com.chejdj.wanandroid.util.StringUtil;
import com.chejdj.wanandroid.util.wxshare.WxShareDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.v4.text.HtmlCompat.FROM_HTML_MODE_LEGACY;


//从首页进来的，都默认为未收藏状态(因为没有这个接口，查询是否收藏)
//从收藏页过来的，都是收藏状态
public class WebViewArticleActivity extends WanAndroidBaseActivty implements WebViewArticleContract.View {
    private static final String ARTICLE_NAME = "article";
    private static final String COLLECT_STATE = "collect_state";
    @BindView(R.id.articleToolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.collect)
    FloatingActionButton collectButton;
    private boolean collectState;
    private Article article;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview_article;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        article = intent.getParcelableExtra(ARTICLE_NAME);
        collectState = intent.getBooleanExtra(COLLECT_STATE, false);
        if (collectState) {
            collectButton.setIconDrawable(getDrawable(R.drawable.collected));
        }
        if (article == null) {
            return;
        }
        toolbar.setTitle(HtmlCompat.fromHtml(article.getTitle(), FROM_HTML_MODE_LEGACY).toString());
        toolbar.setNavigationOnClickListener(view -> finish());
        initWebView();

        presenter = new WebViewArticlePresenter(this);
        ((WebViewArticlePresenter) presenter).start(article);
    }

    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        //允许SessionStorage/LocalStorage存储
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(this.getDir("appacache", MODE_PRIVATE).getPath());
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setBlockNetworkImage(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                webView.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webSettings.setBlockNetworkImage(false);
                super.onPageFinished(view, url);
            }
        });
        webView.setOnKeyListener((view, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
            return false;
        });
        webView.loadUrl(article.getLink());
    }

    @OnClick(R.id.share)
    public void shareArticle() {
        WxShareDialog.showWxShareDialog(this, getWindow().getDecorView().findViewById(android.R.id.content), article);
    }


    @OnClick(R.id.collect)
    public void collectArticle() {
        if (AccountManager.getInstance().isLogin()) {
            if (collectState) {
                ((WebViewArticlePresenter) presenter).cancelCollect(article);
            } else {
                ((WebViewArticlePresenter) presenter).collect(article);
            }
        } else {
            Toast.makeText(this, StringUtil.getString(this, R.string.please_login), Toast.LENGTH_SHORT).show();
        }
    }


    // collectState: 只有从收藏页面进来才是收藏状态，其他都是不收藏状态
    public static void startArticleActivity(Activity activity, Article originArticle, boolean collectState) {
        Intent intent = new Intent(activity, WebViewArticleActivity.class);
        intent.putExtra(ARTICLE_NAME, originArticle);
        intent.putExtra(COLLECT_STATE, collectState);
        activity.startActivity(intent);
    }

    @Override
    public void collectState(boolean collect) {
        if (collect) {
            if (!collectState) {
                collectState = true;
                collectButton.setIconDrawable(getDrawable(R.drawable.collected));
            }
            EventBus.getDefault().post(new UnCollectArticleSucEvent());
        } else {
            Toast.makeText(this, StringUtil.getString(this, R.string.collect_fail), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void cancelCollectState(boolean isCancelCollect) {
        if (isCancelCollect) {
            if (collectState) {
                collectState = false;
                collectButton.setIconDrawable(getDrawable(R.drawable.collect_no));
            }
            EventBus.getDefault().post(new UnCollectArticleSucEvent());
        } else {
            Toast.makeText(this, StringUtil.getString(this, R.string.cancel_collect_fail), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void networkError() {
        //TODO
    }
}
