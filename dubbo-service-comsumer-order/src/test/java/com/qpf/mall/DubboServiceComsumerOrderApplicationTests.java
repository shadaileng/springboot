package com.qpf.mall;

import mall.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DubboServiceComsumerOrderApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    public void contextLoads() {
        String ticket = orderService.buyTicket("1002");
        System.out.println(ticket);
    }

}
