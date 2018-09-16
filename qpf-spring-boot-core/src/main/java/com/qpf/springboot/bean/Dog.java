package com.qpf.springboot.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Dog {
    @Value("${dog.name}")
    private String name;
    @Value("#{2 + 2}")
    private Integer age;
    @Value("true")
    private Boolean isHungury;

    public Dog(){}

    public Dog(String name, Integer age, Boolean isHungury) {
        this.name = name;
        this.age = age;
        this.isHungury = isHungury;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getHungury() {
        return isHungury;
    }

    public void setHungury(Boolean hungury) {
        isHungury = hungury;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isHungury=" + isHungury +
                '}';
    }
}
