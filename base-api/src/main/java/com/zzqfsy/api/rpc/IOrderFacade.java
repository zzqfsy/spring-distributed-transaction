package com.zzqfsy.api.rpc;

import com.zzqfsy.api.resp.BaseResp;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 12:49 2018/8/21
 * @Modified By:
 **/
public interface IOrderFacade {

    @RequestLine("POST /order")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    BaseResp createOrder(@Param("projectId") String projectId, @Param("userId") String userId,
                         @Param("amount") String amount);

    @RequestLine("PUT /order/{orderNo}")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    BaseResp updateOrderStatus(@Param("orderNo") String orderNo, @Param("orderStatus") String orderStatus);

    @RequestLine("GET /order/{orderNo}")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    BaseResp getOrderByOrderNo(@Param("orderNo") String orderNo);
}
