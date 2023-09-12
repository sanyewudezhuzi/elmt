package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 当前配置类不是必须的，因为 Spring Boot 框架会自动装配 RedisTemplate 对象，
 * 但是默认的 key 序列化器为 JdkSerializationRedisSerializer ，
 * 导致我们存到 Redis 中后的数据和原始数据有差别，故设置为 StringRedisSerializer 序列化器
 */
@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("创建redis模板对象");
        RedisTemplate redisTemplate = new RedisTemplate();

        // 设置redis的连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置redis key的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        return redisTemplate;
    }

}
