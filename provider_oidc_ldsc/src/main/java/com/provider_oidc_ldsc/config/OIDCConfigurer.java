package com.provider_oidc_ldsc.config;


import com.provider_oidc_ldsc.domain.RPHolder;
import com.provider_oidc_ldsc.domain.shared.Application;
import com.provider_oidc_ldsc.service.OIDCClientService;
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
        Application application = new Application();

        String host = applicationConfigurer.host();
        application.setHost(host);


        String openidConfigUrl = applicationConfigurer.getOpenidConfigUrl();
        application.setOpenidConfigUrl(openidConfigUrl);

        String openidLogoutUrl = applicationConfigurer.getOpenidLogoutUrl();
        application.setOpenidLogoutUrl(openidLogoutUrl);

        String regApiUrl = applicationConfigurer.getRegApiUrl();
        application.setRegApiUrl(regApiUrl);

        String hrApiUrl = applicationConfigurer.getHrApiUrl();
        application.setHrApiUrl(hrApiUrl);

        String mhCertsUrl = applicationConfigurer.getMhCertsUrl();
        application.setMhCertsUrl(mhCertsUrl);

        String mhCertsKid = applicationConfigurer.getMhCertsKid();
        application.setMhCertsKid(mhCertsKid);

        String regCertsUrl = applicationConfigurer.getRegCertsUrl();
        application.setRegCertsUrl(regCertsUrl);

        String regCertsKid = applicationConfigurer.getRegCertsKid();
        application.setRegCertsKid(regCertsKid);


        RPHolder rpHolder = clientService.loadRPHolder();
        Application.setApiRPHolder(rpHolder);

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
