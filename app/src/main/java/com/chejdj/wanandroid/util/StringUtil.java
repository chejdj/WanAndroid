package com.chejdj.wanandroid.util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class StringUtil {
    public static boolean isEmpty(String data) {
        return data == null || data.length() == 0;
    }

    public static String timeToString(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.CHINA);
        return simpleDateFormat.format(time);
    }

    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

}
