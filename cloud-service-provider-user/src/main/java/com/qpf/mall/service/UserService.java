package com.qpf.mall.service;

import org.springframework.stereotype.Service;

@Service
public class UserService{
    public String getUserName(String userId) {
        return "User - " + userId;
    }
}
