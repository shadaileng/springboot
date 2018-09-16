package com.qpf.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DruidController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test/query")
    public Map<String, Object> query() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from dept");
        return list.size() > 0 ? list.get(0) : new HashMap<>();
    }
}
