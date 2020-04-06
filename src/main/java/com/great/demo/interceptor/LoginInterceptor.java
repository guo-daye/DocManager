package com.great.demo.interceptor;

import com.great.demo.entity.TB_USER;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //获得请求路径的uri
        request.setCharacterEncoding("UTF-8");
        String uri = request.getRequestURI();
        System.out.println("postUrl -->"+uri);

        //判断路径是否是登陆还是登陆验证，是这两者之一执行controller中定义的方法
        if(uri.endsWith("/userLogin")||uri.endsWith("/login")||uri.endsWith("/index")||uri.endsWith("/fileList")||uri.endsWith("/getVerifyCode")||uri.endsWith("/addUser")||uri.endsWith("/judgeId")||uri.endsWith("/judgeUser")){
            System.out.println(">>>不需要执行登陆判断<<<");
            return true;
        }
        TB_USER tb_user = (TB_USER)request.getSession().getAttribute("tb_user");
        //其他情况判断session中是否有key，有的话继续用户的操作
        if(tb_user!=null){
            System.out.println(">>>用户已登录，可放行<<<");
            return true;
        }
        System.out.println(">>>未登录，禁止访问<<<");
        response.sendRedirect(request.getContextPath()+"/web/url/index");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }
}
