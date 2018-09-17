package com.qpf.springboot.controller;

import com.qpf.springboot.bean.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/getEmpl")
    public Map<String, Object> testGetEmpl(Employee employee) {
        Map<String, Object> map = new HashMap<>();

        map.put("empl", employee);

        return map;
    }
    @PostMapping("/postEmpl")
    public Map<String, Object> testPostEmpl(Employee employee) {
        Map<String, Object> map = new HashMap<>();

        map.put("empl", employee);

        return map;
    }
}
