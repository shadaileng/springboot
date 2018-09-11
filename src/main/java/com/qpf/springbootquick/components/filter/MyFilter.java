package com.qpf.springbootquick.components.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("init MyFilter ....");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("MyFilter check ....");
    }

    @Override
    public void destroy() {
        logger.info("MyFilter destroy ....");
    }
}
