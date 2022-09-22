package com.zhichanzaixian.trademarkapi.security.hanlder;


import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author syl
 * @version 1.0.0
 * @date 2021/3/11 15:00
 * @description 登录成功处理
 */
@Component
public class DefaultAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    /**
    * token 过期时间
    */
//    @Value("${jwt.token.expired}")
//    private int jwtTokenExpired;
//
//    /**
//    * 刷新token 时间
//    */
//    @Value("${jwt.token.refresh.expired}")
//    private int jwtTokenRefreshExpired;
    
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        return Mono.defer(() -> Mono.just(webFilterExchange.getExchange().getResponse()).flatMap(response -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            String str= "{\"msg\":\"onAuthenticationSuccess\"}";
            DataBuffer dataBuffer = dataBufferFactory.wrap(str.getBytes());
            return response.writeWith(Mono.just(dataBuffer));
        }));
     }
}