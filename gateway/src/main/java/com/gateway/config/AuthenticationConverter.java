package com.gateway.config;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import com.alibaba.fastjson.JSON;
import reactor.core.publisher.Mono;

/**
 * 登陆信息从GateWay session中获取认证请求信息
 * 默认是获取登陆的用户名和密码
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-11-04 9:54
 */
public class AuthenticationConverter extends ServerFormLoginAuthenticationConverter{
    private static Logger logger = LoggerFactory.getLogger(AuthenticationConverter.class);
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
       //从session中获取登陆用户信息
       String value = exchange.getSession().block().getAttribute("AuthInfo");
       if(value == null) {
           logger.error("用户认证信息为空,返回获取认证信息失败");
           return Mono.empty();
       } else {
           List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
           //获取权限信息
           List<String> roels = JSON.parseObject(value).getJSONArray("roles").toJavaList(String.class);
               roels.forEach(role->{
                   //这里必须添加前缀，参考：AuthorityReactiveAuthorizationManager.hasRole(role)
                   SimpleGrantedAuthority auth = new SimpleGrantedAuthority("ROLE_" + role);
                   simpleGrantedAuthorities.add(auth);
               });
            //添加用户信息到spring security之中。
           AuthenticationToken accountAuthentication = new AuthenticationToken(null, value, simpleGrantedAuthorities);
           return Mono.just(accountAuthentication);
       }
    }
}
