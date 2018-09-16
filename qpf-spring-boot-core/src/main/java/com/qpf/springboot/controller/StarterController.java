package com.qpf.springboot.controller;

import com.qpf.starter.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StarterController {

    @Autowired
    private HelloService helloService;
    @GetMapping("/test/starter/{name}")
    public Map<String, Object> hello(@PathVariable("name") String name) {
        Map<String, Object> map = new HashMap<>();


        map.put("starter", helloService.sayHello(name));

        return map;
    }
}
