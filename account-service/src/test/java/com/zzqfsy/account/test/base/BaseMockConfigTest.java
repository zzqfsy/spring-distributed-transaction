package com.zzqfsy.account.test.base;

import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: 荀凡
 * @Description:
 * @date: Created in 11:44 AM 2018/12/31
 * @modified By:
 **/
@Configuration
public class BaseMockConfigTest extends BaseTest {

    @Bean
    public RedissonClient redissonClient(){
        return Mockito.mock(RedissonClient.class);
    }

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
}
