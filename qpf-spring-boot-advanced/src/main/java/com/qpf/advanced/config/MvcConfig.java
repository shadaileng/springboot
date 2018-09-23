package com.qpf.advanced.config;

import com.qpf.advanced.components.InitHandlerInterceptor;
import com.qpf.advanced.components.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter () {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                super.addViewControllers(registry);
                registry.addViewController("/").setViewName("index");
                registry.addViewController("/index.html").setViewName("index");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                super.addInterceptors(registry);
                registry.addInterceptor(new InitHandlerInterceptor()).addPathPatterns("/**");
            }
        };

        return adapter;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }


}
