package com.chejdj.wanandroid.util;

import android.app.Activity;
import android.os.Handler;

import java.lang.ref.WeakReference;

public  abstract class CommonHandler extends Handler {
    private final WeakReference<Activity> mActivity;
    public CommonHandler(Activity activty) {
        mActivity=new WeakReference<>(activty);
    }
}
