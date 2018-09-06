package com.zzqfsy.entry.proxy;

import com.zzqfsy.api.decoder.FeignClassDecoder;
import com.zzqfsy.api.resp.BaseResp;
import com.zzqfsy.api.rpc.IAccountFacade;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.form.FormEncoder;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 14:45 2018/8/21
 * @Modified By:
 **/
public class AccountProxy {
    private static final IAccountFacade service = Feign.builder()
            .options(new Request.Options(5000, 5000))
            .retryer(new Retryer.Default(5000, 5000, 1))
            .encoder(new FormEncoder())
            .decoder(new FeignClassDecoder())
            .target(IAccountFacade.class, "http://127.0.0.1:8181");

    public static BaseResp getUserAccountBalance(String userId){
        return service.getUserAccountBalance(userId);
    }

    public static BaseResp changeUserAccountBalance(String userId, String orderNo, String changeAmount){
        return service.changeUserAccountBalance(userId, orderNo, changeAmount);
    }
}
