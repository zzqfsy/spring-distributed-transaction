package com.zzqfsy.account.test.base;

import org.mockito.Mockito;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Author: zzqfsy
 * @Description:
 * @date: Created in 11:44 AM 2018/12/31
 * @modified By:
 **/
@Configuration
public class BaseMockConfigTest {

    @Bean
    @Primary
    public RedissonClient redissonClient(){
        return Mockito.mock(RedissonClient.class);
    }
    //
    //@Before
    //public void init(){
    //    MockitoAnnotations.initMocks(this);
    //}

}
