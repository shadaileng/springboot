package com.qpf.springboot.controller;

import com.qpf.springboot.bean.Department;
import com.qpf.springboot.bean.Employee;
import com.qpf.springboot.dao.DepartmentService;
import com.qpf.springboot.dao.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public String addOne(Employee employee, BindingResult bindingResult, HttpServletRequest request) {
        System.out.println(request.getParameter("dept_id"));
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();

            for (ObjectError error: errors) {
                logger.warn("error: " + error.getDefaultMessage());
            }
        }

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
    public String editEmpl(Employee employee, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();

            for (ObjectError error: errors) {
                logger.warn("error: " + error);
            }
        }

        logger.info("修改: " + employee);
        employee = employeeService.editOne(employee);
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
