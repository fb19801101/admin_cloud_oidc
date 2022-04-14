package com.security_oidc_client.web.controller;

import com.security_oidc_client.domain.DiscoveryEndpointInfo;
import com.security_oidc_client.domain.RPHolder;
import com.security_oidc_client.service.OIDCClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handle 'password'  type actions
 * From spring-oauth-client(https://gitee.com/mkk/spring-oauth-client)
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Controller
public class PasswordController {


    private static final Logger LOG = LoggerFactory.getLogger(PasswordController.class);


    @Autowired
    private OIDCClientService clientService;


    /**
     * Entrance:   step-1
     */
    @GetMapping(value = "password")
    public String password(Model model) {
        RPHolder rpHoldDto = clientService.loadRPHolder();
        //异常跳转回主页
        if (!rpHoldDto.isConfigRP()) {
            return "redirect:/";
        }
        DiscoveryEndpointInfo endpointInfo = rpHoldDto.getDiscoveryEndpointInfo();
        LOG.debug("Go to 'password' page, accessTokenUri = {}", endpointInfo.getToken_endpoint());
        model.addAttribute("accessTokenUri", endpointInfo.getToken_endpoint());
        model.addAttribute("rpHoldDto", rpHoldDto);
        return "password";
    }


}