package com.chejdj.wanandroid.db.tablemanager;

import com.chejdj.wanandroid.db.ObjectBox;
import com.chejdj.wanandroid.db.account.AccountManager;
import com.chejdj.wanandroid.db.entity.CollectArticleDB;
import com.chejdj.wanandroid.db.entity.CollectArticleDB_;
import com.chejdj.wanandroid.network.bean.article.Article;
import com.chejdj.wanandroid.util.SharedPreferenceUtil;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;
import io.reactivex.Observable;


public class CollectArticleTableManager {
    //原因是box is not aviable
    private static Box<CollectArticleDB> collectArticleBox;
    private static final String CACHE_FILE_NAME = "cache_time";
    private static final String CACHE_COLLECT_ARTICLE_KEY = "collect_article_cache_time";
    private static final long dayTimeMillis = 24 * 60 * 60 * 1000;

    private CollectArticleTableManager() {
        collectArticleBox = ObjectBox.getBoxStore().boxFor(CollectArticleDB.class);
    }

    private static class WrapperInstance {
        static CollectArticleTableManager INSTANCE = new CollectArticleTableManager();
    }

    public static CollectArticleTableManager getInstance() {
        return WrapperInstance.INSTANCE;
    }

    public Observable<Boolean> insertCollectArticle(Article article) {
        String accountName = AccountManager.getInstance().getCurrentAccount().getUsername();
        return insertCollectArticle(article.getLink(), article.getTitle(), article.getAuthor(), article.getPublishTime(), accountName);
    }

    private Observable<Boolean> insertCollectArticle(String link, String title, String author, long time, String accountName) {
        return Observable.create(emitter -> {
            CollectArticleDB collectArticleDB = new CollectArticleDB(link, title, time, author, accountName);
            CollectArticleTableManager.collectArticleBox.put(collectArticleDB);
            emitter.onNext(true);
            emitter.onComplete();
        });
    }

    public Observable<Boolean> deleteCollectArticle(String title, String author) {
        return Observable.create(emitter -> {
            Query query = CollectArticleTableManager.collectArticleBox.query().equal(CollectArticleDB_.title, title).equal(CollectArticleDB_.author, author).build();
            query.remove();
            emitter.onNext(true);
            emitter.onComplete();
        });
    }

    public Observable<Boolean> isExists(String title, String author) {
        return Observable.create((emitter -> {
            Query query = CollectArticleTableManager.collectArticleBox.query().equal(CollectArticleDB_.title, title).equal(CollectArticleDB_.author, author).build();
            List<CollectArticleDB> collectArticleDBList = query.find();
            if (collectArticleDBList == null || collectArticleDBList.size() == 0) {
                emitter.onNext(false);
            } else {
                emitter.onNext(true);
            }
            emitter.onComplete();
        }));
    }

    public Observable<List<CollectArticleDB>> getAllCollectArticle() {
        return Observable.create((emitter -> {
            String accountName = AccountManager.getInstance().getCurrentAccount().getUsername();
            Query query = CollectArticleTableManager.collectArticleBox.query().equal(CollectArticleDB_.accountName, accountName).build();
            List<CollectArticleDB> data = query.find();
            emitter.onNext(data);
            emitter.onComplete();
        }));
    }

    public Observable<Boolean> updateCollectArticleDB(List<CollectArticleDB> collectArticleDBS) {
        return Observable.create((emitter -> {
            if (System.currentTimeMillis() - SharedPreferenceUtil.LoadLongData(CACHE_FILE_NAME, CACHE_COLLECT_ARTICLE_KEY) >= dayTimeMillis) {
                collectArticleBox.removeAll();
                collectArticleBox.put(collectArticleDBS);
                SharedPreferenceUtil.putData(CACHE_FILE_NAME, CACHE_COLLECT_ARTICLE_KEY, System.currentTimeMillis());
            }
            emitter.onNext(true);
            emitter.onComplete();
        }));
    }

}
