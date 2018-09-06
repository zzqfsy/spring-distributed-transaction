import com.alibaba.fastjson.JSONObject;
import com.zzqfsy.api.decoder.FeignClassDecoder;
import com.zzqfsy.api.resp.BaseResp;
import com.zzqfsy.api.rpc.IOrderFacade;
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
public class OrderCreateTest {
    private static Logger logger= LoggerFactory.getLogger(OrderCreateTest.class);

    IOrderFacade service = Feign.builder()
            .options(new Request.Options(15000, 15000))
            .retryer(new Retryer.Default(5000, 5000, 1))
            .encoder(new FormEncoder())
            .decoder(new FeignClassDecoder())
            .target(IOrderFacade.class, "http://127.0.0.1:8183");

    public void createOrder(){
        BaseResp baseResp = service.createOrder("1","1", "100");
        logger.info("out: " + JSONObject.toJSONString(baseResp));
    }

    @Test
    public void oneCreateOrderTest(){
        createOrder();
    }

    @Test
    public void BatchCreateOrderTest(){
        int threadNum = 10;
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
                    createOrder();
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
