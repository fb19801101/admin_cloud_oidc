package com.provider_oidc_laowu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcLaoWuApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcLaoWuApplication.class, args);
    }

}
