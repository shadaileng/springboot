package com.qpf.springboot.mapper;

import com.qpf.springboot.bean.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {
    public Employee selectEmployeeById(Integer id);
    public int insertEmployee(Employee employee);
}
