package com.qpf.springbootquick.controller;

import com.qpf.springbootquick.bean.Department;
import com.qpf.springbootquick.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MybatisController {

    @Autowired
    private DepartmentMapper departmentMapper;

    @GetMapping("/test/getdept/{id}")
    public Map<String, Object> selectDepart(@PathVariable("id") Integer id) {
        Map<String, Object> map = new HashMap<>();

        Department department = departmentMapper.selectDepartmentById(id);

        System.out.println("department: " + department);

        map.put("dept", department);

        return map;
    }
}
