package com.zhichanzaixian.trademarkapi.security;

import com.zhichanzaixian.trademarkapi.filter.*;
import com.zhichanzaixian.trademarkapi.security.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.*;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @Autowired
    RestAuthenticationFailureHandler restAuthenticationFailureHandler;

    @Autowired
    RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler;

    @Autowired
    RestLogoutSuccessHandler restLogoutSuccessHandler;

    @Autowired
    CustomExpiredSessionStrategy customExpiredSessionStrategy;

    @Autowired
    UnauthorizedEntryPoint unauthorizedEntryPoint;





    @Resource(name = "springSecurityUserDetailsServiceImpl")
    UserDetailsService userDetailsService;

    @Autowired
    SpringSessionBackedSessionRegistry<? extends Session> sessionRegistry;

    @Autowired(required = false)
    CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy;

    @Value("${spring.profiles.active:prod}")
    private String profile;




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.authorizeRequests().antMatchers("/error").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin().disable();
        http.logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID", "X-Auth-Token")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutSuccessHandler(restLogoutSuccessHandler);


        http.sessionManagement()
                .maximumSessions("prod".equals(profile) ? 10 : 2)
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(sessionRegistry)
                .expiredSessionStrategy(customExpiredSessionStrategy);



        http.exceptionHandling()
                // 已登入用户的权限错误
                .accessDeniedHandler(restAuthenticationAccessDeniedHandler)
                // 未登入用户的权限错误
                .authenticationEntryPoint(unauthorizedEntryPoint);


        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }


    @Bean
    public SpringSessionBackedSessionRegistry<? extends Session> sessionRegistry(FindByIndexNameSessionRepository<? extends Session> sessionRepository) {
        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());

    }





    //TODO:仅测试使用
    //CORS配置
    // @Profile("dev")
    //@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "X-Auth-Token"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Autowired
    @Qualifier("compositeSessionAuthenticationStrategy")
    SessionAuthenticationStrategy sessionAuthenticationStrategy;

    //debug path:ConcurrentSessionControlAuthenticationStrategy
    private RestAuthenticationFilter customAuthenticationFilter() throws Exception {
        RestAuthenticationFilter filter = new RestAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
        filter.setAuthenticationManager(this.authenticationManager());
        filter.setFilterProcessesUrl("/login");
        //    filter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        filter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        return filter;
    }





    @Bean
    public CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy(ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy) {
        return new CompositeSessionAuthenticationStrategy(Arrays.asList(
                concurrentSessionControlAuthenticationStrategy,
               new ChangeSessionIdAuthenticationStrategy(),
                new RegisterSessionAuthenticationStrategy(this.sessionRegistry)));
    }



    @Bean
    @Profile({"test", "testprod","dev"})
    public ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy() {
        ConcurrentSessionControlAuthenticationStrategy controlAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(this.sessionRegistry);
        //会与其他项目的数值累加?
        controlAuthenticationStrategy.setMaximumSessions(2);
        //true,阻止登录。
        //false被踢掉
        controlAuthenticationStrategy.setExceptionIfMaximumExceeded(false);
        return controlAuthenticationStrategy;
    }

    @Bean
    @Profile("prod")
    public ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy2() {
        ConcurrentSessionControlAuthenticationStrategy controlAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(this.sessionRegistry);
        controlAuthenticationStrategy.setMaximumSessions(10);
        controlAuthenticationStrategy.setExceptionIfMaximumExceeded(false);
        return controlAuthenticationStrategy;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyPassWordEncoder();
    }


}