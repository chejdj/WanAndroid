package com.chejdj.wanandroid.network;

import com.chejdj.wanandroid.network.api.ApiService;
import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.network.bean.homepage.HomeBannerData;
import com.chejdj.wanandroid.network.bean.hotkey.HotKeyData;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectoryRes;
import com.chejdj.wanandroid.network.bean.login.LoginStateBean;
import com.chejdj.wanandroid.network.cookie.CookieManager;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class HttpService {
    private static final String baseUrl = "https://www.wanandroid.com";
    private ApiService apiService;

    private static class Wrapper {
        static HttpService httpService = new HttpService();
    }

    private HttpService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieManager()).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static HttpService getInstance() {
        return Wrapper.httpService;
    }

    /*
     用户登陆
     */
    public Observable<LoginStateBean> userLogin(String username, String password) {
        return apiService.userLogin(username, password);
    }

    /*
    用户注册
     */
    public Observable<LoginStateBean> userRegister(String username, String password, String repasseord) {
        return apiService.userRegister(username, password, repasseord);
    }

    /*
      获取首页Banner数据
     */
    public Observable<HomeBannerData> getHomeBannerData() {
        return apiService.getHomeBannerData();
    }

    /*
      获取首页Artical数据
     */
    public Observable<ArticleDataRes> getHomeArticlesData(int pageNum) {
        return apiService.getHomeArticlesData(pageNum);
    }

    /*
     获取知识体系分类
     */
    public Observable<PrimaryArticleDirectoryRes> getKnowledgeTreeData() {
        return apiService.getKnowledgeTreeData();
    }

    /*
    获取某个知识体系下面的详细分类的文章
     */
    public Observable<ArticleDataRes> getKnowledgeTreeDetailArticleData(int pageNum, int cid) {
        return apiService.getKnowledgeTreeDetailArticleData(pageNum, cid);
    }

    /*
    获取项目分类
     */
    public Observable<PrimaryArticleDirectoryRes> getProjectTreeData() {
        return apiService.getProjectTreeData();
    }


    /*
    获取项目分类下面具体的文章
     */
    public Observable<ArticleDataRes> getProectTreeDetailArticleData(int pageNum, int cid) {
        return apiService.getProectTreeDetailArticleData(pageNum, cid);
    }

    /*
    获取搜索热词
     */
    public Observable<HotKeyData> getSearchHotKey() {
        return apiService.getSearchHotKey();
    }

    /*
    全局搜索文章
     */
    public Observable<ArticleDataRes> getSearchArticles(int pageNum, String keywords) {
        return apiService.getSearchResult(pageNum, keywords);
    }

    /*
    获取该用户的收藏文章
     */
    public Observable<ArticleDataRes> getCollectedArticles(int pageNum) {
        return apiService.getCollectArticles(pageNum);
    }

    /*
    收藏站内文章
     */
    public Observable<ArticleDataRes> collectWanAndroidArticle(int articleId) {
        return apiService.collectWanAndroidArticle(articleId);
    }

    /*
    取消收藏文章
     */
    public Observable<ArticleDataRes> cancelCollectArticle(int articleId) {
        return apiService.cancelCollectedArticle(articleId);
    }

    /*
    获取微信公共号列表
     */
    public Observable<PrimaryArticleDirectoryRes> getWechatArticlChapter() {
        return apiService.getWechatArticleChatper();
    }

    /*
    获取微信公共号历史文章
     */
    public Observable<ArticleDataRes> getWechatChapterArticles(int cid, int pageNum) {
        return apiService.getWechatChapterArticles(cid, pageNum);
    }

    /*
     在某个公共号里面搜索历史文章
     */
    public Observable<ArticleDataRes> searchWechatChapterArticles(int cid, int pageNum, String keywords) {
        return apiService.searchWechatChapterArticles(cid, pageNum, keywords);
    }
}
