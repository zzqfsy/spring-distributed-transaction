package com.zzqfsy.account.manager;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.function.LongUnaryOperator;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 10:42 2018/8/20
 * @Modified By:
 **/
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
public class RedisDB0Manager {
    
    @Resource(name = "redisTemplateDB0")
    private ListOperations<String, String> messageList;
    @Resource(name = "redisTemplateDB0")
    private RedisOperations<String, String> redisOperations;
    @Resource(name = "redisTemplateDB0")
    private ValueOperations<String, Object> keyValueStore;
    @Resource(name = "redisTemplateDB0")
    private ZSetOperations<String, String> zSetOperations;

    @Autowired
    private RedissonClient redissonClient;

    public void putObject(String key, Object value) {
        keyValueStore.set(key, value);
    }

    public Object getObject(String key) {
        return keyValueStore.get(key);
    }

    /**
     * casUpdate
     * @param key
     * @param updateFunction
     * @return
     */
    public boolean casUpdateLongValue(String key, LongUnaryOperator updateFunction){
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong(key);
        long expect;
        long update;
        do {
            expect = rAtomicLong.get();
            update = updateFunction.applyAsLong(expect);
        } while (!rAtomicLong.compareAndSet(expect, update));
        return true;
    }


}
