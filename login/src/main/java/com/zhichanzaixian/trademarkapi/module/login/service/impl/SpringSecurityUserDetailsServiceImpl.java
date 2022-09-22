package com.zhichanzaixian.trademarkapi.module.login.service.impl;

import com.zhichanzaixian.trademarkapi.module.login.entity.UserLogin;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author syl  Date: 2019/12/5 Email:nerosyl@live.com
 */
@Slf4j
@Component
public class SpringSecurityUserDetailsServiceImpl implements UserDetailsService {



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional.ofNullable(username)
                .filter(StringUtils::isNotBlank)
                .orElseThrow(() -> new BadCredentialsException("username is not null"));


        UserLogin userLogin = new UserLogin();
        userLogin.setUserName(username);
        userLogin.setPassword(DigestUtils.md5Hex("1234"));
        userLogin.setLocked(false);
        userLogin.setEnabled(true);
        userLogin.setUserId(username + UUID.randomUUID().toString().replace("-", ""));

        return Optional.of(userLogin)
                .map(m -> {
                    if (checkExpired()) {
                        throw new BadCredentialsException("checkExpired fail ! ");
                    }
                    return m;
                })
                .map(this::applyInitAuth)
                .orElseThrow(() -> new BadCredentialsException("user not exits !"));
    }

    private String loadUserAuth(UserLogin user) {

        Set<String> springAuthoritySet = new HashSet<>();
        springAuthoritySet.add("api:pageList");
        springAuthoritySet.add("user:profile");
        return String.join(",", springAuthoritySet);
    }

    private boolean checkExpired() {
        return false;
    }







    private UserLogin applyInitAuth(UserLogin u) {
        u.initAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(loadUserAuth(u)));
        return u;
    }
}
