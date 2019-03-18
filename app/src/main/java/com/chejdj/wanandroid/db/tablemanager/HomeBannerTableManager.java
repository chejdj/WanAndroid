package com.chejdj.wanandroid.db.tablemanager;

import com.chejdj.wanandroid.db.ObjectBox;
import com.chejdj.wanandroid.db.entity.HomeBannerDB;
import com.chejdj.wanandroid.util.SharedPreferenceUtil;

import java.util.List;

import io.objectbox.Box;
import io.reactivex.Observable;

public class HomeBannerTableManager {
    private static Box<HomeBannerDB> homeBannerDBBox;
    private static final String CACHE_FILE_NAME = "cache_time";
    private static final String CACHE_HOME_BANNER_KEY = "home_banner_cache_time";
    private static final long dayTimeMillis = 24 * 60 * 60 * 1000;

    private HomeBannerTableManager() {
        homeBannerDBBox = ObjectBox.getBoxStore().boxFor(HomeBannerDB.class);
    }

    private static class WrapperInstance {
        static HomeBannerTableManager INSTANCE = new HomeBannerTableManager();
    }

    public static HomeBannerTableManager getInstance() {
        return WrapperInstance.INSTANCE;
    }

    public Observable<Boolean> updateHomeArticleDB(final List<HomeBannerDB> data) {
        return Observable.create(emitter -> {
            if (System.currentTimeMillis() - SharedPreferenceUtil.LoadLongData(CACHE_FILE_NAME, CACHE_HOME_BANNER_KEY) >= dayTimeMillis) {
                homeBannerDBBox.removeAll();
                homeBannerDBBox.put(data);
                SharedPreferenceUtil.putData(CACHE_FILE_NAME, CACHE_HOME_BANNER_KEY, System.currentTimeMillis());
            }
            emitter.onNext(true);
            emitter.onComplete();
        });
    }

    public Observable<List<HomeBannerDB>> queryHomeBannerAll() {
        return Observable.create(emitter -> {
            List<HomeBannerDB> data = homeBannerDBBox.getAll();
            emitter.onNext(data);
            emitter.onComplete();
        });
    }
}
