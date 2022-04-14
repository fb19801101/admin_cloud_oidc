package com.provider_oidc_yangong.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security API授权
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Order(1)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Override
	@SneakyThrows
	public void configure(WebSecurity web) {
		//Ignore, static
        web.ignoring().antMatchers("/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/api/**").permitAll()
			.antMatchers("/dev-api/**")
			.access("@vueRetController.checkAccessToken()")
			.antMatchers("/auth_callback", "/authorization_callback").permitAll()
			.antMatchers("/all-api/**").hasAuthority("SCOPE_api_yangong:all")
			.antMatchers("/hr11-api/**").hasAuthority("SCOPE_api_yangong:crcc11")
			.antMatchers("/hr12-api/**").hasAuthority("SCOPE_api_yangong:crcc12")
			.antMatchers("/hr19-api/**").hasAuthority("SCOPE_api_yangong:crcc19")
			.anyRequest().authenticated()
			.and()
			.cors()
			.and()
			.csrf().disable()
			.oauth2ResourceServer().jwt().jwkSetUri("https://regtest.crcc.cn/discovery/certs");
	}
}
