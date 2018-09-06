package com.zzqfsy.api.rpc;

import com.zzqfsy.api.resp.BaseResp;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 23:10 2018/8/22
 * @Modified By:
 **/
public interface IEntryFacade {

    @RequestLine("POST /entry/project/buy")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    BaseResp buyProject(@Param("userId") String userId, @Param("projectId") String projectId,
                                      @Param("amount") String amount);

    @RequestLine("POST /entry/project/buy/mq")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    BaseResp buyProjectMQ(@Param("userId") String userId, @Param("projectId") String projectId,
                        @Param("amount") String amount);
}
