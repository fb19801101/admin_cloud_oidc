package com.security_oidc_server.web.controller.resource;


import com.security_oidc_server.service.SecurityService;
import com.security_oidc_server.service.dto.OAuthResourceDto;
import com.security_oidc_server.service.dto.UserJsonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.security_oidc_server.Constants.RESOURCE_ID;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Controller
@RequestMapping("/mobile/")
public class MobileController {


    @Autowired
    private SecurityService securityService;


    @GetMapping("dashboard")
    @ResponseBody
    public OAuthResourceDto dashboard() {
        return new OAuthResourceDto(RESOURCE_ID, "Just mobile-Resource");
    }

    @GetMapping("user_info")
    @ResponseBody
    public UserJsonDto userInfo() {
        return securityService.loadCurrentUserJsonDto();
    }

}