package com.qpf.springbootquick.components;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {
    // 捕获异常
    @ExceptionHandler(UserNotExistException.class)
    public String handlerUserNotExistException(Exception e, HttpServletRequest request) {
        // 自定义异常信息
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "200");
        map.put("message", e.getMessage());

        // 指定请求状态码,/error/5xx.html
        request.setAttribute("javax.servlet.error.status_code", 500);
        // 将异常信息放入request中
        request.setAttribute("ext", map);
        // 转发到/error,BasicErrorController自适应返回类型
        return "forward:/error";
    }
}
