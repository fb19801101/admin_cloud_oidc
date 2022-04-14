package com.provider_oidc_ldsc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcLdscApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcLdscApplication.class, args);
    }

}
