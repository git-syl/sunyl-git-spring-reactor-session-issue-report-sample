package com.zhichanzaixian.trademarkapi.security.hanlder;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhichanzaixian.trademarkapi.result.Result;
import com.zhichanzaixian.trademarkapi.result.ResultOld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.nio.charset.Charset;

/**
 * @author syl
 * @version 1.0.0
 * @date 2021/3/11 15:17
 * @description 未认证处理
 */
@Component
public class DefaultAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return Mono.defer(() -> Mono.just(exchange.getResponse())).flatMap(response -> {  
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                DataBufferFactory dataBufferFactory = response.bufferFactory();

            String result = "";
            try {
                result = objectMapper.writeValueAsString( ResultOld.error("4004","登录状态失效，请重新登录。g"));
            } catch (JsonProcessingException ignored) {

            }
            DataBuffer buffer = dataBufferFactory.wrap(result.getBytes(
                                    Charset.defaultCharset()));
                return response.writeWith(Mono.just(buffer));
        });
    }
}