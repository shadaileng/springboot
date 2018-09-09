package com.qpf.springbootquick.dao;

import com.qpf.springbootquick.bean.Department;
import com.qpf.springbootquick.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public class EmployeeService {

    private static List<Employee> empls = new ArrayList<Employee>();
    private static int index = 0;

    @Autowired
    private DepartmentService departmentService;

    static {

//        Collection<Department> depts = DepartmentService.getAllDepartment();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            index = 0;
            empls.add(new Employee(1, "qpf", "qpf@qq.com", "M", DepartmentService.getOne("1"), dateFormat.parse("1992-05-10")));
            index++;
            empls.add(new Employee(2, "cy", "cy@qq.com", "F", DepartmentService.getOne("2"), dateFormat.parse("2000-04-13")));
            index++;
            empls.add(new Employee(3, "cxl", "cxl@qq.com", "F", DepartmentService.getOne("2"), dateFormat.parse("1993-05-16")));
            index++;

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> getAll() {

        List<Employee> employees = empls;

        return employees;
    }

    public int save(Employee employee) {
        employee.setId(++index);
        employee.getDepartment().setName(DepartmentService.getOne(employee.getDepartment().getId().toString()).getName());
        empls.add(employee);
        return 0;
    }
}
