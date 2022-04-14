package com.consumer_oidc_login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ConsumerOidcLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerOidcLoginApplication.class, args);
    }

}
