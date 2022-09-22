package com.zhichanzaixian.trademarkapi.api;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by syl nerosyl@live.com on 2022/9/21
 *
 * @author syl
 */
@FeignClient(name = "ReactorClient",url = "http://localhost:8051",configuration =FeignHttpClientConfig.class  )
public interface ReactorClient {

    @Headers({"Content-Type: application/json",
            "x-auth-token: {x-auth-token}",
    })
    @RequestLine("POST /userProfile/info")
    Response userProfile(@Param("x-auth-token") String authToken);
}
