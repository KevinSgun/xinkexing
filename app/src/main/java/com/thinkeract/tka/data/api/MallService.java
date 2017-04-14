package com.thinkeract.tka.data.api;

import com.thinkeract.tka.data.api.entity.GoodsComment;
import com.thinkeract.tka.data.api.entity.GoodsItem;
import com.thinkeract.tka.data.api.request.IdRequest;
import com.thinkeract.tka.data.api.request.ListBody;
import com.thinkeract.tka.data.api.request.Request;
import com.thinkeract.tka.data.api.response.GoodsDetailData;
import com.thinkeract.tka.data.api.response.ListData;
import com.zitech.framework.data.network.response.ApiResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by minHeng on 2017/4/12 16:42.
 * mail:minhengyan@gmail.com
 */

public interface MallService {
    /**
     * 所有商品列表
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ApiResponse<ListData<GoodsItem>>> allGoods(@Body Request<ListBody> request);

    /**
     * 商品检索分类列表
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ApiResponse<String>> goodsClassify(@Body Request request);

    /**
     * 商品详情
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ApiResponse<GoodsDetailData>> goodsDetail(@Body Request<IdRequest> request);

    /**
     * 商品评论列表
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ApiResponse<ListData<GoodsComment>>> goodsCommentList(@Body Request<ListBody> request);
}
