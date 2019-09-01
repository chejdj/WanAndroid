package com.chejdj.wanandroid;

import android.app.Application;

import com.chejdj.wanandroid.db.ObjectBox;
import com.chejdj.wanandroid.db.account.AccountManager;
import com.chejdj.wanandroid.ui.webviewarticle.sonic.SonicRuntimeImpl;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;

public class WanAndroidApplication extends Application {
    private static WanAndroidApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(this);
        mApplication = this;
        ObjectBox.init(this);
        AccountManager.getInstance().init();
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
        initSonic();
    }

    private void initSonic() {
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(this), new SonicConfig.Builder().build());
        }
    }

    public static WanAndroidApplication getMyApplication() {
        return mApplication;
    }
}
