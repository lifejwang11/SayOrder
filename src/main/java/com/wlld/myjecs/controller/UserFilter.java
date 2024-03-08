package com.wlld.myjecs.controller;

import com.alibaba.fastjson.JSON;
import com.wlld.myjecs.Session.WlldSession;
import com.wlld.myjecs.config.ErrorCode;
import com.wlld.myjecs.mesEntity.Response;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @param
 * @DATA
 * @Author LiDaPeng
 * @Description
 */
public class UserFilter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {

            if (((HandlerMethod) handler).getBeanType() == IndexController.class) {
                return true;
            }

            if (WlldSession.getSESSION().getValue(response, "myID") != null) {
                return true;
            } else {
                PrintWriter printWriter = response.getWriter();
                Response res = new Response();
                res.setError(ErrorCode.InvalidLogin.getError());
                res.setErrorMessage(ErrorCode.InvalidLogin.getErrorMessage());
                printWriter.print(JSON.toJSONString(res));
                return false;
            }
        }
        return true;


    }
}
