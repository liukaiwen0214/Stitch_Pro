package com.lucky.config;

import com.lucky.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/UserAuth/login",// 排除登录接口
                        "/LoginPage.jsp",// 排除登录页面
                        "/static/**",// 排除静态资源
                        "/MainMenu"// 排除 Star_Home 接口
                );
    }
}