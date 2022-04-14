package com.gateway.config;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 通用返回对象
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-11-04 9:54
 */
public class AuthenticationToken extends AbstractAuthenticationToken{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Object credentials;
    private String principal;
    
    public AuthenticationToken(Object credentials, String principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.credentials = credentials;
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public String getPrincipal() {
        return this.principal;
    }

}
