package com.zhichanzaixian.trademarkapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

import java.util.concurrent.Executor;

/**
 * @author syl  Date: 2020/6/17 Email:nerosyl@live.com
 *
 * 紧急修复 发送通知错误的bug todotest
 */
@Configuration
public class AsyncConfig extends AsyncConfigurerSupport {


    @Qualifier("myExecutor")
    @Autowired
    Executor executor;

    /**
     *  // @Aynsc 线程获取不到User
     */
    @Override
    public Executor getAsyncExecutor() {
        return executor;
    }

}