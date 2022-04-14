package com.security_oidc_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SecurityOidcClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityOidcClientApplication.class, args);
    }

}
