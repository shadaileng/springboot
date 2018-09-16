package com.qpf.springbootquick.bean;

import javax.persistence.*;

// 指定实体类
@Entity
// 指定对应数据表,默认类名首字母小写
@Table(name = "company")
public class Company {
    // 主键
    @Id
    // 自增长
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 指定字段名和长度
    @Column(name = "name", length = 255)
    private String name;
    // 默认属性名就是字段名
    @Column
    private Integer status;

    public Company(){}

    public Company(Integer id, String name, Integer status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
