package com.chejdj.wanandroid.common.wxshare;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.chejdj.wanandroid.R;
import com.chejdj.wanandroid.WanAndroidApplication;
import com.chejdj.wanandroid.util.StringUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WxShareUtils {
    private static final String APP_ID = "wxdf25b090687b0c33"; //填入自己的APPID
    private IWXAPI api;

    private WxShareUtils() {
        api = WXAPIFactory.createWXAPI(WanAndroidApplication.getMyApplication(), APP_ID, true);
        api.registerApp(APP_ID);
    }

    static class Wrapper {
        static WxShareUtils INSTANCE = new WxShareUtils();
    }

    public static WxShareUtils getInstance() {
        return Wrapper.INSTANCE;
    }

    /**
     * @param link        网页链接
     * @param title       网页title
     * @param description 网页描述
     * @param type        分享类型，0代表会话，1代表朋友圈
     */
    public void shareToWeChat(Context context, String link, String title, String description, int type) {

        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = link;
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = title;
        msg.description = description;
        msg.setThumbImage(BitmapFactory.decodeResource(WanAndroidApplication.getMyApplication().getResources(), R.drawable.wanandroid));

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.scene = type == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        boolean state = api.sendReq(req);
        if (!state) {
            Toast.makeText(context, StringUtils.getString(context, R.string.wx_share_error), Toast.LENGTH_SHORT).show();
        }
    }
}
