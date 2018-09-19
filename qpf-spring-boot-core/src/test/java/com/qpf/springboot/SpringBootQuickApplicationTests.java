package com.qpf.springboot;

import com.qpf.springboot.bean.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootQuickApplicationTests {

    @Autowired
    Persion persion;
    @Autowired
    private Dog dog;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ApplicationContext ioc;
    @Autowired
    private TempPlaceHolder tempPlaceHolder;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<Object, Employee> employeeRedisTemplate;

    @Test
    public void contextLoads() {
        System.out.println(persion);
    }

    @Test
    public void  testValue() {
        System.out.println(dog);
    }

    @Test
    public void  testImportResource() {
        System.out.println(ioc.containsBean("temp"));
    }

    @Test
    public void testPlaceHolder() {
        System.out.println(tempPlaceHolder);
    }

    @Test
    public void datasource() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
//        PreparedStatement statement = conn.prepareStatement("select * from dept;");
//        ResultSet resultSet = statement.executeQuery();
//        while (resultSet.next()) {
//            resultSet.getInt("");
//        }
    }

    @Test
    public void testRedis() {

        ValueOperations<String, String> strOps = stringRedisTemplate.opsForValue();
        strOps.set("msg", "hello");
        System.out.println(String.format("msg: %s", strOps.get("msg")));
        strOps.append("msg", " world");
        System.out.println(String.format("msg: %s", strOps.get("msg")));

        employeeRedisTemplate.opsForValue().set("emp-01", new Employee(1, "qpf", "qpf@qq.com", "M", new Department(), new Date()));
    }

}
