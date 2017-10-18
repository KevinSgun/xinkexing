package com.thinkeract.tka.data.api;


import com.thinkeract.tka.data.api.entity.AddressItem;
import com.thinkeract.tka.data.api.request.DoctorDataReviewBody;
import com.thinkeract.tka.data.api.request.IdRequest;
import com.thinkeract.tka.data.api.request.LoginBody;
import com.thinkeract.tka.data.api.request.Request;
import com.thinkeract.tka.data.api.request.UpdateAddressBody;
import com.thinkeract.tka.data.api.request.UpdateUserDataBody;
import com.thinkeract.tka.data.api.request.ValidationCodeBody;
import com.thinkeract.tka.data.api.response.UserData;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;
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
     * 用户登录
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
   Observable<ApiResponse<UserData>> login(@Body Request<LoginBody> request);

    /**
     * 绑定手机
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ApiResponse> bindingPhone(@Body Request<LoginBody> request);


    /**
     * 修改用户资料
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ApiResponse> updateUserData(@Body Request<UpdateUserDataBody> request);

    /**
     * 用户收货地址列表
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ApiResponse<List<AddressItem>>> getUserAddressList(@Body Request request);

    /**
     * 新增用户收货地址
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ApiResponse> addAddress(@Body Request<UpdateAddressBody> request);


    /**
     * 用户收货地址删除接口
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ApiResponse> deleteAddress(@Body Request<IdRequest> request);

    /**
     * 用户收货地址修改接口
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ApiResponse> modifyAddress(@Body Request<UpdateAddressBody> request);

    /**
     * 医生审核资料
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ApiResponse> doctorDataReview(@Body Request<DoctorDataReviewBody> request);

    //------------------------------------------------------------------------------------------------
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
                                                        @Query("datacode") String datecode,
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
                                                       @Query("datacode") String datecode,
                                                       @Body MultipartBody partBody);

}
