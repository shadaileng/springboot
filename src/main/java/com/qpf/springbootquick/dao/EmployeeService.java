package com.qpf.springbootquick.dao;

import com.qpf.springbootquick.bean.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class EmployeeService {

    private static Map<Integer, Employee> map = new HashMap<Integer, Employee>();
    private static int index = 0;

    @Autowired
    private DepartmentService departmentService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    static {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            index = 0;
            map.put(index, new Employee(index, "qpf", "qpf@qq.com", "M", DepartmentService.getOne("1"), dateFormat.parse("1992-05-10")));
            index++;
            map.put(index, new Employee(index, "qpf", "qpf@qq.com", "M", DepartmentService.getOne("1"), dateFormat.parse("1992-05-10")));
            index++;
            map.put(index, new Employee(index, "qpf", "qpf@qq.com", "M", DepartmentService.getOne("1"), dateFormat.parse("1992-05-10")));
            index++;

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Collection<Employee> getAll() {

        Collection<Employee> employees = map.values();

        return employees;
    }

    public int save(Employee employee) {
        employee.setId(++index);
        employee.getDepartment().setName(DepartmentService.getOne(employee.getDepartment().getId().toString()).getName());
        map.put(index, employee);
        return 0;
    }

    public Employee getOne(Integer id) {
        return map.get(id);
    }

    public int editOne(Employee employee) {
        employee.getDepartment().setName(DepartmentService.getOne(employee.getDepartment().getId().toString()).getName());
        map.put(employee.getId(), employee);
        return 0;
    }

    public int delOne(Integer id) {
        logger.info("id: " + id);
        if (map.containsKey(id)) {
            map.remove(id);
        } else{
            logger.info("该员工不在记录中");
        }
        return 0;
    }
}
