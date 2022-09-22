package com.zhichanzaixian.trademarkapi.api;

import com.zhichanzaixian.trademarkapi.api.request.LoginRequest;
import feign.Headers;
import feign.RequestLine;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by syl nerosyl@live.com on 2022/9/21
 *
 * @author syl
 */
@FeignClient(name = "LoginClient",url = "http://localhost:8088",configuration =FeignHttpClientConfig.class  )
public interface LoginClient {

    @Headers({"Content-Type: application/json",
    })
    @RequestLine("POST /login")
    Response login(LoginRequest loginRequest);
}
