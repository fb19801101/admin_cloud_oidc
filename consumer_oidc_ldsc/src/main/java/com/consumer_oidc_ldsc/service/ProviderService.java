package com.consumer_oidc_ldsc.service;

import com.provider_oidc_ldsc.domain.RPHolder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-11-06 11:40
 */
@Component
@FeignClient(value = "provider-oidc-ldsc")
public interface ProviderService {
    @GetMapping("/all-api/info")
    RPHolder apiInfo();

    @GetMapping("/all-api/login")
    String apiLogin();

    @GetMapping("/all-api/echo")
    String apiEcho();
}
