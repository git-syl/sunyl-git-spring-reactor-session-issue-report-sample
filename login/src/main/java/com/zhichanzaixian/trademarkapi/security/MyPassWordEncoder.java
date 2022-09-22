package com.zhichanzaixian.trademarkapi.security;



import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author syl  Date: 2019/5/14 Email:nerosyl@live.com
 *
 */
public class MyPassWordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return DigestUtils.md5Hex((String) rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        return StringUtils.equals(encodedPassword,DigestUtils.md5Hex((String) rawPassword));
    }


}
