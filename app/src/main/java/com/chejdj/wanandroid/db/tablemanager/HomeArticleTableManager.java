package com.chejdj.wanandroid.db.tablemanager;

import com.chejdj.wanandroid.db.ObjectBox;
import com.chejdj.wanandroid.db.entity.HomeArticleDB;
import com.chejdj.wanandroid.util.SharedPreferenceUtils;

import java.util.List;

import io.objectbox.Box;
import io.reactivex.Observable;


public class HomeArticleTableManager {
    private static Box<HomeArticleDB> homeArticleBox;
    private static final String CACHE_FILE_NAME = "cache_time";
    private static final String CACHE_HOME_ARTICLE_KEY = "home_article_cache_time";
    private static long dayTimeMillis = 24 * 60 * 60 * 1000;

    private HomeArticleTableManager() {
        homeArticleBox = ObjectBox.getBoxStore().boxFor(HomeArticleDB.class);
    }

    private static class WrapperInstance {
        static HomeArticleTableManager INSTANCE = new HomeArticleTableManager();
    }

    public static HomeArticleTableManager getInstance() {
        return WrapperInstance.INSTANCE;
    }

    public Observable<Boolean> updateHomeArticleDB(final List<HomeArticleDB> data) {
        return Observable.create(emitter -> {
            if (System.currentTimeMillis() - SharedPreferenceUtils.LoadLongData(CACHE_FILE_NAME, CACHE_HOME_ARTICLE_KEY) >= dayTimeMillis) {
                homeArticleBox.removeAll();
                homeArticleBox.put(data);
                SharedPreferenceUtils.putData(CACHE_FILE_NAME, CACHE_HOME_ARTICLE_KEY, System.currentTimeMillis());
            }
            emitter.onNext(true);
            emitter.onComplete();
        });
    }

    public Observable<List<HomeArticleDB>> queryHomeArticleAll() {
        return Observable.create(emitter -> {
            List<HomeArticleDB> data = homeArticleBox.getAll();
            emitter.onNext(data);
            emitter.onComplete();
        });
    }
}
