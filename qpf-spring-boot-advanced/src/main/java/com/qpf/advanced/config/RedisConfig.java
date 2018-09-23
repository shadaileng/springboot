package com.qpf.advanced.config;

import com.qpf.advanced.bean.Department;
import com.qpf.advanced.bean.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;
import java.util.List;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<Object, Employee> employeeRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Employee> template = new RedisTemplate<Object, Employee>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Employee> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Employee.class);
        template.setDefaultSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    @Bean
    public RedisTemplate<Object, Department> departmentRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Department> template = new RedisTemplate<Object, Department>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Department> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Department>(Department.class);
        template.setDefaultSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    @Bean
    public RedisCacheManager employeeRedisCacheManager(RedisTemplate employeeRedisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(employeeRedisTemplate);
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }

    @Bean
    public RedisCacheManager departmentRedisCacheManager(RedisTemplate departmentRedisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(departmentRedisTemplate);
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }
    @Primary
    @Bean
    public RedisCacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }
}
