package com.qpf.advanced.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;

@Repository
public class TaskService {
    @Async
    public void asyncTask() {
        System.out.println("任务执行中......");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("任务执行完成....");
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void SchedulerTask() {
        System.out.println("do something: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
