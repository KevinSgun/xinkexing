package com.thinkeract.tka.data.api;

import com.thinkeract.tka.data.api.entity.NewsItem;
import com.thinkeract.tka.data.api.request.IdRequest;
import com.thinkeract.tka.data.api.request.Request;
import com.thinkeract.tka.data.api.response.NewsDetailData;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by minHeng on 2016/12/5 16:38.
 * mail:minhengyan@gmail.com
 */

public interface HomePageDataService {

    /**
     * 推荐新闻列表
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ApiResponse<List<NewsItem>>> newsRecommendList(@Body Request request);

    /**
     * 新闻详情
     *
     * @param request
     * @return
     */
    @POST(ApiConstants.COMMON_REQUEST)
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Observable<ApiResponse<NewsDetailData>> newsDetail(@Body Request<IdRequest> request);
}
