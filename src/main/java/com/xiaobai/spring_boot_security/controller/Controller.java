package com.xiaobai.spring_boot_security.controller;

import com.xiaobai.spring_boot_security.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
public class Controller {
//这是Spring Security配置的登录表单页面跳转url,login.html无法直接访问！
    @RequestMapping(value ="/login", method = RequestMethod.GET)
    public ModelAndView login(User user){
        log.info("User:" + user);
        ModelAndView modelAndView = new ModelAndView("login");//权限设置了login页面，是表示访问任何路径默认跳转该页面及该页面用于提交登录并验证，这里字符串里的跳转页原理是jsp加载而不是重定向，所以可以无验证到达该跳转页
        modelAndView.addObject("user",user);
        return modelAndView;
    }
//这里是按Spring Security配置的SpringMVC映射路径，同样无法直接访问html页面，需要SpringMVC这里进行映射转发
    @RequestMapping(value ="/login-error", method = RequestMethod.GET)
    public ModelAndView error(User user){
        log.info("User:" + user);
        ModelAndView modelAndView = new ModelAndView("error");//权限设置了login页面，是表示访问任何路径默认跳转该页面及该页面用于提交登录并验证，这里字符串里的跳转页原理是jsp加载而不是重定向，所以可以无验证到达该跳转页
        modelAndView.addObject("user",user);
        return modelAndView;
    }
//经测试，SpringBoot和Thymeleaf管理的页面，除了工程默认欢迎页面固定为index.html外，其他页面都不可直接抵达，只能通过Controller进行映射和跳转，因为默认把URL都交给了SpringMVC管理（拦截"/"，所有URL）
//这里不配置/index路径，访问工程根路径仍然可以到达index.html,但其他页面或http请求想直接访问index.html是做不到的，亲测！这是因为默认加载了SpringMVC管理所有url,除非在SpringMVC配置静态页面过滤
    @RequestMapping(value ="/index", method = RequestMethod.GET)
    public ModelAndView index(User user){
        log.info("User:" + user);
        ModelAndView modelAndView = new ModelAndView("index");//权限设置了login页面，是表示访问任何路径默认跳转该页面及该页面用于提交登录并验证，这里字符串里的跳转页原理是jsp加载而不是重定向，所以可以无验证到达该跳转页
        modelAndView.addObject("user",user);
        return modelAndView;
    }
//这是Spring Security配置的受保护页跳转url,同样无法直接访问其html页面
    @RequestMapping(value ="/user", method = RequestMethod.GET)
    public ModelAndView user(User user){
        log.info("User:" + user);
        ModelAndView modelAndView = new ModelAndView("user/index");//权限设置了login页面，是表示访问任何路径默认跳转该页面及该页面用于提交登录并验证，这里字符串里的跳转页原理是jsp加载而不是重定向，所以可以无验证到达该跳转页
        modelAndView.addObject("user",user);
        return modelAndView;
    }
}
