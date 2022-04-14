package com.security_oidc_client.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@RestController
public class OidcClientController{

    @GetMapping("/get")
    public String getClentInfo() {
        return "OIDC-CLIENT-INFO";
    }
}
