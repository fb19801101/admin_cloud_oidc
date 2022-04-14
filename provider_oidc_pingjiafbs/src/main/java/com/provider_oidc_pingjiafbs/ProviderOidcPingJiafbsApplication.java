package com.provider_oidc_pingjiafbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcPingJiafbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcPingJiafbsApplication.class, args);
    }

}
