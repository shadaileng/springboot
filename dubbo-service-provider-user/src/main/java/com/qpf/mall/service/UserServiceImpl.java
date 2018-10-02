package com.qpf.mall.service;

import com.alibaba.dubbo.config.annotation.Service;
import mall.service.UserService;
import org.springframework.stereotype.Component;

@Service
@Component
public class UserServiceImpl implements UserService {
    @Override
    public String getUserName(String userId) {
        return "User - " + userId;
    }
}
