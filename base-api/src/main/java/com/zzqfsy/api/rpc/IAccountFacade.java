package com.zzqfsy.api.rpc;

import com.zzqfsy.api.resp.BaseResp;
import feign.*;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 8:40 2018/8/17
 * @Modified By:
 **/
public interface IAccountFacade {

    @RequestLine("GET /account/{userId}")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    BaseResp getUserAccountBalance(@Param("userId") String userId);

    @RequestLine("POST /account/{userId}")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    BaseResp changeUserAccountBalance(@Param("userId") String userId, @Param("orderNo") String orderNo,
                                      @Param("changeAmount") String changeAmount);
}
