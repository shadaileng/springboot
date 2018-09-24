package com.qpf.advanced;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@EnableRabbit
@EnableCaching
@SpringBootApplication
public class QpfSpringBootAdvancedApplication {

    public static void main(String[] args) {
        SpringApplication.run(QpfSpringBootAdvancedApplication.class, args);
    }
}
