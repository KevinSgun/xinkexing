package com.thinkeract.tka.data.api;


import com.thinkeract.tka.Constants;
import com.thinkeract.tka.User;
import com.thinkeract.tka.common.utils.Md5;
import com.thinkeract.tka.data.api.request.FindPasswordBody;
import com.thinkeract.tka.data.api.request.LoginBody;
import com.thinkeract.tka.data.api.request.RegisterBody;
import com.thinkeract.tka.data.api.request.Request;
import com.thinkeract.tka.data.api.request.RequestHeader;
import com.thinkeract.tka.data.api.request.ValidationCodeBody;
import com.thinkeract.tka.data.api.response.UserData;
import com.zitech.framework.data.network.HttpResultFunc;
import com.zitech.framework.data.network.RetrofitClient;
import com.zitech.framework.data.network.response.ApiResponse;
import com.zitech.framework.data.network.subscribe.SchedulersCompat;

import java.io.File;
import java.util.HashMap;
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

    public static OrderPayService getOrderPayService() {
        return getService(ApiConstants.getRootUrl(), OrderPayService.class);
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
     * 注册
     *
     * @return
     */
    public static Observable<ApiResponse<UserData>> register(RegisterBody body) {
        Request<RegisterBody> request = new Request<RegisterBody>(RequestHeader.create(ApiConstants.REGISTER), body);
        return getApiService().register(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse>applyExecutorSchedulers());
    }

    /**
     * 用户登录
     *
     * @return
     * @see UserData
     */
    public static Observable<ApiResponse<UserData>> login(String phoneNumber, String password) {
        LoginBody loginBody = new LoginBody();
        loginBody.setPassword(Md5.ecryptSpecial(password));
        loginBody.setMobile(phoneNumber);
        Request<LoginBody> request = new Request<LoginBody>(RequestHeader.create(ApiConstants.LONGIN), loginBody);
        return getApiService().login(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse<UserData>>applyExecutorSchedulers());
    }

    /**
     * 找回密码
     *
     * @return
     */
    public static Observable<ApiResponse> findPassword(FindPasswordBody body) {
        Request<FindPasswordBody> request = new Request<FindPasswordBody>(RequestHeader.create(ApiConstants.FINDPASSWORD), body);
        return getApiService().findPassword(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse>applyExecutorSchedulers());
    }
//
//    /**
//     * 完善用户资料
//     * @param body
//     * @return
//     */
//    public static Observable<ApiResponse> completeProfile(PerfectDataRequest body) {
//        Request<PerfectDataRequest> request = new Request<>(RequestHeader.create(ApiConstants.COMPLETE_PROFILE), body);
//        return getApiService().completeProfile(request.sign()).map(new HttpResultFunc()).compose(SchedulersCompat.<ApiResponse>applyExecutorSchedulers());
//
//    }

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
