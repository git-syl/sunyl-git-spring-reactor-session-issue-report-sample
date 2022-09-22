package com.zhichanzaixian.trademarkapi.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhichanzaixian.trademarkapi.comm.result.CodeMsg;
import com.zhichanzaixian.trademarkapi.comm.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author syl  Date: 2019/11/12 Email:nerosyl@live.com
 */
@Component
public class RestSessionAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json");
       // response.setStatus(401);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Result.error(CodeMsg.EMPTY_PAY_PASSWORD.findArgsObject("onAuthenticationFailure。"))));
        response.getWriter().flush();
    }
}
