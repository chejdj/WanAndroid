package com.chejdj.wanandroid;

import android.app.Application;

import com.chejdj.wanandroid.db.ObjectBox;
import com.chejdj.wanandroid.db.account.AccountManager;
import com.chejdj.wanandroid.util.NetUtils;
import com.tencent.bugly.crashreport.CrashReport;

public class WanAndroidApplication extends Application {
    private static WanAndroidApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(this);
        mApplication = this;
        ObjectBox.init(this);
        NetUtils.init(this);
        AccountManager.getInstance().init();
    }

    public static WanAndroidApplication getMyApplication() {
        return mApplication;
    }
}
