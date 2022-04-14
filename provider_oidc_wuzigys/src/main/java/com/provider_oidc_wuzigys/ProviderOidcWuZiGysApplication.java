package com.provider_oidc_wuzigys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcWuZiGysApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcWuZiGysApplication.class, args);
    }

}
