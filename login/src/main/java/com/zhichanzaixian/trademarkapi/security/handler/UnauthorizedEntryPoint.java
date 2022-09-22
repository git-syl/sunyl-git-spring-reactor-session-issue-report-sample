package com.zhichanzaixian.trademarkapi.security.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhichanzaixian.trademarkapi.comm.result.CodeMsg;
import com.zhichanzaixian.trademarkapi.comm.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

//By default, if the login fails, it will jump to the page, which is customized here. At the same time,
// it will determine whether the ajax request is made or not. If the ajax request is made, it will return json
/**
 * @author syl  Date: 2019/5/10 Email:nerosyl@live.com
 */
@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(UnauthorizedEntryPoint.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {




        log.info("[BLOCK THIS URL ->][{}],[IP->]{},[Case By->]{}",request.getRequestURL(),request.getHeader("X-Real-IP")==null?request.getRemoteAddr():request.getHeader("X-Real-IP"),authException.getMessage());

        response.setContentType("application/json");
        response.setStatus(401);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Result.error(CodeMsg.NOT_LOGIN)));
        response.getWriter().flush();

//            return;
//        if (isAjaxRequest(request)){
//        }
//
//        response.sendRedirect("/home/myloginPage");


    }
//
    private static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return "XMLHttpRequest" .equals(ajaxFlag);
    }
}