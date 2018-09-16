package com.qpf.springbootquick.mapper;

import com.qpf.springbootquick.bean.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {
    public Employee selectEmployeeById(Integer id);
    public int insertEmployee(Employee employee);
}
