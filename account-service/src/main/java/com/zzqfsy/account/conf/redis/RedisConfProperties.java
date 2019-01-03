package com.zzqfsy.account.conf.redis;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 荀凡
 * @Description:
 * @date: Created in 11:17 AM 2018/12/31
 * @modified By:
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@ConditionalOnProperty("spring.redis.host")
public class RedisConfProperties {

    private String host;
    private Integer port;
    private String password;
    private Integer database;
}
