package com.qpf.springbootquick.mapper;

import com.qpf.springbootquick.bean.Department;
import org.apache.ibatis.annotations.*;
@Mapper
public interface DepartmentMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into dept(name) values(#{name})")
    public int insertDepartment(Department department);
    @Delete("delete from dept where id = #{id}")
    public int deleteDepartmentById(Integer id);
    @Update("update dept set name = #{name} where id = #{id}")
    public int updateDepartmentById(Integer id);
    @Select("Select * from dept where id = #{id}")
    public Department selectDepartmentById(Integer id);
}
