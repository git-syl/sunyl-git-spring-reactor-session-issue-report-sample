package com.zhichanzaixian.trademarkapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * test version
 */
@Slf4j
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

//    @Bean
//    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
//        return new Jackson2JsonRedisSerializer<>(Object.class);
//    }



    @Bean
    public RouteLocator testRouteLocator(RouteLocatorBuilder builder) {
        // 关键代码下面几行
        return builder.routes()
                .route("test_rewritepath_route", r ->
                        r.path("/testfoo/**").filters(f -> f.rewritePath("/testfoo/(?<segment>.*)", "/$\\{segment}"))
                                .uri("http://www.baidu.com"))
                .build();
    }




}
