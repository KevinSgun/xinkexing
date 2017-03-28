package com.zitech.framework.data.network;


import com.zitech.framework.data.network.exception.ApiException;
import com.zitech.framework.data.network.response.ApiResponse;

import io.reactivex.functions.Function;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class HttpResultFunc<T> implements Function<ApiResponse<T>, ApiResponse<T>> {

    @Override
    public ApiResponse<T> apply(ApiResponse<T> tApiResponse) throws Exception {
        if (!tApiResponse.isSuccess()) {
            throw new ApiException(tApiResponse.getStatusCode(), tApiResponse.getMsg());
        }
        return tApiResponse;
    }
}