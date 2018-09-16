package com.qpf.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @PostMapping(value = "/user/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Map<String, Object> map, HttpSession session) {

        if (!StringUtils.isEmpty(password) && "123456".equals(password)) {

            logger.info(email + " [登陆成功]");

            session.setAttribute("user", email);

            return "redirect:/main";
        } else {
            logger.info(email + " [密码错误]");
            map.put("msg", "密码错误");
            return "login";
        }

    }
}
