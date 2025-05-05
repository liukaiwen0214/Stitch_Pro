package com.lucky.interceptor;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, @Nonnull HttpServletResponse response,  @Nonnull Object handler) throws Exception {
        logger.info("PreHandle method is called for URL: {}", request.getRequestURL());
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        // 放行登录相关请求和公共API
        if(uri.contains("/") || uri.contains("/public/")) {
            return true;
        }

        // 检查登录状态
        if (session.getAttribute("user") == null) {
            // 判断是否为Ajax请求
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"code\":401, \"message\":\"未登录\"}");
            } else {
                response.sendRedirect("/LoginPage.jsp");
            }
            return false;
        }
        return true;
    }
}
