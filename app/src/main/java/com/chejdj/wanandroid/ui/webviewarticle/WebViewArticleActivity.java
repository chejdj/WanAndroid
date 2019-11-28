package com.chejdj.wanandroid.ui.webviewarticle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.text.HtmlCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.WanAndroidApplication;
import com.chejdj.wanandroid.db.account.AccountManager;
import com.chejdj.wanandroid.event.UnCollectArticleSucEvent;
import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.ui.webviewarticle.contract.WebViewArticleContract;
import com.chejdj.wanandroid.ui.webviewarticle.presenter.WebViewArticlePresenter;
import com.chejdj.wanandroid.ui.webviewarticle.sonic.SonicRuntimeImpl;
import com.chejdj.wanandroid.ui.webviewarticle.sonic.SonicSessionClientImpl;
import com.chejdj.wanandroid.util.DisplayUtils;
import com.chejdj.wanandroid.util.StringUtils;
import com.chejdj.wanandroid.common.wxshare.WxShareDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v4.text.HtmlCompat.FROM_HTML_MODE_LEGACY;


//从首页进来的，都默认为未收藏状态(因为没有这个接口，查询是否收藏)
//从收藏页过来的，都是收藏状态
public class WebViewArticleActivity extends AppCompatActivity implements WebViewArticleContract.View {
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
    private WebViewArticleContract.Presenter presenter;
    private SonicSession sonicSession;
    private SonicSessionClientImpl sonicSessionClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        Intent intent = getIntent();
        article = intent.getParcelableExtra(ARTICLE_NAME);
        collectState = intent.getBooleanExtra(COLLECT_STATE, false);
        initSonic();
        setContentView(R.layout.activity_webview_article);
        DisplayUtils.setCustomDensity(this, WanAndroidApplication.getMyApplication());
        ButterKnife.bind(this);
        initView();
        initWebView();
    }

    private void initSonic() {
        // init sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
        }

        sonicSessionClient = new SonicSessionClientImpl();
        SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
        sessionConfigBuilder.setSupportLocalServer(true);
        // create sonic session and run sonic flow
        sonicSession = SonicEngine.getInstance().createSession(article.getLink(), sessionConfigBuilder.build());
        if (null != sonicSession) {
            sonicSession.bindClient(sonicSessionClient);
        } else {
            Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show();
        }
    }


    protected void initView() {
        if (collectState) {
            collectButton.setIconDrawable(getDrawable(R.drawable.collected));
        }
        if (article == null) {
            return;
        }
        toolbar.setTitle(HtmlCompat.fromHtml(article.getTitle(), FROM_HTML_MODE_LEGACY).toString());
        toolbar.setNavigationOnClickListener(view -> finish());

        presenter = new WebViewArticlePresenter(this);
        presenter.start(article);
    }

    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(this.getDir("appacache", MODE_PRIVATE).getPath());
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setBlockNetworkImage(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSaveFormData(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (sonicSession != null) {
                    sonicSession.getSessionClient().pageFinish(url);
                }
            }

            @TargetApi(21)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                if (sonicSession != null) {
                    return (WebResourceResponse) sonicSession.getSessionClient().requestResource(request.getUrl().toString());
                }
                return null;
            }
        });
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webView);
            sonicSessionClient.clientReady();
        } else {
            webView.loadUrl(article.getLink());
        }
    }

    @Override
    protected void onDestroy() {
        if (sonicSession != null) {
            sonicSession.destroy();
            sonicSession = null;
        }
        super.onDestroy();
    }

    @OnClick(R.id.share)
    public void shareArticle() {
        WxShareDialog.showWxShareDialog(this, getWindow().getDecorView().findViewById(android.R.id.content), article);
    }


    @OnClick(R.id.collect)
    public void collectArticle() {
        if (AccountManager.getInstance().isLogin()) {
            if (collectState) {
                presenter.cancelCollect(article);
            } else {
                presenter.collect(article);
            }
        } else {
            Toast.makeText(this, StringUtils.getString(this, R.string.please_login), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, StringUtils.getString(this, R.string.collect_fail), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, StringUtils.getString(this, R.string.cancel_collect_fail), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void networkError() {
        //TODO
    }
}
