package com.qpf.advanced.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("qpf").password("123456").roles("VIP1", "VIP2", "VIP3")
                .and()
                .withUser("sdl").password("123456").roles("VIP1", "VIP2")
                .and()
                .withUser("shadaileng").password("123456").roles("VIP1", "VIP3");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level/level1/**").hasRole("VIP1")
                .antMatchers("/level/level2/**").hasRole("VIP2")
                .antMatchers("/level/level3/**").hasRole("VIP3");
        http.formLogin();
        http.logout();
    }
}
