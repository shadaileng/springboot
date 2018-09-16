package com.qpf.springboot.dao;

import com.qpf.springboot.bean.Company;
import org.springframework.data.jpa.repository.JpaRepository;

// 继承JpaRepository类,范型指定操作类的类型,主键类型
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
