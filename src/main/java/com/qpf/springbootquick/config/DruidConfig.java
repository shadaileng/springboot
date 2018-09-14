package com.qpf.springbootquick.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {
    @ConfigurationProperties(value = "spring.datasource")
    @Bean
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
//      参数设置com.alibaba.druid.support.http.ResourceServlet
        Map<String, String> initParam = new HashMap<>();
//      登陆用户
        initParam.put("loginUsername", "admin");
//      登陆密码
        initParam.put("loginPassword", "admin");
//      默认或者设为空,允许所有IP地址
        initParam.put("allow", "");
//        禁止访问的IP地址
//        initParam.put("deny", "");

        bean.setInitParameters(initParam);

        return bean;
    }
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        bean.setUrlPatterns(Arrays.asList("/test/*"));

        Map<String, String> initParam = new HashMap<>();
        initParam.put("exclusions", "/public/*,*.js,*.css,/druid/*,*.jsp,*.swf");
        bean.setInitParameters(initParam);

        return bean;
    }
}
