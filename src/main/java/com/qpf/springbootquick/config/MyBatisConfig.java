package com.qpf.springbootquick.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                // 驼峰法和下划线对应
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
