package com.chejdj.wanandroid.network.api;

import com.chejdj.wanandroid.network.bean.article.ArticleDataRes;
import com.chejdj.wanandroid.network.bean.homepage.HomeBannerData;
import com.chejdj.wanandroid.network.bean.hotkey.HotKeyData;
import com.chejdj.wanandroid.network.bean.knowledgesystem.PrimaryArticleDirectoryRes;
import com.chejdj.wanandroid.network.bean.login.LoginStateBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("user/login")
    @FormUrlEncoded
    Observable<LoginStateBean> userLogin(@Field("username") String username, @Field("password") String password);

    @POST("user/register")
    @FormUrlEncoded
    Observable<LoginStateBean> userRegister(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);


    /* 获取首页Banner信息
     */
    @GET("banner/json")
    Observable<HomeBannerData> getHomeBannerData();

    /*
      获取首页Article信息
     */
    @GET("article/list/{pageNum}/json")
    Observable<ArticleDataRes> getHomeArticlesData(@Path("pageNum") int pageNum);

    /*
       获取知识体系的主标题和副标题
     */
    @GET("tree/json")
    Observable<PrimaryArticleDirectoryRes> getKnowledgeTreeData();



   /*
      获取知识体系下详细的文章
      参数介绍：
      pageNum:页码从0开始
      cid : 分类的id,也是当前二级目录的id
    */

    @GET("article/list/{pageNum}/json")
    Observable<ArticleDataRes> getKnowledgeTreeDetailArticleData(@Path("pageNum") int pageNum, @Query("cid") int cid);

    /*
       获取项目分类
     */
    @GET("project/tree/json")
    Observable<PrimaryArticleDirectoryRes> getProjectTreeData();

    /*
      获取某个项目分类的项目
      参数介绍：
      pageNum: 页码从1开始
      cid:项目的分类id

     */
    @GET("project/list/{pageNum}/json")
    Observable<ArticleDataRes> getProectTreeDetailArticleData(@Path("pageNum") int pageNum, @Query("cid") int cid);

    /*
     获取搜索热点
     */
    @GET("hotkey/json")
    Observable<HotKeyData> getSearchHotKey();

    /*
    全局搜索
     */
    @POST("article/query/{pageNum}/json")
    @FormUrlEncoded
    Observable<ArticleDataRes> getSearchResult(@Path("pageNum") int pageNum, @Field("k") String keywords);

    /*
    获取的收藏的文章
     */

    @GET("lg/collect/list/{pageNum}/json")
    Observable<ArticleDataRes> getCollectArticles(@Path("pageNum") int pageNum);

    /*
    取消收藏的文章
     */
    @POST("lg/uncollect/{articleId}/json")
    @FormUrlEncoded
    Observable<ArticleDataRes> cancelCollectedArticle(@Path("articleId") int articleId,@Field("originId")int originId);

    /*
    收藏站内文章
     */
    @POST("lg/collect/{articleId}/json")
    Observable<ArticleDataRes> collectWanAndroidArticle(@Path("articleId") int articleId);

    /*
    获取微信公共号的列表
     */
    @GET("wxarticle/chapters/json")
    Observable<PrimaryArticleDirectoryRes> getWechatArticleChatper();

    /*
    查看微信公众号历史文章
     */
    @GET("wxarticle/list/{cid}/{pageNum}/json")
    Observable<ArticleDataRes> getWechatChapterArticles(@Path("cid") int cid, @Path("pageNum") int pageNum);

    /*
    在某个公共号里面搜索历史文章
     */
    @GET("wxarticle/list/{cid}/{pageNum}/json")
    Observable<ArticleDataRes> searchWechatChapterArticles(@Path("cid") int cid, @Path("pageNum") int pageNum, @Query("k") String keywords);
}
