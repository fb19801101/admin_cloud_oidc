package com.provider_oidc_wuzi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcWuZiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcWuZiApplication.class, args);
    }

}
