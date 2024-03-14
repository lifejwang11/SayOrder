package com.wlld.myjecs.config;

import com.wlld.myjecs.access.SessionCreator;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html", "doc.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/login.html").setViewName("index");
        registry.addViewController("/api/to_admin").setViewName("admin");
        registry.addViewController("/api/to_worker").setViewName("worker");

    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionCreator())
                .addPathPatterns("/**")
                // 排除的请求路径
                .excludePathPatterns(
                        "/login.html", "/index.html", "/",
                        "/ai/talk", "/ai/myTalk", "/admin/register", "/admin/login",
                        "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }

}
