package com.qpf.springboot.config;

import com.qpf.springboot.bean.Temp;
import com.qpf.springboot.listeners.HelloApplicationRunner;
import com.qpf.springboot.listeners.HelloCommandLineRunner;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAppConfig {
    @Bean
    public Temp temp () {
        return new Temp();
    }

    @Bean
    public ApplicationRunner addApplicationRunner() {
        return new HelloApplicationRunner();
    }
    @Bean
    public CommandLineRunner addCommandLineRunner() {
        return new HelloCommandLineRunner();
    }
}
