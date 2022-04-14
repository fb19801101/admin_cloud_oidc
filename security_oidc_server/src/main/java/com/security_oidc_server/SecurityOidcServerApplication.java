package com.security_oidc_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaRepositories("com.security_oidc_server.infrastructure.jpa")
@EntityScan("com.security_oidc_server.domain.user")
public class SecurityOidcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityOidcServerApplication.class, args);
    }

}
