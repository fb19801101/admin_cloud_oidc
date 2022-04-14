package com.gateway.config;

import com.gateway.constant.AuthConstant;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用返回对象
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-11-04 9:54
 */
//@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private List<String> authorities = new ArrayList<>();

    public AuthorizationManager(String authority, String... authorities ) {
        this.authorities.add("ROLE_" + authority);
        if(authorities != null) {
            for(String auth : authorities) {
                this.authorities.add("ROLE_" + auth);
            }
        }
    }

    /**
     * Spring Security提供的模式是一个请求配置一个角色
     * 多个角色共同拥有权限的验证
     * ADMIN USER CLIENT UNITY MOBILE CRCC
     *
     * @param mono
     * @param authorizationContext
     * @return
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        // 从MySQL中获取当前路径可访问角色列表
//        URI uri = authorizationContext.getExchange().getRequest().getURI();
//        authorities.add("ROLE_ADMIN");
//        authorities.add("ROLE_USER");
//        authorities.add("ROLE_CLIENT");
//        authorities.add("ROLE_UNITY");
//        authorities.add("ROLE_MOBILE");
//        authorities.add("ROLE_CRCC");
        authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
        //认证通过且角色匹配的用户可访问当前路径
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}
