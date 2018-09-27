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
                .withUser("qpf@qq.com").password("1").roles("VIP1", "VIP2", "VIP3")
                .and()
                .withUser("sdl@qq.com").password("1").roles("VIP1", "VIP2")
                .and()
                .withUser("shadaileng@qq.com").password("1").roles("VIP1", "VIP3")
                .and()
                .withUser("qpf0510@qq.com").password("1").roles("VIP1", "VIP3");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level/level1/**").hasRole("VIP1")
                .antMatchers("/level/level2/**").hasRole("VIP2")
                .antMatchers("/level/level3/**").hasRole("VIP3");
        http.formLogin().loginPage("/userlogin").usernameParameter("email").passwordParameter("password");
        http.logout().logoutSuccessUrl("/");
        http.rememberMe().rememberMeParameter("remember-me");
    }
}
