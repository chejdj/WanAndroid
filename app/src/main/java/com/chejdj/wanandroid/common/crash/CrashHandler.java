package com.chejdj.wanandroid.common.crash;

import android.content.Context;
import android.util.Log;

/**
 * @author by zhuyangyang
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context context;

    private CrashHandler() {}

    static class InstanceWrapper {
        private static CrashHandler INSTANCE = new CrashHandler();
    }

    public static CrashHandler getInstance() {
        return InstanceWrapper.INSTANCE;
    }

    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.d("ERROR", t.getName() + e.getLocalizedMessage());
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
