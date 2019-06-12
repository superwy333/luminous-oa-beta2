package cn.luminous.squab.config;

import cn.luminous.squab.filter.MyDisableUrlSessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyDisableUrlSessionFilter myDisableUrlSessionFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //用於客戶端第一次訪問時去掉URL中的jsessionid
        http.addFilterBefore(myDisableUrlSessionFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
