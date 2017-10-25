package com.qf.config;

import com.qf.web.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by ios on 17/10/17.
 */
@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
//    /**
//     * 配置静态资源路径
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //配置静态访问资源，前面的是映射路径，后面的是资源存放路径，可以是file:/ios/Desktop本机地址
//        registry.addResourceHandler("/my/**").addResourceLocations("classpath:/my/");
//        super.addResourceHandlers(registry);
//    }
//
//    /**
//     * 页面跳转
//     */
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/toLogin").setViewName("login");
//        super.addViewControllers(registry);
//    }

    //这样可以防止在loginIntercepror中的service报空指针异常
    @Bean
    HandlerInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    /**
     * 拦截器
     * addPathPatherns 用于添加拦截规则
     * excludePathPatterns 用于排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns("/toLogin", "/login");
        super.addInterceptors(registry);
    }
}
