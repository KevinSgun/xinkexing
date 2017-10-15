package com.thinkeract.tka;

import com.thinkeract.tka.data.api.ApiConstants;

/**
 * Created by lu on 2016/6/16.
 */
public class Constants {

    //验证码长度
    public static final int MIN_VALIDATE_CODE_LENGTH = 6;

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "travel.db";

    public static final String XMPP_SERVER_NAME = "travel";
    public static final int XMPP_PORT = 5222;
    public static final String DEFAULT_PASSWORD = "88888888";

    /***
     * 官方小秘书ID
     */
    public static final String OFFICIAL_ID = "100000";

    public static final int KEPP_ALIVE_INTERVAL = 30 * 60 * 1000;

    // 此为线上用的地址
    public static String RELEASED_URL = "http://120.24.95.8:8086/dateExp/ex.do";
    public static String RELEASED_UPLOAD_URL = "http://120.24.95.8:8086/dateExp/fileUpload.do";
    public static String RELEASED_XMPP_HOST = "120.24.82.102";

    // 此为测试用的地址
    public static String TEST_URL = "http://120.24.209.64:8080/dateExp/ex.do";
    public static String TEST_UPLOAD_URL = "http://120.24.209.64:8080/dateExp/fileUpload.do";
    public static String TEST_XMPP_HOST = "120.24.82.102";

    // 局域网测试
    public static String TEST_URL_LAN = "http://172.17.27.1:8080/dateExp/ex.do";
    public static String TEST_UPLOAD_URL_LAN = "http://172.17.27.1:8080/dateExp/fileUpload.do";// "http://192.168.0.128:8080/baby/ups.do";
    public static String TEST_XMPP_HOST_LAN = "172.17.27.1";


    public static final String URL = BuildConfig.DEBUG ? TEST_URL : RELEASED_URL;
    public static final String UPLOAD_URL = BuildConfig.DEBUG ? TEST_UPLOAD_URL : RELEASED_UPLOAD_URL;
    public static final String XMPP_HOST = BuildConfig.DEBUG ? TEST_XMPP_HOST : RELEASED_XMPP_HOST;

    public static final String WX_APP_ID = "wxd35c4398e0feb8cc";
    public static final String WX_APP_KEY = "1357c8687432ff5965b1070392fff8e7";

    public static final String QQ_APP_ID = "1105777029";
    public static final String QQ_APP_KEY = "w7KvF37Vkc3yB51z";

    public static final String WEIBO_APP_ID = "294032740";
    public static final String WEIBO_APP_KEY = "cd5dce34b5fd63489915cff90c21623b";


    //上传类型
    public static final int UPLOAD_TYPE_AVATAR = 1;
    public static final int UPLOAD_OTHER = 2;
    public static final int UPLOAD_TYPE_VIDEO = 3;
    public static final int UPLOAD_TYPE_ALBUM = 4;
    public static final int UPLOAD_TYPE_CHAT_PIC = 5;//5.上传聊天照片
    public static final int UPLOAD_TYPE_CHAT_VOICE = 6;//6.上传聊天语音

    public static final String SALT = "godo@)#($*%&^~!";

    public static final String HELP_AND_FEEDBACK = ApiConstants.getHtmlRootUrl()+"index.html#/helpAndFeedback";
    public static final String ABOUT_US = ApiConstants.getHtmlRootUrl()+"index.html#/about";
    public static final String NET_DIAGNOSIS = ApiConstants.getHtmlRootUrl()+"index.html#/checkNet";
    public static final String SERVICE_PROTOCOL = ApiConstants.getHtmlRootUrl()+"fwxy.html";
    public static final String USER_AGREEMENT_PROTOCOL = ApiConstants.getHtmlRootUrl()+"fwxy.html";
    public static final String INVITATION_URL = ApiConstants.getRootUrl()+"mb/invite/";
    public static final String SHARE_LIVE_URL = ApiConstants.getRootUrl() + "mb/live/";

    public interface ActivityExtra {
        String BUSI_ID = "busi_id";
        String PHOTOS = "photos";
        String LAUNCH_HOME = "launch_home";
        String IS_FROM_LOGIN = "is_from_login";
        String DATA_CONTENT = "data_content";
        String DATA_EDIT_VO = "data_edit_vo";
        String NEWS_ID = "news_id";
        String GOODS_ID = "goods_id";
        String ADDRESS_ITEM = "address_item";
        String PO = "po";
        String TITLE = "title";
    }

    public interface FragmentExtra {


    }

    public interface ImageDefResId{
        int DEF_AVA_NORMAL = R.mipmap.ic_avatar_def;
        int DEF_SQUARE_PIC_NORMAL = R.mipmap.ic_loading_square;
//        int DEF_BANNER_PIC_NORMAL = R.mipmap.ic_loading_home_banner;
        int DEF_LAND_PIC_NORMAL = R.mipmap.ic_loading_square;
    }
}
