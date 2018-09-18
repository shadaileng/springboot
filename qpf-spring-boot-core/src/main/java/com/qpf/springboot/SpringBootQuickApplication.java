package com.qpf.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//@ImportResource(locations = {"classpath:beans.xml"})
//@MapperScan(basePackages = "com.qpf.springboot.mapper")
@EnableCaching
@SpringBootApplication
public class SpringBootQuickApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootQuickApplication.class, args);
    }
}
