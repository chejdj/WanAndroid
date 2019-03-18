package com.chejdj.wanandroid.network.cookie;

import java.io.Serializable;

import okhttp3.Cookie;


public class DiskHttpCookie implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    private String name;
    private String value;
    private long expiresAt;
    private String domain;
    private String path;
    private boolean secure;
    private boolean httpOnly;

    private boolean persistent; // True if 'expires' or 'max-age' is present.
    private boolean hostOnly;


    public DiskHttpCookie(Cookie mCookie) {
        this.name = mCookie.name();
        this.value = mCookie.value();
        this.expiresAt = mCookie.expiresAt();
        this.domain = mCookie.domain();
        this.path = mCookie.path();
        this.secure = mCookie.secure();
        this.httpOnly = mCookie.httpOnly();
        this.persistent = mCookie.persistent();
        this.hostOnly = mCookie.hostOnly();
    }

    public Cookie getHttpCookie() {
        Cookie.Builder builder = new Cookie.Builder()
                .name(name)
                .value(value)
                .expiresAt(expiresAt)
                .path(path);
        if (secure) {
            builder.secure();
        }
        if (httpOnly) {
            builder.httpOnly();
        }
        if (hostOnly) {
            builder.hostOnlyDomain(domain);
        } else {
            builder.domain(domain);
        }
        return builder.build();
    }
}
