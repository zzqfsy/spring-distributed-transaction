package com.zzqfsy.account.conf.redis;

import io.lettuce.core.ClientOptions;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

/**
 * Created by john on 16-7-6.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    @ConditionalOnBean(RedisConfProperties.class)
    public CacheManager cacheManager(@Qualifier("connectionFactoryDB0") RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.create(redisConnectionFactory);
    }

    @Bean(name = "redisTemplateDB0")
    @ConditionalOnBean(RedisConfProperties.class)
    public RedisTemplate<String, String> redisTemplateDB0(@Qualifier("connectionFactoryDB0") RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnBean(RedisConfProperties.class)
    public RedissonClient getRedissonClient(@Autowired RedisConfProperties redisConfProperties) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisConfProperties.getHost() + ":" + redisConfProperties.getPort())
                .setPassword(redisConfProperties.getPassword())
                .setDatabase(redisConfProperties.getDatabase());
        return Redisson.create(config);
    }

    @Bean(name = "connectionFactoryDB0")
    @ConditionalOnBean(RedisConfProperties.class)
    public RedisConnectionFactory connectionFactoryDB1(@Autowired RedisConfProperties redisConfProperties) {
        return connectionFactory(redisConfProperties.getHost(), redisConfProperties.getPort(), redisConfProperties.getDatabase(), redisConfProperties.getPassword());
    }

    public RedisConnectionFactory connectionFactory(String hostName, int port, int database, String password) {
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//                .useSsl().and()
                .commandTimeout(Duration.ofSeconds(2))
                .shutdownTimeout(Duration.ZERO)
                .clientOptions(ClientOptions.builder().pingBeforeActivateConnection(true).build())
                .build();

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(hostName, port);
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
    }

}
