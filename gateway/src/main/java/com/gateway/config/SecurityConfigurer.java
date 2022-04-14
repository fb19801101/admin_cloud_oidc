package com.gateway.config;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.UnpooledByteBufAllocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Flux;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-11-04 9:54
 */
@EnableWebFluxSecurity
@Configuration
public class SecurityConfigurer {
    //security的鉴权排除的url列表
    private static final String[] excludedAuthPages = {
            "/security-oidc-server/**",
            "/security-oidc-client/**",
            "/consumer-oidc-ldsc/oidc-ldsc/**",
            "/provider-oidc-login/auth-api/**",
            "/provider-oidc-login/static/**"
    };

    /**
     * 过滤spring security提供的默认登陆页面
     * 显示自定义的登陆页面
     * 满足登陆form的action地址是分离
     *
     * @param http
     * @return
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        RedirectServerAuthenticationEntryPoint loginPoint = new RedirectServerAuthenticationEntryPoint("/security-oidc-server/login");

        http.authorizeExchange()
                // excludedAuthPages请求都不做验证，直接放过。
                .pathMatchers(excludedAuthPages).permitAll()
                // 需要认证的请求，认证成功之后返回给客户端的信息
                .and()
                .formLogin()
                .loginPage("/provider-oidc-login/auth-api/login")
                .authenticationEntryPoint(loginPoint)
                // 表示其它请求都必须是认证（登陆成功）之后才可以访问
                .authenticationSuccessHandler((webFilterExchange,authentication)->{
                    JSONObject result = new JSONObject();
                    result.put("code", 0);
                    return webFilterExchange.getExchange().getResponse().writeWith(Flux.create(sink -> {
                        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
                        try {
                            DataBuffer dataBuffer= nettyDataBufferFactory.wrap(result.toJSONString().getBytes("utf8"));
                            sink.next(dataBuffer);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        sink.complete();
                    }));
                })
                // 请求配置必须拥有Manager权限才可以访问
                //.and().authorizeExchange().pathMatchers("/provider-login/login/account/main").hasRole("Manager")
                //目前Spring Security提供的模式是一个请求配置一个角色，自定义个角色共同拥有的验证, ADMIN,CLIENT,USER。
                .and()
                .authorizeExchange()
                .pathMatchers("/provider-oidc-login/auth-api/auth")
                .access(new AuthorizationManager("ADMIN", "CLIENT", "USER", "UNITY"))
                .and()
                .authorizeExchange()
                .anyExchange().authenticated()
                .and()
                .csrf()
                .disable();

        SecurityWebFilterChain chain = http.build();

        // AuthenticationWebFilter用来拦截认证请求
        // 认证请求需要使用.flatMap( matchResult -> this.authenticationConverter.convert(exchange))方式从认证请求获取认证需要的信息，默认是获取登陆的用户名和密码
        Iterator<WebFilter> weIterable = chain.getWebFilters().toIterable().iterator();
        while(weIterable.hasNext()) {
            WebFilter f = weIterable.next();
            if(f instanceof AuthenticationWebFilter) {
                AuthenticationWebFilter webFilter = (AuthenticationWebFilter) f;
                //将自定义的AccountAuthenticationConverter添加到过滤器中
                webFilter.setServerAuthenticationConverter(new AuthenticationConverter());
            }
        }
        return chain;
    }

    /**
     * 认证成功操作,跳到转到主页面
     *
     * @return
     */
    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        return new ReactiveAuthenticationManagerAdapter((authentication)->{
            if(authentication instanceof AuthenticationToken) {
                AuthenticationToken authenticationToken = (AuthenticationToken)authentication;
                if(authenticationToken.getPrincipal() != null) {
                    authentication.setAuthenticated(true);
                    return authentication;
                } else {
                    return authentication;
                }
            } else {
                return authentication;
            }
        });
    }
}
