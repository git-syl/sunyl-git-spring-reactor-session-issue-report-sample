package com.zhichanzaixian.trademarkapi.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by syl nerosyl@live.com on 2022/9/21
 *
 * @author syl
 */
@NoArgsConstructor
@Data
public class LoginRequest {

    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
}
