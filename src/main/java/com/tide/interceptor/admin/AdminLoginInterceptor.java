package com.tide.interceptor.admin;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wengliemiao on 15/12/7.
 */
public class AdminLoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * 访问 action 前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String adminname = (String) request.getSession().getAttribute("adminname");

        System.out.println("admin login interceptor: "+adminname);

        if(null == adminname) { // 未登录
            request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
            return false;
        }
        return true;
    }

    /**
     * 访问action后，生成视图前
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 最后执行，通常用于释放资源，处理异常
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
