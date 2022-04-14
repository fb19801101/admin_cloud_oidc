package com.provider_oidc_dynamicplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcDynamicPlanApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcDynamicPlanApplication.class, args);
    }

}
