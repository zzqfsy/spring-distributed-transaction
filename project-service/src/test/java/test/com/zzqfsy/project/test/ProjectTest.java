package test.com.zzqfsy.project.test;

import com.alibaba.fastjson.JSONObject;
import com.zzqfsy.api.decoder.FeignClassDecoder;
import com.zzqfsy.api.resp.BaseResp;
import com.zzqfsy.api.rpc.IAccountFacade;
import com.zzqfsy.api.rpc.IProjectFacade;
import feign.*;
import feign.form.FormEncoder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 10:28 2018/8/21
 * @Modified By:
 **/
public class ProjectTest {
    private static Logger logger= LoggerFactory.getLogger(ProjectTest.class);

    private AtomicInteger serialNumber = new AtomicInteger();

    public void changProject(Integer i){
        IProjectFacade service = Feign.builder()
                .options(new Request.Options(15000, 15000))
                .retryer(new Retryer.Default(5000, 5000, 1))
                .encoder(new FormEncoder())
                .decoder(new FeignClassDecoder())
                .requestInterceptor(new RequestInterceptor() {
                    @Override
                    public void apply(RequestTemplate template) {
                        template.header("serialNumber", i.toString());
                    }
                })
                .target(IProjectFacade.class, "http://127.0.0.1:8182");

        BaseResp baseResp = service.changeProjectAble("1", "-100");
        logger.info("out: " + JSONObject.toJSONString(baseResp));
    }

    @Test
    public void oneTest(){
        changProject(1);
    }

    @Test
    public void BatchTest(){
        int threadNum = 1000;
        final ExecutorService service = Executors.newFixedThreadPool(threadNum);
        CyclicBarrier barrier = new CyclicBarrier(threadNum, () -> System.out.println(threadNum + " threads ready, let's go"));
        CountDownLatch latch = new CountDownLatch(threadNum);

        try {
            List<Future> futures = new ArrayList<>();
            for (int i = 0; i < threadNum; i++) {
                int finalI = i;
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
                    changProject(finalI);
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
