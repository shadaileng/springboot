package com.qpf.mall.service;

import com.alibaba.dubbo.config.annotation.Reference;
import mall.service.OrderService;
import mall.service.UserService;
import org.springframework.stereotype.Repository;

@Repository
public class OrderServiceImpl implements OrderService {

    @Reference
    private UserService userService;

    @Override
    public String buyTicket(String userId) {
        String userName = userService.getUserName(userId);

        return userName + " 买到了一张票";
    }
}
