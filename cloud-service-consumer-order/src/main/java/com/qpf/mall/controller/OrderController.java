package com.qpf.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/buyticket/{userId}")
    public Map<String, Object> buyTicket(@PathVariable("userId") String userId) {
        String userName = restTemplate.getForObject("http://cloud-service-provider-user/user/" + userId, String.class);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("result", "ok");
        map.put("hits", userName + " 买到了一张票");

        return map;
    }
}
