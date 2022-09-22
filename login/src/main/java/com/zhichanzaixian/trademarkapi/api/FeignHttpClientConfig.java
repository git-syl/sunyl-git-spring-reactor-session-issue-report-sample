package com.zhichanzaixian.trademarkapi.api;

import com.zhichanzaixian.trademarkapi.api.decoder.JacksonDecoder;
import com.zhichanzaixian.trademarkapi.api.decoder.JacksonEncoder;
import feign.Contract;
import feign.Logger;
import feign.QueryMapEncoder;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.querymap.BeanQueryMapEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Created by syl nerosyl@live.com on 2022/9/21
 *
 * @author syl
 */
public class FeignHttpClientConfig {

    @Value("${spring.profiles.active:prod}")
    private String profile;

    @Bean
    public Contract contract() {
        return new Contract.Default();
    }

    @Bean
    public QueryMapEncoder queryMapEncoder() {
        return new BeanQueryMapEncoder();
    }

    // @Bean
    public Logger logger() {

        String logName = "FeignHttpClient-default.log";

        return new Logger.JavaLogger(LoginClient.class).appendToFile(logName);

    }



    @Bean
    public Decoder decoder() {
        return new JacksonDecoder();
    }

    @Bean
    public Encoder encoder() {
        return new FormEncoder(new JacksonEncoder());
    }

}
