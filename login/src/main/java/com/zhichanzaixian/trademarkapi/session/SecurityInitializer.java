package com.zhichanzaixian.trademarkapi.session;

import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
/**
 * @author syl  Date: 2020/06/18 Email:nerosyl@live.com
 *
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

	public SecurityInitializer() {
		super(SecurityConfig.class, HttpSessionConfig.class);
	}

}