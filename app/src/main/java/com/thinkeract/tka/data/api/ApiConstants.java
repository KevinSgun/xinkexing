package com.thinkeract.tka.data.api;


import com.zitech.framework.BuildConfig;

/**
 * Created by minHeng on 2016/6/30 0030 15:17.
 * mail:minhengyan@gmail.com
 */
public class ApiConstants {

    static final String UPLOAD ="" ;//上传文件统一接口

    static final String GET_VALIDATION_CODE = "xk001";//获取验证码
    static final String LOGIN = "xk002";//登录
    static final String BINDING_PHONE = "xk003";//绑定手机
    static final String MODIFY_USER_DATA = "xk004";//修改用户资料
    static final String USER_ADDRESS_LIST = "xk005";//用户收货地址列表
    static final String ADD_ADDRESS = "xk006";//新增用户收货地址
    static final String DELETE_ADDRESS = "xk007";//用户收货地址删除接口
    static final String MODIFY_ADDRESS = "xk008";//用户收货地址修改接口
    static final String DOCTOR_DATA_REVIEW = "xk009";//医生审核资料
    static final String NEWS_RECOMMEND = "xk010";//推荐新闻列表
    static final String NEWS_DETAIL = "xk011";//新闻详情
    static final String ALL_NEWS = "xk012";//所有新闻列表
    static final String HOME_PAGE_DATA = "xk013";//首页接口
    static final String SECOND_REPORT = "xk014";//二级检测页面
    static final String ALL_GOODS = "xk015";//所有商品列表
    static final String GOODS_CLASSIFY = "xk016";//商品检索分类列表
    static final String GOODS_DETAIL = "xk017";//商品详情
    static final String ALL_GOODS_COMMENT = "xk018";//所有商品评论列表
    static final String SUBMIT_ORDER = "xk019";//提交订单
    static final String PAY_FOR_ORDER = "xk020";//支付订单
    static final String MY_ORDER_LIST = "xk021";//我的订单列表
    static final String ORDER_DETAIL = "xk022";//订单详情
    static final String COMMENT_ORDER = "xk023";//评论订单
    static final String ORDER_LOGISTICS = "xk024";//查看订单物流信息
    static final String LOOK_AUTH_DATA = "xk025";//查看审核信息
    static final String MY_LIVE_LIST = "xk026";//我的直播列表
    static final String CHECK_RESULT = "xk028";//检测项目明细

    /*
         * 线上环境
         */
    //API接口地址
//    public static final String RELEASE_URL = "http://maixiang.godochina.com:8080/";
    public static String RELEASE_URL = "http://api.thinkeract.com:9090/";
//    public static String RELEASE_URL = "http://192.168.2.121:8080/";

    /*
     * daily环境
	 */
    //API接口地址
//    public static final String DAILY_URL = "http://maixiang.godochina.com:8080/";
    public static final String DAILY_URL = "http://api.thinkeract.com:9090/";
//    public static final String DAILY_URL = "http://192.168.2.121:8080/";
    public static final String BAIDU_MAP_API_URL= "http://api.map.baidu.com/";
    public static final String  GEO_CONVERT="geoconv/v1/";
    public static final String  GEOCODER="geocoder/v2/";
        //?ak=E4805d16520de693a3fe707cdc962045&callback=renderReverse&location=39.983424,116.322987&output=json&pois=1
    public static final String COMMON_REQUEST = "api/ex.do";
    public static final String UPLOAD_REQUEST = "api/upload";

    public static String getRootUrl() {

        return BuildConfig.DEBUG ? DAILY_URL : RELEASE_URL;

    }

    public static String getHtmlRootUrl() {

//        return "http://ok0cfmsp8.bkt.clouddn.com/";
        return BuildConfig.DEBUG ? DAILY_URL+"app/" : RELEASE_URL+"app/";

    }


}
