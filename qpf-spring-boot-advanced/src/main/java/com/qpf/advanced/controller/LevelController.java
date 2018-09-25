package com.qpf.advanced.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class LevelController {
    @GetMapping("/level/{level}")
    public String redirectLevel(@PathVariable("level") String level, Map<String, Object> map) {

        map.put("current level", level);

        return "level";
    }
}
