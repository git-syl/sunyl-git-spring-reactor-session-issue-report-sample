package com.zhichanzaixian.trademarkapi.filter;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.zhichanzaixian.trademarkapi.comm.result.CodeMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class RestAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(RestAuthenticationFilter.class);


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;


        try (InputStream is = request.getInputStream()) {
            DocumentContext context = JsonPath.parse(is);
            String username = context.read("$.username", String.class);
            String password = context.read("$.password", String.class);
            log.info("RestAuthenticationFilter::" + username);
            authRequest = new UsernamePasswordAuthenticationToken(username, password);
        } catch (IOException | PathNotFoundException e) {
            log.error("RestAuthenticationFilter", e);
            throw new InternalAuthenticationServiceException(CodeMsg.PARAM_ERROR.getMsg());
        }


        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }


}