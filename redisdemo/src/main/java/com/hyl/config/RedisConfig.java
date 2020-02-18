package com.hyl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.io.Serializable;
import java.time.Duration;

@Configuration
public class RedisConfig {
    //必须有一个redis的连接工厂
    private RedisConnectionFactory redisConnectionFactory;

    //    这里是使用redisTemplate序列化能更好控制逻辑
//      引入的依赖已经有了一个RedisTemplate配置类，当我们想用自己的可以重新写一个并放入到spring容器中
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Serializable> redisTemplate = new RedisTemplate<String,Serializable>();
        redisTemplate.setKeySerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());// Hash key序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());// Hash value序列化
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
    //    注解缓存时使用的序列化器
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(){
        RedisCacheConfiguration redisCacheConfiguration=RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration=redisCacheConfiguration
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofSeconds(30));
        return redisCacheConfiguration;
    }

}