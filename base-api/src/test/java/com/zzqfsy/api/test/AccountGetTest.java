package com.zzqfsy.api.test;

import com.alibaba.fastjson.JSONObject;
import com.zzqfsy.api.decoder.FeignClassDecoder;
import com.zzqfsy.api.resp.BaseResp;
import com.zzqfsy.api.rpc.IAccountFacade;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.form.FormEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 20:01 2018/8/22
 * @Modified By:
 **/
public class AccountGetTest {
    private static Logger logger= LoggerFactory.getLogger(AccountChangeTest.class);

    IAccountFacade service = Feign.builder()
            .options(new Request.Options(15000, 15000))
            .retryer(new Retryer.Default(5000, 5000, 1))
            .encoder(new FormEncoder())
            .decoder(new FeignClassDecoder())
            .target(IAccountFacade.class, "http://127.0.0.1:8181");

    public void getAccount(){
        BaseResp baseResp = service.getUserAccountBalance("1");
        logger.info("out: " + JSONObject.toJSONString(baseResp));
    }


    //@Test
    public void testGetAccountBalance(){
        testGetAccountBalance();
    }

}
