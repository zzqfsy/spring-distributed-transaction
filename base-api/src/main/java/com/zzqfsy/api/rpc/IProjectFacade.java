package com.zzqfsy.api.rpc;

import com.zzqfsy.api.resp.BaseResp;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 10:49 2018/8/21
 * @Modified By:
 **/
public interface IProjectFacade {
    @RequestLine("POST /project/{projectId}")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    BaseResp changeProjectAble(@Param("projectId") String projectId, @Param("changeAmount") String changeAmount);
}
