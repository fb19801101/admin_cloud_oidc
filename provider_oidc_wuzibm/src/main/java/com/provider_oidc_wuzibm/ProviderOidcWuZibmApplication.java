package com.provider_oidc_wuzibm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcWuZibmApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcWuZibmApplication.class, args);
    }

}
