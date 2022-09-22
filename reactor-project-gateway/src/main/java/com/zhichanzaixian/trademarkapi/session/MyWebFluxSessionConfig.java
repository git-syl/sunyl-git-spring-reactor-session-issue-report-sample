package com.zhichanzaixian.trademarkapi.session;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

/**
 * @author syl  Date: 2022/08/31 Email:nerosyl@live.com
 */
@Configuration
@EnableRedisWebSession(maxInactiveIntervalInSeconds = 20)
public class MyWebFluxSessionConfig {
    public final static String X_AUTH_TOKEN = "X-AUTH-TOKEN";

    @Bean
    public WebSessionIdResolver webSessionIdResolver() {
        HeaderWebSessionIdResolver resolver = new HeaderWebSessionIdResolver();
        resolver.setHeaderName(X_AUTH_TOKEN);
        return resolver;
    }

//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory();
//    }

//    @Bean
//    public ServerSecurityContextRepository serverSecurityContextRepository() {
//        return new WebSessionServerSecurityContextRepository();
//    }
//
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }
}
