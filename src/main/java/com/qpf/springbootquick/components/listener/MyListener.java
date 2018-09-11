package com.qpf.springbootquick.components.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("init MyListener ....");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("destroy MyListener ....");
    }
}
