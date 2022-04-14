package com.provider_oidc_gangjinchang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcGangJinChangApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcGangJinChangApplication.class, args);
    }

}
