package com.provider_oidc_gongchengsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcGongChengslApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcGongChengslApplication.class, args);
    }

}
