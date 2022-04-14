package com.provider_oidc_hrext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcHrExtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcHrExtApplication.class, args);
    }

}
