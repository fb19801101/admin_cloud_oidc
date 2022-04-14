package com.provider_oidc_hrext.web.controller;

import com.provider_oidc_hrext.domain.DateUtils;
import com.provider_oidc_hrext.domain.DiscoveryEndpointInfo;
import com.provider_oidc_hrext.domain.RPHolder;
import com.provider_oidc_hrext.domain.shared.Application;
import com.provider_oidc_hrext.service.dto.AuthorizationCodeDto;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Handle 'authorization_code'  type actions
 *
 * From spring-oauth-client(https://gitee.com/mkk/spring-oauth-client)
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@RestController
@RequestMapping("/all-api")
public class DevAllController {

    /**
     * 应用API注册信息
     *
     * @return
     */
    @GetMapping("info")
    public static RPHolder apiInfo() {
        RPHolder rpHolder = Application.getApiRPHolder();

        if (!rpHolder.isConfigRP()) {
            return null;
        }

        return rpHolder;
    }

    /**
     * 应用API登录
     *
     * @return
     */
    @GetMapping("login")
    public static String apiLogin() throws Exception {
        System.out.println("Redirect Login Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        RPHolder rpHolder = Application.getApiRPHolder();

        //异常跳转回主页
        if (!rpHolder.isConfigRP()) {
            return "redirect:/";
        }

        DiscoveryEndpointInfo endpointInfo = rpHolder.getDiscoveryEndpointInfo();
        AuthorizationCodeDto authorizationCodeDto = new AuthorizationCodeDto();
        authorizationCodeDto.setUserAuthorizationUri(endpointInfo.getAuthorization_endpoint());
        authorizationCodeDto.setResponseType("code");
        authorizationCodeDto.setScope("openid");

        if(rpHolder.getApiName() == null) {
            authorizationCodeDto.setRedirectUri(Application.host("authorization_callback"));
        } else {
            authorizationCodeDto.setRedirectUri(Application.host("auth_callback"));
        }

        authorizationCodeDto.setClientId(rpHolder.getClientId());
        authorizationCodeDto.setState(UUID.randomUUID().toString());

        String url = authorizationCodeDto.getFullUri();

        if(url != null && url.length() > 0) {
            System.out.println("Redirect Login Uri: " + url);
            System.out.println("Redirect Login Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
            return url;
        }

        return "redirect:/";
    }

    @GetMapping("echo")
    public String apiEcho() {
        return "api_hrext echo api all return: api-hrext-echo-all";
    }

}