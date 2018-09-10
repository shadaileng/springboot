package com.qpf.springbootquick.controller;

import com.qpf.springbootquick.bean.Department;
import com.qpf.springbootquick.bean.Employee;
import com.qpf.springbootquick.dao.DepartmentService;
import com.qpf.springbootquick.dao.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping(value = "/empls")
    public String list(Map<String, Object> map) {

        Collection<Employee> employees = employeeService.getAll();

        map.put("empls", employees);

        return "empl/list";
    }

    @GetMapping(value = "/empl")
    public String add(Map<String, Object> map) {
        Collection<Department> departments = departmentService.getAll();
        map.put("depts", departments);
        return "empl/add";
    }

    @PostMapping("/empl")
    public String addOne(Employee employee) {

        logger.info("添加员工: " + employee);

        employeeService.save(employee);

        return "redirect:/empls";
    }
    @GetMapping(value = "/empl/{id}")
    public String edit(@PathVariable("id") Integer id, Map<String, Object> map) {
        Collection<Department> departments = departmentService.getAll();
        map.put("depts", departments);
        Employee employee = employeeService.getOne(id);
        map.put("empl", employee);
        return "empl/add";
    }

    @PutMapping("/empl")
    public String editEmpl(Employee employee) {
        logger.info("修改: " + employee);
        int update = employeeService.editOne(employee);
        return "redirect:/empls";
    }
    @DeleteMapping("/empl/{id}")
    public String delEmpl(@PathVariable("id") Integer id) {
        logger.info("删除: " + id);
        int delOne = employeeService.delOne(id);
        logger.info("delone: " + delOne);
        return "redirect:/empls";
    }
}
