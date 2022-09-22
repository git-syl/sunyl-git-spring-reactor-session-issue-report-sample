package com.zhichanzaixian.trademarkapi.security.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhichanzaixian.trademarkapi.comm.result.CodeMsg;
import com.zhichanzaixian.trademarkapi.comm.result.Result;
import com.zhichanzaixian.trademarkapi.exception.WechatAppLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author syl  Date: 2019/5/9 Email:nerosyl@live.com
 */
@Component
@Slf4j
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    ObjectMapper objectMapper;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.info("onAuthenticationFailure", exception);


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (exception instanceof BadCredentialsException) {
            response.getWriter().write(objectMapper.writeValueAsString(Result.error(CodeMsg.PASSWORD_ERROR.findArgsObject(exception.getMessage()))));
        } else if (exception instanceof SessionAuthenticationException) {
            response.getWriter().write(objectMapper.writeValueAsString(Result.error(CodeMsg.ACCOUNT_LIMIT.findArgsObject("多处登录已超过上线！须等待其他用户退出登录。 若不是您本人登录 请尽快修改登录密码。"))));
        } else if (exception instanceof WechatAppLoginException) {
            response.setStatus(449);
            response.getWriter().write(objectMapper.writeValueAsString(Result.error(CodeMsg.ACCOUNT_LIMIT.findArgsObject(exception.getMessage()))));
        } else {
            response.getWriter().write(objectMapper.writeValueAsString(Result.error(CodeMsg.ACCOUNT_LIMIT.findArgsObject(exception.getMessage()))));
        }
        response.getWriter().flush();
    }
}
