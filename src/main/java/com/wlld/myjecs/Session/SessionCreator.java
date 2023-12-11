package com.wlld.myjecs.Session;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @param
 * @DATA
 * @Author LiDaPeng
 * @Description
 */
public class SessionCreator implements HandlerInterceptor {//会话

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!request.getMethod().equals("OPTIONS")) {
            String tokenId = request.getHeader("tokenID");
            if (tokenId == null) {
                long id = WlldSession.getSESSION().createSession();
                response.setHeader("tokenID", String.valueOf(id));
            } else {
                if (!WlldSession.getSESSION().isLife(tokenId)) {//tokenId已失效
                    long id = WlldSession.getSESSION().createSession();
                    response.setHeader("tokenID", String.valueOf(id));
                } else {//sessionID未失效
                    response.setHeader("tokenID", tokenId);
                }
            }
        }
        response.setHeader("Access-Control-Expose-Headers", "tokenID");
        return true;
    }
}
