package com.provider_oidc_laowufbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcLaoWufbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcLaoWufbsApplication.class, args);
    }

}
