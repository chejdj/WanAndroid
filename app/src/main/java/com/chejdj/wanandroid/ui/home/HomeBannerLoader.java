package com.chejdj.wanandroid.ui.home;

import android.content.Context;
import android.widget.ImageView;

import com.chejdj.wanandroid.network.bean.homepage.HomeBanner;
import com.chejdj.wanandroid.common.glide.ExploreImageLoader;
import com.youth.banner.loader.ImageLoader;


public class HomeBannerLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ExploreImageLoader.getInstance().loadImage(context, ((HomeBanner) path).getImagePath(), imageView);
    }
}
