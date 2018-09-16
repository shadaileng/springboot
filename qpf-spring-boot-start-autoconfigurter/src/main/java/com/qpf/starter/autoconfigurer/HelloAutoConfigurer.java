package com.qpf.starter.autoconfigurer;

import com.qpf.starter.properties.HelloProperties;
import com.qpf.starter.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(HelloProperties.class)
public class HelloAutoConfigurer {
    @Autowired
    private HelloProperties properties;
    @Bean
    public HelloService addHelloService() {
        return new HelloService(properties);
    }
}
