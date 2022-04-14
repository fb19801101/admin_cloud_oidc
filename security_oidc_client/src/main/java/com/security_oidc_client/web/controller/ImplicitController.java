package com.security_oidc_client.web.controller;

import com.security_oidc_client.domain.DiscoveryEndpointInfo;
import com.security_oidc_client.domain.RPHolder;
import com.security_oidc_client.domain.shared.Application;
import com.security_oidc_client.service.OIDCClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handle 'implicit'  type actions
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Controller
public class ImplicitController {


    private static final Logger LOG = LoggerFactory.getLogger(ImplicitController.class);


    @Autowired
    private OIDCClientService clientService;


    /**
     * Entrance:   step-1
     */
    @GetMapping(value = "implicit")
    public String password(Model model) {
        RPHolder rpHoldDto = clientService.loadRPHolder();
        //异常跳转回主页
        if (!rpHoldDto.isConfigRP()) {
            return "redirect:/";
        }
        DiscoveryEndpointInfo endpointInfo = rpHoldDto.getDiscoveryEndpointInfo();
        LOG.debug("Go to 'implicit' page: {}",endpointInfo);
        model.addAttribute("rpHoldDto", rpHoldDto);
        model.addAttribute("endpointInfo", endpointInfo);
//        model.addAttribute("userAuthorizationUri", userAuthorizationUri);
//        model.addAttribute("unityUserInfoUri", unityUserInfoUri);
        model.addAttribute("host", Application.host());
        return "implicit";
    }


}