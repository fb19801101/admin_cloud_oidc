package com.security_oidc_server.infrastructure;


import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public abstract class RandomUtils {


    public static String randomText() {
        return RandomStringUtils.random(32, true, true);
    }


    private RandomUtils() {
    }

    //全数字的随机值
    public static String randomNumber() {
        return RandomStringUtils.random(32, false, true);
    }

}
