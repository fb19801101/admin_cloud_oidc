package com.provider_oidc_gongchengkb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcGongChengkbApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcGongChengkbApplication.class, args);
    }

}
