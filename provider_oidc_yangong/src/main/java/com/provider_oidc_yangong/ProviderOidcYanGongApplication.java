package com.provider_oidc_yangong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcYanGongApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcYanGongApplication.class, args);
    }

}
