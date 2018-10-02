package com.qpf.mall.controller;

import com.qpf.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{userId}")
    public String getUserName(@PathVariable("userId") String userId) {
        return userService.getUserName(userId);
    }

    @GetMapping("/testuser")
    public String getUserTest() {
        return "test User";
    }
}
