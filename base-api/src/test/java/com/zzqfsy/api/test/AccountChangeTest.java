package com.zzqfsy.api.test;

import com.alibaba.fastjson.JSONObject;
import com.zzqfsy.api.decoder.FeignClassDecoder;
import com.zzqfsy.api.resp.BaseResp;
import com.zzqfsy.api.rpc.IAccountFacade;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.form.FormEncoder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 9:21 2018/8/17
 * @Modified By:
 **/

public class AccountChangeTest {
    private static Logger logger= LoggerFactory.getLogger(AccountChangeTest.class);

    IAccountFacade service = Feign.builder()
            .options(new Request.Options(15000, 15000))
            .retryer(new Retryer.Default(5000, 5000, 1))
            .encoder(new FormEncoder())
            .decoder(new FeignClassDecoder())
            .target(IAccountFacade.class, "http://127.0.0.1:8181");

    public void changAccount(){
        BaseResp baseResp = service.changeUserAccountBalance("1", "order", "100");
        logger.info("out: " + JSONObject.toJSONString(baseResp));
    }

    //@Test
    public void oneChangAccountTest(){
        changAccount();
    }

    //@Test
    public void batchChangAccountTest(){
        int threadNum = 2;
        final ExecutorService service = Executors.newFixedThreadPool(threadNum);
        CyclicBarrier barrier = new CyclicBarrier(threadNum, () -> System.out.println(threadNum + " threads ready, let's go"));
        CountDownLatch latch = new CountDownLatch(threadNum);

        try {
            List<Future> futures = new ArrayList<>();
            for (int i = 0; i < threadNum; i++) {
                futures.add(service.submit(() -> {
                    try {
                        logger.info("wait");
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    logger.info("start");
                    changAccount();
                }));
            }

            for(Future future: futures){
                try {
                    future.get(20000, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }
        } finally {
            service.shutdown();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info(threadNum + " times change acount end.");
    }
}
