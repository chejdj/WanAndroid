package com.chejdj.wanandroid.util.glide;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

@GlideModule
public class ExploreGlideModule extends AppGlideModule {
    private final int diskSize = 1024 * 1024 * 50; //50M
    private final int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8; //最大内存的10分之一
    private final String fileName = Environment.getExternalStorageDirectory() + "/wanandroid/image/";

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setDiskCache(new DiskLruCacheFactory(fileName, diskSize))
                .setMemoryCache(new LruResourceCache(memorySize))
                .setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_RGB_565));
    }
}
