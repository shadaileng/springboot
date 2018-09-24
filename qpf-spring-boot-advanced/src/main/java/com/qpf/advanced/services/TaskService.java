package com.qpf.advanced.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

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

    @Scheduled(cron = "10/3 * * * * *")
    public void SchedulerTask() {
        System.out.println("do something");
    }
}
