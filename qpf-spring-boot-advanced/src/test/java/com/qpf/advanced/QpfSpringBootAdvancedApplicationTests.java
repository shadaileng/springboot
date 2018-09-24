package com.qpf.advanced;

import com.qpf.advanced.bean.Article;
import com.qpf.advanced.bean.Department;
import com.qpf.advanced.repository.ArticleRepository;
import com.qpf.advanced.services.TaskService;
import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
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

    @Autowired
    private JestClient jestClient;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private JavaMailSenderImpl mailSender;

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

    @Test
    public void testElasticsearchIndex() throws IOException {
        Article article = new Article(1, "qpf", "news", "good news");

        Index index = new Index.Builder(article).index("qpf").type("article").build();

        DocumentResult result = jestClient.execute(index);
        System.out.println("result " + result);
    }
    @Test
    public void testElasticsearchSearch() throws IOException {
        String json = "{'query': {'match': {'content': 'news'}}}";
        Search search = new Search.Builder(json).addIndex("qpf").addType("article").build();

        SearchResult result = jestClient.execute(search);
        System.out.println("result " + result.getJsonString());
    }

    @Test
    public void testElasticsearchRepository() {
        articleRepository.index(new Article(2, "qpf", "weather", "sunny"));
//        QueryBuilder queryBuilder = new QueryBuilder();
//        articleRepository.search();
    }

    @Test
    public void testTask() {
        System.out.println("begin");
        taskService.asyncTask();
        System.out.println("end");
    }

    @Test
    public void sendSimpleMail() {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("qpf0510@163.com");
        mail.setFrom("qpf0510@qq.com");
        mail.setSubject("通知·开会");
        mail.setText("今晚 7:30 开会");
        mailSender.send(mail);
        System.out.println("发送完成....");
    }

    @Test
    public void sendMimeMaile() throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo("qpf0510@163.com");
        helper.setSubject("通知·开会");
        helper.setFrom("qpf0510@qq.com");
        helper.setText("<h1>今晚<b style='color:red;'>7:30</b>开会</h1>", true);

        File[] files = new File("/home/shadaileng/下载/images/tumblr/tumblr_001").listFiles();

        for (File file : files) {
            if (file.isFile()) {
                helper.addAttachment(file.getName(), file);
                System.out.println("attache file: " + file.getName());
            }
        }

        mailSender.send(mimeMessage);
    }
}
