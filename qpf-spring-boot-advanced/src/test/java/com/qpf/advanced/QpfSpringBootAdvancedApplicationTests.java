package com.qpf.advanced;

import com.qpf.advanced.bean.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QpfSpringBootAdvancedApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;
    @Test
    public void contextLoads() {
        stringRedisTemplate.opsForValue().append("msg", "hello");
        stringRedisTemplate.opsForValue().append("msg", "world");
        String msg = stringRedisTemplate.opsForValue().get("msg");
        System.out.println(msg);
    }

    @Test
    public void testRabbitmq() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 10001);
        map.put("msg", "send msg");

//        rabbitTemplate.convertAndSend("amq.direct", "queue.direct", map);
        rabbitTemplate.convertAndSend("amq.direct", "queue.direct", new Department(1, "AAA-a"));
        rabbitTemplate.convertAndSend("amq.direct", "queue.direct", new Department(2, "BBB-b"));
    }
    @Test
    public void testRabbitmqrecv() {
        Object o = rabbitTemplate.receiveAndConvert("queue.direct");
        System.out.println(o);
        System.out.println(o != null ? o.getClass() : "null");
    }

    @Test
    public void testAmqpAdmin() {
        amqpAdmin.declareExchange(new DirectExchange("adminDirect"));
        amqpAdmin.declareQueue(new Queue("queue.admin", true));
        amqpAdmin.declareBinding(new Binding("queue.admin", Binding.DestinationType.QUEUE, "adminDirect", "queue.admin", null));
    }

}
