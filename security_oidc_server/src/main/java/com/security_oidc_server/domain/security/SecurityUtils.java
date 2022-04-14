package com.security_oidc_server.domain.security;


import com.security_oidc_server.domain.user.OauthUser;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class SecurityUtils {

    private static SecurityHolder securityHolder;

    public void setSecurityHolder(SecurityHolder securityHolder) {
        SecurityUtils.securityHolder = securityHolder;
    }


    /**
     * 当前登录用户
     *
     * @return Current user
     * @since 1.1.0
     */
    public static OauthUser currentUser() {
        OIDCUserDetails userDetails = securityHolder.userDetails();
        return userDetails != null ? userDetails.user() : null;
    }


    /**
     * 当前登录用户名
     *
     * @return Current username
     * @since 1.1.0
     */
    public static String currentUsername() {
        final OauthUser user = currentUser();
        return user != null ? user.username() : "unknown";
    }
}