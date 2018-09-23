package com.qpf.springboot.dao;

import com.qpf.springboot.bean.Department;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.*;

@CacheConfig(cacheNames = "dept", cacheManager = "departmentRedisManager")
@Repository
public class DepartmentService {
    private static Map<Integer, Department> map = new HashMap<Integer, Department>();
    static {
        map.put(1, new Department(1, "A-a"));
        map.put(2, new Department(2, "B-b"));
    }
    public static Collection<Department> getAllDepartment() {
        Collection<Department> deps = map.values();
        return deps;
    }
    public Collection<Department> getAll() {
        return getAllDepartment();
    }
    public static Department getDepartment(int id) {
        return map.get(id);
    }
    @Cacheable(key = "#index")
    public Department getOne(int index) {
        return map.get(index);
    }
}
