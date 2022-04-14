package com.provider_oidc_gongchengbm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcGongChengbmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcGongChengbmApplication.class, args);
    }

}
