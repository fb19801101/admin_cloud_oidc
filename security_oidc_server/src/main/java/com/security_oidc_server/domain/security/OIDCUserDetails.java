package com.security_oidc_server.domain.security;


import com.security_oidc_server.domain.user.Privilege;
import com.security_oidc_server.domain.user.OauthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class OIDCUserDetails implements UserDetails {


    protected static final String ROLE_PREFIX = "ROLE_";
    private static final long serialVersionUID = 5161957098952238466L;

    protected OauthUser user;

    protected List<GrantedAuthority> authorities = new ArrayList<>();

    public OIDCUserDetails() {
    }

    public OIDCUserDetails(OauthUser user) {
        this.user = user;
        initialPrivileges();
    }

    private void initialPrivileges() {
        List<Privilege> privilegeList = privilegeList();
        for (Privilege privilege : privilegeList) {
            this.authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + privilege.name()));
        }
    }

    private List<Privilege> privilegeList() {
        //defaultUser  有所有权限
        if (user.defaultUser()) {
            return Arrays.asList(Privilege.values());
        } else {
            //固定值
            final List<Privilege> privileges = user.privileges();
            privileges.add(Privilege.USER);
            return privileges;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.password();
    }

    @Override
    public String getUsername() {
        return user.username();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public OauthUser user() {
        return user;
    }


    @Override
    public String toString() {
        return "{" +
                "user=" + user +
                ", authorities=" + authorities +
                '}';
    }
}