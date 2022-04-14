package com.provider_oidc_jingying;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcJingYingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcJingYingApplication.class, args);
    }

}
