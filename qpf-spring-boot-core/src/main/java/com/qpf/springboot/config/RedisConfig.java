package com.qpf.springboot.config;

import com.qpf.springboot.bean.Department;
import com.qpf.springboot.bean.Employee;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;
import java.util.List;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<Object, Employee> employeeRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Employee> template = new RedisTemplate<Object, Employee>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Employee> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Employee.class);
        template.setDefaultSerializer(jsonRedisSerializer);
        return template;
    }
    @Bean
    public RedisTemplate<Object, Department> departmentRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Department> template = new RedisTemplate<Object, Department>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Department> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Department.class);
        template.setDefaultSerializer(jsonRedisSerializer);
        return template;
    }
    @Bean
    public RedisCacheManager employeeRedisCacheManager(RedisTemplate employeeRedisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(employeeRedisTemplate);
        redisCacheManager.setUsePrefix(true);
        return redisCacheManager;
    }
    @Bean
    public RedisCacheManager departmentRedisManager(RedisTemplate departmentRedisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(departmentRedisTemplate);
        redisCacheManager.setUsePrefix(true);
        return redisCacheManager;
    }
    @Primary
    @Bean
    public RedisCacheManager cacheManager(RedisTemplate stringRedisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(stringRedisTemplate);
        cacheManager.setUsePrefix(true);
        return cacheManager;
    }
}
