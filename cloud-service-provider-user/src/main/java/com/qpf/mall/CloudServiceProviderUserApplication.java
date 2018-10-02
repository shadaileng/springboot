package com.qpf.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@SpringBootApplication
public class CloudServiceProviderUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudServiceProviderUserApplication.class, args);
    }
}
