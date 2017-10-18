package com.thinkeract.tka.data.api;


import com.thinkeract.tka.Constants;
import com.thinkeract.tka.User;
import com.thinkeract.tka.data.api.entity.AddressItem;
import com.thinkeract.tka.data.api.entity.GoodsComment;
import com.thinkeract.tka.data.api.entity.GoodsItem;
import com.thinkeract.tka.data.api.entity.NewsItem;
import com.thinkeract.tka.data.api.entity.OrderDetailData;
import com.thinkeract.tka.data.api.entity.OrderItem;
import com.thinkeract.tka.data.api.entity.SecondReportItem;
import com.thinkeract.tka.data.api.request.CommentOrderBody;
import com.thinkeract.tka.data.api.request.DoctorDataReviewBody;
import com.thinkeract.tka.data.api.request.GIdListBody;
import com.thinkeract.tka.data.api.request.IdRequest;
import com.thinkeract.tka.data.api.request.ListBody;
import com.thinkeract.tka.data.api.request.LoginBody;
import com.thinkeract.tka.data.api.request.PoBody;
import com.thinkeract.tka.data.api.request.Request;
import com.thinkeract.tka.data.api.request.RequestHeader;
import com.thinkeract.tka.data.api.request.StatusListBody;
import com.thinkeract.tka.data.api.request.SubmitOrderBody;
import com.thinkeract.tka.data.api.request.UpdateAddressBody;
import com.thinkeract.tka.data.api.request.UpdateUserDataBody;
import com.thinkeract.tka.data.api.request.ValidationCodeBody;
import com.thinkeract.tka.data.api.response.CheckResultData;
import com.thinkeract.tka.data.api.response.GoodsDetailData;
import com.thinkeract.tka.data.api.response.HomePageData;
import com.thinkeract.tka.data.api.response.ListData;
import com.thinkeract.tka.data.api.response.LogisticsData;
import com.thinkeract.tka.data.api.response.NewsDetailData;
import com.thinkeract.tka.data.api.response.PayResultData;
import com.thinkeract.tka.data.api.response.PoData;
import com.thinkeract.tka.data.api.response.UserData;
import com.zitech.framework.data.network.HttpResultFunc;
import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.SchedulersCompat;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Api协议工厂，具体方法代码通过{@link }来生成
 */
public class ApiFactory {

    private static Map<String, Object> mCache = new HashMap();


    public static AccountService getApiService() {
        return getService(ApiConstants.getRootUrl(), AccountService.class);
    }

    public static HomePageDataService getHomePageDataService() {
        return getService(ApiConstants.getRootUrl(), HomePageDataService.class);
    }

    public static MineService getMineService() {
        return getService(ApiConstants.getRootUrl(), MineService.class);
    }

    public static MallService getMallService() {
        return getService(ApiConstants.getRootUrl(), MallService.class);
    }

    public static <T> T getService(String baseUrl, Class<T> cls) {
//        String key = cls.getSimpleName();
        Object target = mCache.get(baseUrl);
        if (target == null || !cls.isAssignableFrom(target.getClass())) {
            target = RetrofitClient.getInstance().create(baseUrl, cls);
            mCache.put(baseUrl, target);
        }
        return (T) target;
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public static Observable<ApiResponse> getValidationCode(ValidationCodeBody body) {
        Request<ValidationCodeBody> request = new Request<ValidationCodeBody>(RequestHeader.create(ApiConstants.GET_VALIDATION_CODE), body);
        return getApiService().getValidationCode(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse>applyExecutorSchedulers());
    }

    /**
     * 用户登录
     *
     * @return
     * @see UserData
     */
    public static Observable<ApiResponse<UserData>> login(String phoneNumber, String validateCode) {
        LoginBody loginBody = new LoginBody();
        loginBody.setCode(validateCode);
        loginBody.setMobile(phoneNumber);
        Request<LoginBody> request = new Request<LoginBody>(RequestHeader.create(ApiConstants.LOGIN), loginBody);
        return getApiService().login(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<UserData>>applyExecutorSchedulers());
    }

    /**
     * 绑定手机
     *
     * @return
     * @see UserData
     */
    public static Observable<ApiResponse> bindingPhone(String phoneNumber, String validateCode) {
        LoginBody loginBody = new LoginBody();
        loginBody.setCode(validateCode);
        loginBody.setMobile(phoneNumber);
        Request<LoginBody> request = new Request<LoginBody>(RequestHeader.create(ApiConstants.BINDING_PHONE), loginBody);
        return getApiService().bindingPhone(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse>applyExecutorSchedulers());
    }


    /**
     * 修改用户资料
     * @param body
     * @return
     */
    public static Observable<ApiResponse> updateUserData(UpdateUserDataBody body) {
        Request<UpdateUserDataBody> request = new Request<>(RequestHeader.create(ApiConstants.MODIFY_USER_DATA), body);
        return getApiService().updateUserData(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse>applyExecutorSchedulers());
    }

    /**
     * 用户收货地址列表
     * @return
     */
    public static Observable<ApiResponse<List<AddressItem>>> getUserAddressList() {
        Request request = new Request<>(RequestHeader.create(ApiConstants.USER_ADDRESS_LIST), null);
        return getApiService().getUserAddressList(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<ListData<AddressItem>>>applyExecutorSchedulers());
    }

    /**
     * 新增用户收货地址
     * @param body
     * @return
     */
    public static Observable<ApiResponse> addAddress(UpdateAddressBody body) {
        Request<UpdateAddressBody> request = new Request<>(RequestHeader.create(ApiConstants.ADD_ADDRESS), body);
        return getApiService().addAddress(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse>applyExecutorSchedulers());
    }

    /**
     * 用户收货地址删除接口
     * @param body
     * @return
     */
    public static Observable<ApiResponse> deleteAddress(IdRequest body) {
        Request<IdRequest> request = new Request<>(RequestHeader.create(ApiConstants.DELETE_ADDRESS), body);
        return getApiService().deleteAddress(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse>applyExecutorSchedulers());
    }

    /**
     * 用户收货地址修改接口
     * @param body
     * @return
     */
    public static Observable<ApiResponse> modifyAddress(UpdateAddressBody body) {
        Request<UpdateAddressBody> request = new Request<>(RequestHeader.create(ApiConstants.MODIFY_ADDRESS), body);
        return getApiService().modifyAddress(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse>applyExecutorSchedulers());
    }

    /**
     * 医生审核资料
     * @param body
     * @return
     */
    public static Observable<ApiResponse> doctorDataReview(DoctorDataReviewBody body) {
        Request<DoctorDataReviewBody> request = new Request<>(RequestHeader.create(ApiConstants.DOCTOR_DATA_REVIEW), body);
        return getApiService().doctorDataReview(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse>applyExecutorSchedulers());
    }

    /**
     * 推荐新闻列表
     * @return
     */
    public static Observable<ApiResponse<List<NewsItem>>> newsRecommendList() {
        Request request = new Request<>(RequestHeader.create(ApiConstants.NEWS_RECOMMEND), null);
        return getHomePageDataService().newsRecommendList(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<List<NewsItem>>>applyExecutorSchedulers());

    }

    /**
     * 新闻详情
     * @param body
     * @return
     */
    public static Observable<ApiResponse<NewsDetailData>> newsDetail(IdRequest body) {
        Request<IdRequest> request = new Request<>(RequestHeader.create(ApiConstants.NEWS_DETAIL), body);
        return getHomePageDataService().newsDetail(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<NewsDetailData>>applyExecutorSchedulers());
    }

    /**
     * 所有新闻列表
     * @param body
     * @return
     */
    public static Observable<ApiResponse<ListData<NewsItem>>> allNews(ListBody body) {
        Request<ListBody> request = new Request<>(RequestHeader.create(ApiConstants.ALL_NEWS), body);
        return getHomePageDataService().allNews(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<ListData<NewsItem>>>applyExecutorSchedulers());
    }

    /**
     * 首页接口
     * @return
     */
    public static Observable<ApiResponse<HomePageData>> homePageData() {
        Request request = new Request<>(RequestHeader.create(ApiConstants.HOME_PAGE_DATA), null);
        return getHomePageDataService().homePageData(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<HomePageData>>applyExecutorSchedulers());
    }

    /**
     * 二级检测页面接口
     * @param body
     * @return
     */
    public static Observable<ApiResponse<List<SecondReportItem>>> secondReport(IdRequest body) {
        Request<IdRequest> request = new Request<>(RequestHeader.create(ApiConstants.SECOND_REPORT), body);
        return getHomePageDataService().secondReport(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<List<SecondReportItem>>>applyExecutorSchedulers());
    }

    /**
     * 所有商品列表
     * @param body
     * @return
     */
    public static Observable<ApiResponse<ListData<GoodsItem>>> allGoods(ListBody body) {
        Request<ListBody> request = new Request<>(RequestHeader.create(ApiConstants.ALL_GOODS), body);
        return getMallService().allGoods(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<ListData<GoodsItem>>>applyExecutorSchedulers());
    }

    /**
     * 商品检索分类列表
     * @return
     */
    public static Observable<ApiResponse<String>> goodsClassify() {
        Request request = new Request<>(RequestHeader.create(ApiConstants.GOODS_CLASSIFY), null);
        return getMallService().goodsClassify(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<String>>applyExecutorSchedulers());
    }

    /**
     * 商品详情
     * @param body
     * @return
     */
    public static Observable<ApiResponse<GoodsDetailData>> goodsDetail(IdRequest body) {
        Request<IdRequest> request = new Request<>(RequestHeader.create(ApiConstants.GOODS_DETAIL), body);
        return getMallService().goodsDetail(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<GoodsDetailData>>applyExecutorSchedulers());
    }

    /**
     * 商品评论列表
     * @param body
     * @return
     */
    public static Observable<ApiResponse<ListData<GoodsComment>>> goodsCommentList(GIdListBody body) {
        Request<GIdListBody> request = new Request<>(RequestHeader.create(ApiConstants.ALL_GOODS_COMMENT), body);
        return getMallService().goodsCommentList(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<ListData<GoodsComment>>>applyExecutorSchedulers());
    }

    /**
     * 提交订单
     * @param body
     * @return
     */
    public static Observable<ApiResponse<PoData>> submit(SubmitOrderBody body) {
        Request<SubmitOrderBody> request = new Request<>(RequestHeader.create(ApiConstants.SUBMIT_ORDER), body);
        return getMallService().submit(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<PoData>>applyExecutorSchedulers());
    }

    /**
     * 订单支付
     * @param body
     * @return
     */
    public static Observable<ApiResponse<PayResultData>> payForOrder(PoBody body) {
        Request<PoBody> request = new Request<>(RequestHeader.create(ApiConstants.PAY_FOR_ORDER), body);
        return getMallService().payForOrder(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<PayResultData>>applyExecutorSchedulers());
    }

    /**
     * 我的订单列表
     * @param body
     * @return
     */
    public static Observable<ApiResponse<ListData<OrderItem>>> myOrderList(StatusListBody body) {
        Request<StatusListBody> request = new Request<>(RequestHeader.create(ApiConstants.MY_ORDER_LIST), body);
        return getMallService().myOrderList(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<ListData<OrderItem>>>applyExecutorSchedulers());
    }

    /**
     * 订单详情
     * @param body
     * @return
     */
    public static Observable<ApiResponse<OrderDetailData>> orderDetail(PoBody body) {
        Request<PoBody> request = new Request<>(RequestHeader.create(ApiConstants.ORDER_DETAIL), body);
        return getMallService().orderDetail(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<OrderDetailData>>applyExecutorSchedulers());
    }

    /**
     * 评论订单
     * @param body
     * @return
     */
    public static Observable<ApiResponse> commentOrder(CommentOrderBody body) {
        Request<CommentOrderBody> request = new Request<>(RequestHeader.create(ApiConstants.COMMENT_ORDER), body);
        return getMallService().commentOrder(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse>applyExecutorSchedulers());
    }

    /**
     * 查看订单物流信息
     * @param body
     * @return
     */
    public static Observable<ApiResponse> lookOrderLogistics(PoBody body) {
        Request<PoBody> request = new Request<>(RequestHeader.create(ApiConstants.ORDER_LOGISTICS), body);
        return getMallService().lookOrderLogistics(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<LogisticsData>>applyExecutorSchedulers());
    }

    /**
     * 查看订单物流信息
     * @param body
     * @return
     */
    public static Observable<ApiResponse<CheckResultData>> getCheckResult(IdRequest body) {
        Request<IdRequest> request = new Request<>(RequestHeader.create(ApiConstants.CHECK_RESULT), body);
        return getHomePageDataService().getCheckResult(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<LogisticsData>>applyExecutorSchedulers());
    }

   //-----------------------------------------------------------------------------------------------

    /**
     * 注意：type为业务类型
     * 1.上传用户头像
     * 2.上传认证车辆
     * 3.上传认证视频 统一用ma4格式录制
     * 4.上传个人相册照片
     * 服务器返回数据如下：
     * {"data":"http://localhost:80/20161020/3/head/1476975186066.jpg","head":{"sign":"369de99aa9cd1bd63e85b39ae6ecfc1d","status":"1","datecode":"date000","msg":"上传成功！"}}
     * 如果是同时上传多种图  data 中间的数据以 1.jpg@X@2.jpg格式返回
     * 同时上传多种图片时客户端提交表单的file控件名字不能一样 file1,file2之类依次命名
     * 图片上传完成后会自动更新到数据库不0用再客户端去手动更新了
     *
     * @param path
     * @return
     */
    public static Observable<ApiResponse<String>> upload(int type, File... path) {
        //video/mp4
        //----多文件已测试通过---
        Map<String, RequestBody> photosMap = new HashMap<>();
        for (int i = 0; i < path.length; i++) {
            RequestBody fileBody = RequestBody.create(MediaType.parse(type == Constants.UPLOAD_TYPE_VIDEO ? "video/mp4" : "image/jpg"), path[i]);
            // RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), path[i]);
            String fileName = "fileUpload" + i;
            photosMap.put(fileName + "\"; filename=\"" + path[i].getName(), fileBody);
            //photos.put("path",  RequestBody.create(null, "user"));
        }
        return getApiService().upload(String.valueOf(type), String.valueOf(User.get().getId()), ApiConstants.UPLOAD, photosMap).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<String>>applyIoSchedulers());
    }

    public static Observable<ApiResponse<String>> uploadSingle(int type, File file) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                //添加文件参数
                .addFormDataPart("files", file.getName(), fileBody)
                .build();
        return getApiService().upload(String.valueOf(type), String.valueOf(User.get().getId()), ApiConstants.UPLOAD, multipartBody).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<String>>applyIoSchedulers());
    }

}
