package com.qpf.advanced.controller;

import com.qpf.advanced.bean.Employee;
import com.qpf.advanced.services.DepartmentService;
import com.qpf.advanced.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;
    @GetMapping("/empls")
    public String employees(Map<String, Object> map) {
        map.put("empls", employeeService.getEmployees());
        return "list";
    }
    @GetMapping("/empl")
    public String redictAdd(Map<String, Object> map) {
        map.put("depts", DepartmentService.getDepartments());
        return "add";
    }

    @PostMapping("/empl")
    public String addEmployee(Employee employee) {
        System.out.println("save: " + employee);
        employee = employeeService.save(employee);
        System.out.println("result: " + employee);
        return "redirect:/empls";
    }

    @GetMapping("/empl/{id}")
    public String redictEdit(@PathVariable("id") Integer id, Map<String, Object> map) {
        map.put("depts", DepartmentService.getDepartments());
        map.put("empl", employeeService.getEmployee(id));
        return "add";
    }

    @PutMapping("/empl")
    public String editEmployee(Employee employee) {
        System.out.println("edit: " + employee);
        employee = employeeService.edit(employee);
        System.out.println("result: " + employee);
        return "redirect:/empls";
    }

    @DeleteMapping("/empl/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        System.out.println("delete: " + id);
        id = employeeService.delete(id);
        System.out.println("result: " + id);
        return "redirect:/empls";
    }
}