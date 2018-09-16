package com.qpf.springbootquick.dao;

import com.qpf.springbootquick.bean.Company;
import org.springframework.data.jpa.repository.JpaRepository;

// 继承JpaRepository类,范型指定操作类的类型,主键类型
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
