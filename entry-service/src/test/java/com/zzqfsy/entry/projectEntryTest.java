package com.zzqfsy.entry;

import com.alibaba.fastjson.JSONObject;
import com.zzqfsy.api.decoder.FeignClassDecoder;
import com.zzqfsy.api.resp.BaseResp;
import com.zzqfsy.api.rpc.IEntryFacade;
import com.zzqfsy.api.rpc.IOrderFacade;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.form.FormEncoder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 23:09 2018/8/22
 * @Modified By:
 **/
public class projectEntryTest {

    private static Logger logger= LoggerFactory.getLogger(projectEntryTest.class);

    IEntryFacade service = Feign.builder()
            .options(new Request.Options(15000, 15000))
            .retryer(new Retryer.Default(5000, 5000, 1))
            .encoder(new FormEncoder())
            .decoder(new FeignClassDecoder())
            .target(IEntryFacade.class, "http://127.0.0.1:8180");

    public void buyProject(){
        BaseResp baseResp = service.buyProject("1","1", "-100");
        logger.info("out: " + JSONObject.toJSONString(baseResp));
    }

    //@Test
    public void oneBuyProjectTest(){
        buyProject();
    }

}
