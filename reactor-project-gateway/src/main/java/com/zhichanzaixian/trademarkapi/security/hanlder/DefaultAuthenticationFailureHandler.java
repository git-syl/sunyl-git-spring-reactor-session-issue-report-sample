package com.zhichanzaixian.trademarkapi.security.hanlder;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author syl
 * @version 1.0.0
 * @date 2021/3/11 15:14
 * @description 登录失败处理
 */
@Component
public class DefaultAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        return Mono.defer(() -> Mono.just(webFilterExchange.getExchange()
                .getResponse()).flatMap(response -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            String resultVO = "";
            // 账号不存在
            if (exception instanceof UsernameNotFoundException) {
                resultVO = "{\"msg\":\"UsernameNotFoundException\"}";
                // 用户名或密码错误
            } else if (exception instanceof BadCredentialsException) {
                resultVO = "{\"msg\":\"BadCredentialsException\"}";
                // 账号已过期
            } else if (exception instanceof AccountExpiredException) {
                resultVO = "{\"msg\":\"AccountExpiredException\"}";
                // 账号已被锁定
            } else if (exception instanceof LockedException) {
                resultVO = "{\"msg\":\"LockedException\"}";
                // 用户凭证已失效
            } else if (exception instanceof CredentialsExpiredException) {
                resultVO  ="{\"msg\":\"CredentialsExpiredException\"}";
                // 账号已被禁用
            } else if (exception instanceof DisabledException) {
                resultVO = "{\"msg\":\"DisabledException\"}";
            }
            DataBuffer dataBuffer = dataBufferFactory.wrap(resultVO.getBytes());
            return response.writeWith(Mono.just(dataBuffer));
        }));
    }
}