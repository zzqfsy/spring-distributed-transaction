package com.zzqfsy.api.test;

import com.alibaba.fastjson.JSONObject;
import com.zzqfsy.api.decoder.FeignClassDecoder;
import com.zzqfsy.api.resp.BaseResp;
import com.zzqfsy.api.rpc.IOrderFacade;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.form.FormEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 23:01 2018/8/22
 * @Modified By:
 **/
public class OrderUpdateTest {
    private static Logger logger= LoggerFactory.getLogger(OrderCreateTest.class);

    IOrderFacade service = Feign.builder()
            .options(new Request.Options(15000, 15000))
            .retryer(new Retryer.Default(5000, 5000, 1))
            .encoder(new FormEncoder())
            .decoder(new FeignClassDecoder())
            .target(IOrderFacade.class, "http://127.0.0.1:8183");

    public void updateOrder(){
        BaseResp baseResp = service.updateOrderStatus("PO0000000011808222303005046771",  "completed");
        logger.info("out: " + JSONObject.toJSONString(baseResp));
    }

    //@Test
    public void oneUpdateOrderTest(){
        updateOrder();
    }
}
