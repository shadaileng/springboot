package com.qpf.springbootquick;

import com.qpf.springbootquick.bean.Dog;
import com.qpf.springbootquick.bean.Persion;
import com.qpf.springbootquick.bean.Temp;
import com.qpf.springbootquick.bean.TempPlaceHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootQuickApplicationTests {

    @Autowired
    Persion persion;
    @Autowired
    private Dog dog;

    @Autowired
    private ApplicationContext ioc;
    @Autowired
    private TempPlaceHolder tempPlaceHolder;

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


}
