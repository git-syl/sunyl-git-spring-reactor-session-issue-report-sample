package com.zhichanzaixian.trademarkapi.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * Created by syl nerosyl@live.com on 2022/1/11
 *
 * @author syl
 */
public class WechatAppLoginException  extends InternalAuthenticationServiceException {
    public WechatAppLoginException(String message) {
        super(message);
    }
}
