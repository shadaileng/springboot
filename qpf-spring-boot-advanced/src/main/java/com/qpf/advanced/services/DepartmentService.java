package com.qpf.advanced.services;

import com.qpf.advanced.bean.Department;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@CacheConfig(cacheNames = "dept", cacheManager = "departmentRedisCacheManager")
@Repository
public class DepartmentService {
    private static Map<Integer, Department> departments = new HashMap<Integer, Department>();

    static {
        departments.put(1, new Department(1, "开发部"));
        departments.put(2, new Department(2, "测试部"));
    }
    @Cacheable(key = "#id")
    public static Department getDepartment(int id) {
        return departments.get(id);
    }
    public static Collection<Department> getDepartments() {
        return departments.values();
    }

    @RabbitListener(queues = "queue.direct")
    public void recviceDepartment(Department department) {
        System.out.println("recv: " + department);
    }
}
