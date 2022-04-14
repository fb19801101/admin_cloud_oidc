package com.provider_oidc_shebeigys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcSheBeiGysApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcSheBeiGysApplication.class, args);
    }

}
