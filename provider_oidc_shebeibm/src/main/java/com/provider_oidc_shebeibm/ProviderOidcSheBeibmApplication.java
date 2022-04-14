package com.provider_oidc_shebeibm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcSheBeibmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcSheBeibmApplication.class, args);
    }

}
