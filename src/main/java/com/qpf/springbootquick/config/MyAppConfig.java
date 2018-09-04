package com.qpf.springbootquick.config;

import com.qpf.springbootquick.bean.Temp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAppConfig {
    @Bean
    public Temp temp () {
        return new Temp();
    }
}
