package com.provider_oidc_workplan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcWorkPlanApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcWorkPlanApplication.class, args);
    }

}
