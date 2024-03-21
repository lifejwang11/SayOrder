package com.wlld.myjecs.access;

import com.alibaba.fastjson.JSON;
import com.wlld.myjecs.config.ErrorCode;
import com.wlld.myjecs.entity.mes.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * @param
 * @DATA
 * @Author LiDaPeng
 * @Description
 */
@Slf4j
public class SessionCreator implements HandlerInterceptor {//会话

    private static ThreadLocal<Integer> adminHolder = new ThreadLocal<>();

    public static void setAdmin(Integer user) {
        adminHolder.set(user);
    }

    public static Integer getAdmin() {
        return adminHolder.get();
    }

    /**
     * session 拦截器
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {


        //判断用户是否登录
        HttpSession session = request.getSession();
        log.debug("Session preHandle " + request.getRequestURI() + "sessionId={}", session.getId());

        // 若存在，则放行
        if (Objects.nonNull(session.getAttribute("adminId"))) {
            adminHolder.set((Integer) session.getAttribute("adminId"));
            return true;
        }
        //拦截住，并给前端页面返回未登录信息，以输出流的方式，json格式返回
        response.setContentType("application/json; charset=utf-8");

        Response res = new Response();
        res.setError(ErrorCode.InvalidLogin.getError());
        res.setErrorMessage(ErrorCode.InvalidLogin.getErrorMessage());
        // 1、使用Fastjson（默认过滤null值）
        response.getWriter()
                .write(JSON.toJSONString(res));
        return false;

    }
}
