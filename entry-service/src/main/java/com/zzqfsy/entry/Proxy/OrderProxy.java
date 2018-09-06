package com.zzqfsy.entry.proxy;

import com.zzqfsy.api.decoder.FeignClassDecoder;
import com.zzqfsy.api.resp.BaseResp;
import com.zzqfsy.api.rpc.IOrderFacade;
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
public class OrderProxy {
    private final static IOrderFacade service = Feign.builder()
            .options(new Request.Options(5000, 5000))
            .retryer(new Retryer.Default(5000, 5000, 1))
            .encoder(new FormEncoder())
            .decoder(new FeignClassDecoder())
            .target(IOrderFacade.class, "http://127.0.0.1:8183");

    public static BaseResp creteOrder(String projectId, String userId, String amount){
        return service.createOrder(projectId,userId, amount);
    }

    public static BaseResp updateOrder2CompletedByOrderNo(String orderNo){
        return service.updateOrderStatus(orderNo, "completed");
    }

    public static BaseResp updateOrder2CanceledByOrderNo(String orderNo){
        return service.updateOrderStatus(orderNo, "canceled");
    }

    public static BaseResp getOrderByOrderNo(String orderNo){
        return service.getOrderByOrderNo(orderNo);
    }
}
