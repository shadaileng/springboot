package com.qpf.springbootquick.config;

import com.qpf.springbootquick.components.filter.MyFilter;
import com.qpf.springbootquick.components.listener.MyListener;
import com.qpf.springbootquick.components.servlet.MyServlet;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextListener;
import java.util.Arrays;

@Configuration
public class MyServerConfig {
    @Bean
    public EmbeddedServletContainerCustomizer coustomeServerConfig() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.setPort(8081);
            }
        };
    }

    @Bean
    public ServletRegistrationBean registrationServlet() {
        return new ServletRegistrationBean(new MyServlet(), "/test/myServlet");
    }
    @Bean
    public FilterRegistrationBean registrationFilter() {
        FilterRegistrationBean registrationFilter = new FilterRegistrationBean(new MyFilter());
        registrationFilter.setUrlPatterns(Arrays.asList("/test/myServlet"));
        return registrationFilter;
    }
    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> registrationServletListener() {
        ServletListenerRegistrationBean<ServletContextListener> registrationServletListener = new ServletListenerRegistrationBean<ServletContextListener>(new MyListener());
        return registrationServletListener;
    }
}
