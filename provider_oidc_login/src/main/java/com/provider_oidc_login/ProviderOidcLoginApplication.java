package com.provider_oidc_login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 2018/03/22
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcLoginApplication.class, args);
    }

}
