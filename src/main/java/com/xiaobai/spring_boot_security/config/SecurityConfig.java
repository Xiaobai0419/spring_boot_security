package com.xiaobai.spring_boot_security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .antMatchers("/css/**", "/index").permitAll()
                .antMatchers("/user/**").hasRole("USER")//经测试，这个受保护页如果发现登录用户没有该权限，会自动跳转到下面配置的failureUrl页
                .and()
                .formLogin()
                .loginPage("/login").failureUrl("/login-error");//这是设置登录表单页面！经测试，默认跳转到工程根路径，默认是index.html页面，无论SpringMVC是否配置任何URL
    }//经测试，工程根路径即首页，默认可无需任何登录验证访问，且默认为登录成功后的跳转页面

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication().passwordEncoder((NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance())//这里的意思是将页面传递的密码不加密，与这里的用户比较，不设置会报异常：There is no PasswordEncoder mapped for the id "null"，查了下发现是spring security 版本在5.0后就要加个PasswordEncoder了
                .withUser("user").password("password").roles("USER");
    }//如果是BCrypt加密，一般应用于配置数据库UserDetailsService,在数据库中存的密码也是要经过这个加密的才能匹配上
}
//参考：https://www.jianshu.com/p/9e7792d767b2 https://blog.csdn.net/dream_an/article/details/79381459