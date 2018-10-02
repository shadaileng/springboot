package com.qpf.mall.controller;

import com.qpf.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/buyticket/{userId}")
    public Map<String, Object> buyTicket(@PathVariable("userId") String userId) {
        Map<String, Object> map = new HashMap<String, Object>();

        String ticket = orderService.buyTicket(userId);

        map.put("result", "ok");
        map.put("hits", ticket);

        return map;
    }
}
