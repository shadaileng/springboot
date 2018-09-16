package com.qpf.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ImportResource(locations = {"classpath:beans.xml"})
//@MapperScan(basePackages = "com.qpf.springboot.mapper")
@SpringBootApplication
public class SpringBootQuickApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootQuickApplication.class, args);
    }
}
