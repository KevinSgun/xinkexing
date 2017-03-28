package com.thinkeract.tka.data.api;


import com.thinkeract.tka.data.api.request.FindPasswordBody;
import com.thinkeract.tka.data.api.request.LoginBody;
import com.thinkeract.tka.data.api.request.RegisterBody;
import com.thinkeract.tka.data.api.request.Request;
import com.thinkeract.tka.data.api.request.ValidationCodeBody;
import com.thinkeract.tka.data.api.response.UserData;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 *
 * Created by lu on 2016/6/2.
 */
public interface AccountService {


    /**
     * 获取验证码
     *
     * @param request
     * @return
     */

    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ApiResponse> getValidationCode(@Body Request<ValidationCodeBody> request);

    /**
     * 注册
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
   Observable<ApiResponse<UserData>> register(@Body Request<RegisterBody> request);

    /**
     * 用户登录
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
   Observable<ApiResponse<UserData>> login(@Body Request<LoginBody> request);


    /**
     * 找回密码
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
   Observable<ApiResponse> findPassword(@Body Request<FindPasswordBody> request);
//
//    /**
//     * 完善用户资料
//     *
//     * @param request
//     * @return
//     */
//    @POST(ApiConstants.COMMON_REQUEST)
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//   io.reactivex.Observable<ApiResponse> completeProfile(@Body Request request);
//
//
//    /**
//     * 获取所在行业和经营阶段列表
//     *
//     * @param request
//     * @return
//     */
//    @POST(ApiConstants.COMMON_REQUEST)
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//   io.reactivex.Observable<ApiResponse<IndustryAndPhaseResponse>> getIndustryAndPhase(@Body Request request);
    /**
     * 多文件上传
     *
     * @param params
     * @return
     */
    @Multipart
    @POST(ApiConstants.UPLOAD_REQUEST)
    io.reactivex.Observable<ApiResponse<String>> upload(@Query("type") String type,
                                                        @Query("userId") String userId,
                                                        @Query("datecode") String datecode,
                                                        @PartMap Map<String, RequestBody> params);
    /**
     * 单文件上传
     *
     * @param partBody
     * @return
     */
    @POST(ApiConstants.UPLOAD_REQUEST)
   io.reactivex.Observable<ApiResponse<String>> upload(@Query("type") String type,
                                                       @Query("userId") String userId,
                                                       @Query("datecode") String datecode,
                                                       @Body MultipartBody partBody);

}
