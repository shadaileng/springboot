package com.qpf.starter.service;

import com.qpf.starter.properties.HelloProperties;

public class HelloService {

    private HelloProperties properties;

    public HelloService(HelloProperties properties) {
        this.properties = properties;
    }

    public String sayHello(String name) {
        return properties.getPrefix() + " - " + name + " - " + properties.getSuffix();
    }
}
