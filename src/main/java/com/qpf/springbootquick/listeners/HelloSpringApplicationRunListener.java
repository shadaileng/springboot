package com.qpf.springbootquick.listeners;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;

public class HelloSpringApplicationRunListener implements SpringApplicationRunListener {

    public HelloSpringApplicationRunListener(SpringApplication application, String[] args){
        System.out.println("HelloSpringApplicationRunListener--------------------");
        System.out.println("application: " + application);
        System.out.println("args: " + Arrays.asList(args));
        System.out.println("--------------------HelloSpringApplicationRunListener");
    }

    @Override
    public void starting() {
        System.out.println("SpringApplicationRunListener---- starting----");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("SpringApplicationRunListener---- environmentPrepared----" + environment.getSystemProperties().get("os.name"));
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener---- contextPrepared----");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener---- contextLoaded----");
    }

    @Override
    public void finished(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("SpringApplicationRunListener---- finished----");
    }
}
