package com.chejdj.wanandroid.common.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

//全局唯一的用于加载图片的Loader
public class ExploreImageLoader {
    private ExploreImageLoader() {

    }

     static class WrapperInstance {
        static ExploreImageLoader INSTANCE = new ExploreImageLoader();
    }

    public static ExploreImageLoader getInstance() {
        return WrapperInstance.INSTANCE;
    }

    public void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

}
