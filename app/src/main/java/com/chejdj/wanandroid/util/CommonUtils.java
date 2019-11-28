package com.chejdj.wanandroid.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.chejdj.wanandroid.WanAndroidApplication;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by jxy on 2018/6/21.
 */

public class CommonUtils {

    /**
     * 隐藏键盘
     */
    public static void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) WanAndroidApplication.getMyApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 获取随机rgb颜色值
     */
    public static int randomColor() {
        Random random = new Random();
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int red = random.nextInt(150);
        //0-190
        int green = random.nextInt(150);
        //0-190
        int blue = random.nextInt(150);
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red, green, blue);
    }

    /**
     * 计算当前观看人数
     */
    public static String getOnlineNum(int num) {
        String online = "";
        if (num > 10000) {
            StringBuilder sb = new StringBuilder();
            sb.append(num / 10000);
            sb.append(".");
            if (num % 10000 == 0) {
                sb.append("0");
            } else {
                sb.append(num % 10000 / 1000);
            }
            sb.append("万");
            online = String.valueOf(sb);
        } else if (num > 0 && num < 10000) {
            online = String.valueOf(num);
        }
        return online;
    }

    /**
     * 获得当前屏幕亮度值 0--255
     */
    public static int getScreenBrightness(Activity activity) {
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenBrightness;
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    public static void hideBottomUIMenu(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 显示虚拟按键
     */
    public static void showBottomUIMenu(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    /**
     * 控制虚拟按键的显示或隐藏
     */
    public static void showNavigationBar(Activity activity, boolean show) {
        try {
            if (show) {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Navigation menu item 高度
     */
    private void resetItemLayout(NavigationView navigationView) {
        //通过反射拿到menu的item布局。修改布局参数
        try {
            Field mPresenter = NavigationView.class.getDeclaredField("mPresenter");
            mPresenter.setAccessible(true);
            //此处mPresenter.get(navigationView)会得到一个NavigationMenuPresenter对象，但是该类是@hide的。所以此处直接再拿其内部的NavigationMenuView。该类也是@hide的。需要注意的是，该类继承自RecyclerView。菜单的布局也就是由其完成的。
            Field mMenuView = mPresenter.get(navigationView).getClass().getDeclaredField("mMenuView");
            mMenuView.setAccessible(true);
            //由于NavigationMenuView是隐藏类。此处用其父类。
            RecyclerView recycler = (RecyclerView) mMenuView.get(mPresenter.get(navigationView));
            for (int i = 0; i < recycler.getAdapter().getItemCount(); i++) {
                RecyclerView.ViewHolder holder = recycler.findViewHolderForLayoutPosition(i);
                //这里看实际项目了。我的项目中添加了一个head。
//                if (i == 0 || holder == null)//因为这里有一个header。所以要先排除第一个
//                    continue;
                if (holder == null)
                    return;
                //剩下的就是修改整体布局参数了。
                ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = 10;
                holder.itemView.setLayoutParams(params);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 随机图
     */
    public static String getRandomImage() {
        int index = new Random().nextInt(22);
        String imageUrl = "http://ojyz0c8un.bkt.clouddn.com/home_six_";
        return imageUrl + index + ".png";
    }

    /**
     * 设置文件下载路径
     */
    public static String getDownloadPath(String path) {
        String rootPath = Environment.getExternalStorageDirectory() + "/WanAndroid";
        if (path == null) {
            return rootPath;
        }
        try {
            // 指定路径如果没有则创建并添加
            File file = new File(rootPath + path);
            //判断是否存在
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootPath + path;
    }


}
