package com.qpf.springbootquick.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:temp.properties"})
@ConfigurationProperties(prefix = "temp")
public class TempPlaceHolder {
    private String name;
    private Integer age;
    private Boolean isOk;
    private String randomStr;

    public TempPlaceHolder(){}

    public TempPlaceHolder(String name, Integer age, Boolean isOk, String randomStr) {
        this.name = name;
        this.age = age;
        this.isOk = isOk;
        this.randomStr = randomStr;
    }

    @Override
    public String toString() {
        return "Temp{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isOk=" + isOk +
                ", randomStr='" + randomStr + '\'' +
                '}';
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

    public Boolean getOk() {
        return isOk;
    }

    public void setOk(Boolean ok) {
        isOk = ok;
    }

    public String getRandomStr() {
        return randomStr;
    }

    public void setRandomStr(String randomStr) {
        this.randomStr = randomStr;
    }
}
