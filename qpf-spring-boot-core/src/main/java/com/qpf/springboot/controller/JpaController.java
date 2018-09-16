package com.qpf.springboot.controller;

import com.qpf.springboot.bean.Company;
import com.qpf.springboot.dao.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class JpaController {
    @Autowired
    CompanyRepository companyRepository;

    @GetMapping("/test/getcompany/{id}")
    public Map<String, Object> getCompany(@PathVariable("id") Integer id) {
        Map<String, Object> map = new HashMap<>();

        Company company = companyRepository.findOne(id);

//        Company company = companyRepository.getOne(id);

        System.out.println("Company: " + company);

        map.put("company", company);

        return map;
    }

    @GetMapping("/test/company")
    public Map<String, Object> insertCompany(Company company) {
        Map<String, Object> map = new HashMap<>();

        Company company1 = companyRepository.save(company);

        System.out.println("company: " + company);

        map.put("company", company1);

        return map;
    }
}
