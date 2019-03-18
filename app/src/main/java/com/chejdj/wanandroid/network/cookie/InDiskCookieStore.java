package com.chejdj.wanandroid.network.cookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.Cookie;

public class InDiskCookieStore {
    private static final String LOG_TAG = "InDiskCookieStore";
    private static final String COOKIE_PREFS = "CookiePrefsFile";
    private static final String COOKIE_NAME_PREFIX = "cookie_";

    //在内存中，缓存cookie，加快访问速度
    private HashMap<String, ConcurrentHashMap<String, Cookie>> cookies;
    //存储cookie到本地
    private final SharedPreferences cookiePrefs;

    private ReentrantLock mLock;

    public InDiskCookieStore(Context context) {
        cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, 0);
        mLock = new ReentrantLock(false);
        cookies = new HashMap<>();
        initMemoryCookie();
    }

    private void initMemoryCookie() {
        // 加载本地cookie到内存中
        Map<String, ?> prefsMap = cookiePrefs.getAll();
        for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {
            if ((entry.getValue()) != null && !((String) entry.getValue()).startsWith(COOKIE_NAME_PREFIX)) {
                String[] cookieNames = TextUtils.split((String) entry.getValue(), ",");
                for (String name : cookieNames) {
                    String encodedCookie = cookiePrefs.getString(COOKIE_NAME_PREFIX + name, null);
                    if (encodedCookie != null) {
                        Cookie decodedCookie = decodeCookie(encodedCookie);
                        if (decodedCookie != null) {
                            if (!cookies.containsKey(entry.getKey())) {
                                cookies.put(entry.getKey(), new ConcurrentHashMap<>());
                            }
                            cookies.get(entry.getKey()).put(name, decodedCookie);
                        }
                    }
                }
            }
        }
    }

    public void add(URI uri, Cookie cookie) {
        if (cookie.persistent() && cookiesIsExpired(uri, cookie)) {
            mLock.lock();
            try {
                String name = getCookieToken(cookie);
                if (!cookies.containsKey(uri.getHost())) {
                    cookies.put(uri.getHost(), new ConcurrentHashMap<>());
                }
                cookies.get(uri.getHost()).put(name, cookie);
                SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
                // 加入了uri 对应的 cookies名字
                prefsWriter.putString(uri.getHost(), TextUtils.join(",", cookies.get(uri.getHost()).keySet()));
                //加入对应的cookies
                prefsWriter.putString(COOKIE_NAME_PREFIX + name, encodeCookie(new DiskHttpCookie(cookie)));
                prefsWriter.commit();
            } finally {
                mLock.unlock();
            }
        }
    }

    private boolean cookiesIsExpired(URI uri, Cookie cookie) {
        String name = getCookieToken(cookie);
        if (cookies.containsKey(uri.getHost()) && cookies.get(uri.getHost()).contains(name)) {
            Cookie lastCookie = cookies.get(uri.getHost()).get(name);
            long currentTime = System.currentTimeMillis();
            return lastCookie.expiresAt() > currentTime ? true : false;
        }
        return true;
    }

    private String getCookieToken(Cookie cookie) {
        return cookie.name() + cookie.domain();
    }

    //这里仅仅是返回内存中的变量，应该添加返回磁盘当中的
    public List<Cookie> get(URI uri) {
        mLock.lock();
        ArrayList<Cookie> ret = new ArrayList<>();
        if (cookies.containsKey(uri.getHost()))
            ret.addAll(cookies.get(uri.getHost()).values());
        mLock.unlock();
        return ret;
    }

    //序列化DiskHttpCookie，并将其转为字符串
    private String encodeCookie(DiskHttpCookie cookie) {
        if (cookie == null)
            return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(cookie);
        } catch (IOException e) {
            Log.d(LOG_TAG, "IOException in encodeCookie", e);
            return null;
        }

        return byteArrayToHexString(os.toByteArray());
    }

    //通过cookie字符串反序列化为HttpCookie对象
    private Cookie decodeCookie(String cookieString) {
        byte[] bytes = hexStringToByteArray(cookieString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((DiskHttpCookie) objectInputStream.readObject()).getHttpCookie();
        } catch (IOException e) {
            Log.d(LOG_TAG, "IOException in decodeCookie", e);
        } catch (ClassNotFoundException e) {
            Log.d(LOG_TAG, "ClassNotFoundException in decodeCookie", e);
        }

        return cookie;
    }


    //字节转16进制
    private String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    //16进制转字节
    private byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}
