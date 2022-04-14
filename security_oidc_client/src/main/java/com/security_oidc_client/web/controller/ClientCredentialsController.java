package com.security_oidc_client.web.controller;


import com.security_oidc_client.domain.DiscoveryEndpointInfo;
import com.security_oidc_client.domain.RPHolder;
import com.security_oidc_client.service.OIDCClientService;
import com.security_oidc_client.service.dto.AccessTokenDto;
import com.security_oidc_client.service.dto.AuthAccessTokenDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Handle 'client_credentials'  type actions
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Controller
public class ClientCredentialsController {


    private static final Logger LOG = LoggerFactory.getLogger(ClientCredentialsController.class);


    @Autowired
    private OIDCClientService clientService;

    /**
     * Entrance:   step-1
     */
    @GetMapping(value = "client_credentials")
    public String password(Model model) {
        RPHolder rpHoldDto = clientService.loadRPHolder();
        //异常跳转回主页
        if (!rpHoldDto.isConfigRP()) {
            return "redirect:/";
        }
        DiscoveryEndpointInfo endpointInfo = rpHoldDto.getDiscoveryEndpointInfo();
        LOG.debug("Go to 'client_credentials' page, accessTokenUri = {}", endpointInfo.getToken_endpoint());
        model.addAttribute("rpHoldDto", rpHoldDto);
        model.addAttribute("endpointInfo", endpointInfo);
        return "client_credentials";
    }


    /**
     * Ajax call , get access_token
     */
    @GetMapping(value = "credentials_access_token")
    @ResponseBody
    public AccessTokenDto getAccessToken(AuthAccessTokenDto authAccessTokenDto) {
        return clientService.retrieveCredentialsAccessTokenDto(authAccessTokenDto);
    }

}