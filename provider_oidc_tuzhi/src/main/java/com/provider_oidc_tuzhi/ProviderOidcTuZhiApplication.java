package com.provider_oidc_tuzhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderOidcTuZhiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderOidcTuZhiApplication.class, args);
    }

}
