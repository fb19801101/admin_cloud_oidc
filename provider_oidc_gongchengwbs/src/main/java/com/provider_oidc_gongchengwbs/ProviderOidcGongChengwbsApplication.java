package com.provider_oidc_gongchengwbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcGongChengwbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcGongChengwbsApplication.class, args);
    }

}
