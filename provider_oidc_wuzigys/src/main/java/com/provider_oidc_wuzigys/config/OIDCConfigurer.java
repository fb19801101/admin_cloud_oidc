package com.provider_oidc_wuzigys.config;


import com.provider_oidc_wuzigys.domain.RPHolder;
import com.provider_oidc_wuzigys.domain.shared.Application;
import com.provider_oidc_wuzigys.service.OIDCClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;


/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Configuration
public class OIDCConfigurer {

//    @Value("${application.host}")
//    private String host;

    @Autowired
    private ApplicationConfigurer applicationConfigurer;

    @Autowired
    private OIDCClientService clientService;

    /**
     * Application
     * @return
     */
    @Bean
    public Application application() {
        String host = applicationConfigurer.host();
        Application application = new Application();
        application.setHost(host);

        RPHolder rPHolder = clientService.loadRPHolder();
        Application.setApiRPHolder(rPHolder);
        return application;
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AsyncRestTemplate asyncRestTemplate() {
        return new AsyncRestTemplate();
    }

}
