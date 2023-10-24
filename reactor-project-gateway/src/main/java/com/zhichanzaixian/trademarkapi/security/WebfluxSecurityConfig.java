package com.zhichanzaixian.trademarkapi.security;

import com.zhichanzaixian.trademarkapi.security.hanlder.DefaultAccessDeniedHandler;
import com.zhichanzaixian.trademarkapi.security.hanlder.DefaultAuthenticationEntryPoint;
import com.zhichanzaixian.trademarkapi.security.hanlder.DefaultAuthenticationFailureHandler;
import com.zhichanzaixian.trademarkapi.security.hanlder.DefaultAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

import javax.annotation.Resource;

/**
 * @author syl
 * @version 1.0.0
 * @date 2021/3/11 10:56
 *
 * webflux security核心配置类
 */

@EnableWebFluxSecurity
public class WebfluxSecurityConfig {


    @Resource
    private DefaultAuthenticationSuccessHandler defaultAuthenticationSuccessHandler;

    @Resource
    private DefaultAuthenticationFailureHandler defaultAuthenticationFailureHandler;


    @Resource
    private DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    @Resource
    private DefaultAccessDeniedHandler defaultAccessDeniedHandler;



    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity
                // 登录认证处理
                // .authenticationManager(reactiveAuthenticationManager())
                // 请求拦截处理
                .authorizeExchange(exchange -> exchange
                        // .pathMatchers(
                        .pathMatchers(

                                //静态资源：
                                "/js/**", "/css/**", "/img/**", "/images/**", "/picture/**", "/fonts/**"
                                , "/*.ico",
                               //? , "/**/favicon.ico",
                                "/gateway-trademark/news/noLogin/**",
                                "/testfoo/**",
                                "/module/jslib/**",
                                "/script/**",

                                //   "/**",//close all
                                //spring boot 默认错误页面
                                "/error",
                                //swagger-ui:
                                "/v2/api-docs/**", "/swagger-resources/**", "/webjars/**", "/swagger-ui.html",
                                //单点登录
                                "/gateway-trademark/sso/**",
                                //  "/gateway-trademark/sso/mobile/login",
                                "/gateway-trademark/api/userAccount/register",
                                "/gateway-trademark/api/userAccount/register/checkPhone",
                                "/gateway-trademark/api/userAccount/register/checkUserName",
                                "/gateway-trademark/api/userAccount/resetPassword",


                                //** in medium not supposed !
                               // "/gateway-trademark/shop/**/noLogin/**",
                               // "/gateway-trademark/shop/**/nLgn/**",

                                "/gateway-trademark/api/noLogin/**",
                                "/gateway-trademark/api/*/noLogin/**",
                                "/gateway-trademark/api/*/*/noLogin/**",
                                "/gateway-trademark/api/*/*/*/noLogin/**",

                                //** in medium not supposed !  "/gateway-trademark/api/**/nLgn/**",
                                "/gateway-trademark/api/nLgn/**",
                                "/gateway-trademark/api/*/nLgn/**",
                                "/gateway-trademark/api/*/*/nLgn/**",
                                "/gateway-trademark/api/*/*/*/nLgn/**",


                                //websocket项目 微信配置错了：
                                "/gateway-trademark/gateway-trademark/msg/**",
                                "/gateway-trademark/websocket/**",




                                "/gateway-trademark/api/innerorder/docDownload/**",

                                "/actuator/health/**",

                                "/gateway-trademark/comm/**",
                                "/gateway-trademark/search/**",
                                "/gateway-trademark/management/**",

                                //微信和小程序
                                "/gateway-trademark/api/globalAlert/message",
                                "/gateway-trademark/api/userAccount/register/code2Session",
                                "/gateway-trademark/api/userAccount/register/checkWeChatJsCode",
                                "/gateway-trademark/api/userAccount/register/bindWeChat",
                                "/gateway-trademark/api/userAccount/register/wxQrCreate",

                                //Windows 客户端
                                "/gateway-trademark/api/userAccount/getMyAccountStatusByClientId",

                                //微信事件
                                "/gateway-trademark/api/weChatEvent/notify/**",
                                "/gateway-trademark/api/weComEvent/**"
                        ).permitAll()
                        // .pathMatchers("/**").permitAll()
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        // .anyExchange().access(defaultAuthorizationManager)
                        .anyExchange().authenticated()
                )
                //open remark:
                .formLogin()
                // 自定义处理
                .authenticationSuccessHandler(defaultAuthenticationSuccessHandler)
                .authenticationFailureHandler(defaultAuthenticationFailureHandler)
                //open-remark-end-2022年9月8日
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(defaultAccessDeniedHandler)
                .and()
                .csrf().disable()
                .formLogin().disable();



        return httpSecurity.build();
    }



    /**
     * BCrypt密码编码
     */
    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }



//    @Bean
//    public MapReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.withUsername("user")
//                .passwordEncoder(passwordEncoder::encode)
//                .password("password")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withUsername("admin")
//                .passwordEncoder(passwordEncoder::encode)
//                .password("password")
//                .roles("USER", "ADMIN")
//                .build();
//       // log.info("user: {}", user);
//      //  log.info("admin: {}", admin);
//        return new MapReactiveUserDetailsService(user, admin);
//    }


    /**
     * 注册用户信息验证管理器，可按需求添加多个按顺序执行
     */
//     @Bean
//     ReactiveAuthenticationManager reactiveAuthenticationManager() {
//            LinkedList<ReactiveAuthenticationManager> managers = new LinkedList<>();
//         managers.add(authentication -> {
//                    // 其他登陆方式 (比如手机号验证码登陆) 可在此设置不得抛出异常或者 Mono.error
//                    return Mono.empty();
//         });
//         // 必须放最后不然会优先使用用户名密码校验但是用户名密码不对时此 AuthenticationManager 会调用 Mono.error 造成后面的 AuthenticationManager 不生效
//         managers.add(new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsServiceImpl));
//         managers.add(tokenAuthenticationManager);
//         return new DelegatingReactiveAuthenticationManager(managers);
//     }



}