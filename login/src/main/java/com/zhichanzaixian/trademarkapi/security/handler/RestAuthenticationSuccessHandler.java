package com.zhichanzaixian.trademarkapi.security.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhichanzaixian.trademarkapi.comm.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(RestAuthenticationSuccessHandler.class);

    @Autowired
    private ObjectMapper objectMapper;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        log.debug("onAuthenticationSuccess");
        log.info( "{} ,login success。",authentication.getName() );

        if (request.getSession() != null) {
            log.debug("GetMaxInactiveInterval:{} getId:{}" , request.getSession().getMaxInactiveInterval(),
                    request.getSession().getId());
        } else {
            log.debug("request.getSession() is null");
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Result.success(authentication.getPrincipal())));
        response.getWriter().flush();



    }





    private String getHeader(HttpServletRequest request) {
        StringBuilder headInfoStrBuffer = new StringBuilder();
        Enumeration<String> requestHeaderNames = request.getHeaderNames();
        while (requestHeaderNames.hasMoreElements()) {
            String headerName = requestHeaderNames.nextElement();
            String headValue = request.getHeader(headerName);
            headInfoStrBuffer.append(headerName).append(":").append(headValue).append(";");
        }
        return headInfoStrBuffer.toString();
    }





}
