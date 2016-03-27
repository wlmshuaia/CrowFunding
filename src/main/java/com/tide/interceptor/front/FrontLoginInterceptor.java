package com.tide.interceptor.front;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前台登录判断
 * Created by wengliemiao on 16/3/14.
 */
public class FrontLoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * 访问 action 前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object oUser = request.getSession().getAttribute("userid");

        System.out.println("front login interceptor: " + oUser);

        if(null == oUser) { // 未登录
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx950c13ed59c4faab&redirect_uri=http://www.zm-college.com/front/Index/oauth2.do&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
            String sUrl = request.getRequestURL().toString();
            String sQuery = request.getQueryString();
            if(sQuery != null && !"".equals(sQuery)) {
                request.getSession().setAttribute("fromUrl", sUrl + "?" + sQuery);
            } else {
                request.getSession().setAttribute("fromUrl", sUrl);
            }

            response.sendRedirect(url);
            //request.getRequestDispatcher(url).forward(request, response);
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
