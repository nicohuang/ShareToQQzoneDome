package hwz.com.sharetoqqzonedome.share;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.connect.share.QzoneShare;

import java.util.ArrayList;

import hwz.com.sharetoqqzonedome.MainActivity;


/**
 * Created by jan on 15/7/13.
 */
public class DoShareHelp
{

    /**qq空间分享
     * 设置分享内容
     * 参数说明
     * QzoneShare.SHARE_TO_QQ_KEY_TYPE	选填	Int	SHARE_TO_QZONE_TYPE_IMAGE_TEXT（图文）
     * QzoneShare.SHARE_TO_QQ_TITLE	必填	Int	分享的标题，最多200个字符。
     * QzoneShare.SHARE_TO_QQ_SUMMARY	选填	String	分享的摘要，最多600字符。
     * QzoneShare.SHARE_TO_QQ_TARGET_URL	必填	String	需要跳转的链接，URL字符串。
     * QzoneShare.SHARE_TO_QQ_IMAGE_URL	选填	String	分享的图片, 以ArrayList<String>的类型传入，以便支持多张图片（注：图片最多支持9张图片，多余的图片会被丢弃）。
     */
    public static void shareToQQzone(Activity activity,String title, String message,String path)
    {
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, message);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://www.hicsg.com");
        //图片集合
        ArrayList imageUrls = new ArrayList();
        imageUrls.add(path);
        imageUrls.add("http://media-cdn.tripadvisor.com/media/photo-s/01/3e/05/40/the-sandbar-that-links.jpg");

        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
//        params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        MainActivity.mTencent.shareToQzone(activity, params, new BaseUiListener());
    }
}
