package com.provider_oidc_gongcheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcGongChengApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcGongChengApplication.class, args);
    }

}
