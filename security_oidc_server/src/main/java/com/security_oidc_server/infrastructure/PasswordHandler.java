package com.security_oidc_server.infrastructure;

import com.security_oidc_server.domain.shared.BeanProvider;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * From spring-oauth-server
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public abstract class PasswordHandler {


//    private PasswordEncoder passwordEncoder = SOSContextHolder.getBean(PasswordEncoder.class);


    private PasswordHandler() {
    }


    public static String encode(String password) {
        PasswordEncoder passwordEncoder = BeanProvider.getBean(PasswordEncoder.class);
        Assert.notNull(passwordEncoder, "passwordEncoder is null");
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }


    /**
     * 生成新的随机密码, 长度: 12
     *
     * @return Random password
     * @since 1.1.0
     */
    public static String randomPassword() {
        return RandomStringUtils.random(12, true, true);
    }
}
