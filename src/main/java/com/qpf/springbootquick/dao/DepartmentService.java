package com.qpf.springbootquick.dao;

import com.qpf.springbootquick.bean.Department;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DepartmentService {
    private static Map<String, Department> map = new HashMap<String, Department>();
    static {
        map.put("1", new Department(1, "A-a"));
        map.put("2", new Department(2, "B-b"));
    }
    public static Collection<Department> getAllDepartment() {
        Collection<Department> deps = map.values();
        return deps;
    }
    public Collection<Department> getAll() {
        return getAllDepartment();
    }
    public static Department getOne(String index) {
        return map.get(index);
    }
}
