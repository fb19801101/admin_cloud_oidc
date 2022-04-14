package com.provider_oidc_gongchengbm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security 门户聚合API授权
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Order(0)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class PortalSecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/gongchengbm/**")
			.authorizeRequests()
			.antMatchers("/gongchengbm/crcc-menu")
			.hasAuthority("SCOPE_api_menhu")
			.anyRequest().authenticated()
			.and()
			.cors()
			.and()
			.csrf().disable()
			.oauth2ResourceServer().jwt().jwkSetUri("http://36.112.135.164:8099/discovery/certs");
	}
}
