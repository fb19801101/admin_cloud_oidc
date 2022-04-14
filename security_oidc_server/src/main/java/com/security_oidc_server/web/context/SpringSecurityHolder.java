package com.security_oidc_server.web.context;


import com.security_oidc_server.domain.security.OIDCUserDetails;
import com.security_oidc_server.domain.security.SecurityHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class SpringSecurityHolder implements SecurityHolder {

    @Override
    public OIDCUserDetails userDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof OIDCUserDetails) {
            return (OIDCUserDetails) principal;
        }
        return null;
    }
}