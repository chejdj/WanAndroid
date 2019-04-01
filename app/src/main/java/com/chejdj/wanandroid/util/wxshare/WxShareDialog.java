package com.chejdj.wanandroid.util.wxshare;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.network.bean.article.Article;

public class WxShareDialog {
    public static void showWxShareDialog(Context context, View parent, Article article) {
        PopupWindow popupWindow = new PopupWindow(LayoutInflater.from(context).inflate(R.layout.wechat_share, null), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        View.OnClickListener listener = (View v) -> {
            switch (v.getId()) {
                case R.id.share_friends:
                    WxShareUtil.getInstance().shareToWeChat(context, article.getLink(), article.getTitle(), article.getDesc(), 0);
                    break;
                case R.id.share_timeline:
                    WxShareUtil.getInstance().shareToWeChat(context, article.getLink(), article.getTitle(), article.getDesc(), 1);
                    break;
                default:
                    Log.e("WxShareDialg", "no this view" + v.getId());
            }
            popupWindow.dismiss();
        };


        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(context.getDrawable(R.drawable.wx_share_bg));

        popupWindow.getContentView().findViewById(R.id.share_friends).setOnClickListener(listener);
        popupWindow.getContentView().findViewById(R.id.share_timeline).setOnClickListener(listener);

        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }
}
