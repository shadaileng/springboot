package com.qpf.springbootquick;

import com.qpf.springbootquick.bean.Persion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootQuickApplicationTests {

    @Autowired
    Persion persion;

    @Test
    public void contextLoads() {
        System.out.println(persion);
    }

}
