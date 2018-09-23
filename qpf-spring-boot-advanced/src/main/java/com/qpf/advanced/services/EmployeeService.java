package com.qpf.advanced.services;

import com.qpf.advanced.bean.Employee;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CacheConfig(cacheNames = "empl", cacheManager = "employeeRedisCacheManager")
@Repository
public class EmployeeService {
    private static Map<Integer, Employee> employees = new HashMap<Integer, Employee>();
    private static int index = 0;
    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            employees.put(index, new Employee(index++, "qpf", "qpf@qq.com", "M", DepartmentService.getDepartment(1), dateFormat.parse("1992-05-10")));
            employees.put(index, new Employee(index++, "sql", "sql@qq.com", "M", DepartmentService.getDepartment(1), dateFormat.parse("1992-05-10")));
            employees.put(index, new Employee(index++, "shadaileng", "shadaileng@qq.com", "M", DepartmentService.getDepartment(1), dateFormat.parse("1992-05-10")));
            employees.put(index, new Employee(index++, "cy", "cy@qq.com", "M", DepartmentService.getDepartment(1), dateFormat.parse("1992-04-13")));
            employees.put(index, new Employee(index++, "cxl", "cxl@qq.com", "M", DepartmentService.getDepartment(1), dateFormat.parse("1993-05-16")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Collection<Employee> getEmployees() {
        return employees.values();
    }

    public Employee save(Employee employee) {
        employee.setId(index);
        employee.setDepartment(DepartmentService.getDepartment(employee.getDepartment().getId()));
        employees.put(index++, employee);
        return employee;
    }

    @CachePut(key = "#result.id")
    public Employee edit(Employee employee) {
        System.out.println("edit(" + employee.getId() + ")");
        employee.setDepartment(DepartmentService.getDepartment(employee.getDepartment().getId()));
        employees.put(employee.getId(), employee);
        return employee;
    }

    @Cacheable(key = "#id")
    public Employee getEmployee(int id) {
        System.out.println("getEmployee(" + id + ")");
        return employees.get(id);
    }

    @CacheEvict(key = "#id")
    public int delete(Integer id) {
        System.out.println("delete(" + id + ")");
        employees.remove(id);
        return 0;
    }
}
