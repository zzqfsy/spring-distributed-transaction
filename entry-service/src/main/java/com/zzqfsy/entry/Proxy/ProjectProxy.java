package com.zzqfsy.entry.proxy;

import com.zzqfsy.api.decoder.FeignClassDecoder;
import com.zzqfsy.api.resp.BaseResp;
import com.zzqfsy.api.rpc.IProjectFacade;
import feign.*;
import feign.form.FormEncoder;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 14:45 2018/8/21
 * @Modified By:
 **/
public class ProjectProxy {
    private static final IProjectFacade service = Feign.builder()
            .options(new Request.Options(15000, 15000))
            .retryer(new Retryer.Default(5000, 5000, 1))
            .encoder(new FormEncoder())
            .decoder(new FeignClassDecoder())
            .target(IProjectFacade.class, "http://127.0.0.1:8182");

    public static BaseResp changProject(String projectId, String changeAmount){
        return service.changeProjectAble(projectId, changeAmount);
    }
}
