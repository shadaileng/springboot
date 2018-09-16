package com.qpf.springboot.controller;

import com.qpf.springboot.bean.Department;
import com.qpf.springboot.bean.Employee;
import com.qpf.springboot.mapper.DepartmentMapper;
import com.qpf.springboot.mapper.EmployeeMapper;
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
    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping("/test/getdept/{id}")
    public Map<String, Object> selectDepart(@PathVariable("id") Integer id) {
        Map<String, Object> map = new HashMap<>();

        Department department = departmentMapper.selectDepartmentById(id);

        System.out.println("department: " + department);

        map.put("dept", department);

        return map;
    }

    @GetMapping("/test/dept")
    public Map<String, Object> insertDepartment(Department department) {
        Map<String, Object> map = new HashMap<>();
        int i = departmentMapper.insertDepartment(department);
        System.out.println("insert: " + i);
        map.put("dept", department);
        return map;
    }

    @GetMapping("test/getempl/{id}")
    public Map<String, Object> selectEmpl(@PathVariable("id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        Employee employee = employeeMapper.selectEmployeeById(id);
        System.out.println("employee: " + employee);
        map.put("empl", employee);
        return map;
    }

    @GetMapping("/test/empl")
    public Map<String, Object> insertEmployee(Employee employee) {
        Map<String, Object> map = new HashMap<>();
        int insert = employeeMapper.insertEmployee(employee);
        System.out.println("insert: " + insert);
        map.put("empl", employee);
        return map;
    }
}
