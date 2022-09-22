package com.zhichanzaixian.trademarkapi.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;

import java.util.concurrent.*;

/**
 * @author syl  Date: 2021/02/04 Email:nerosyl@live.com
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig {




    @Bean(name = "myExecutor")
    public Executor threadPoolTaskExecutor() {

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("AsyncConfigurerSupport-pool-%d").build();
        int processors = Runtime.getRuntime().availableProcessors();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(processors, 2 * processors + 1,
                60L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(), namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());

        return new DelegatingSecurityContextExecutorService(threadPoolExecutor);
    }




}