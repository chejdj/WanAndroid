package com.chejdj.wanandroid.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class NetUtils {
    /**
     * 没有连接网络
     */
    private static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    private static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    private static final int NETWORK_WIFI = 1;

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static int getNetWorkState() {
        if (mContext == null) {
            throw new UnsupportedOperationException("please use NetUtils before init it");
        }
        // 得到连接管理器对象
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取所有网络连接的信息
        Network[] networks = connMgr.getAllNetworks();
        //通过循环将网络信息逐个取出来
        for (int i = 0; i < networks.length; i++) {
            //获取ConnectivityManager对象对应的NetworkInfo对象
            NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
            if (networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return NETWORK_MOBILE;
                } else {
                    return NETWORK_WIFI;
                }
            }
        }
        return NETWORK_NONE;
    }
}
