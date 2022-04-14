package com.security_oidc_server.web.controller.endpoint;

import com.security_oidc_server.service.SecurityService;
import com.security_oidc_server.service.dto.UserJsonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  UserInfo API
 *  https://openid.net/specs/openid-connect-core-1_0.html#UserInfo
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@RestController
@RequestMapping("/api/")
public class UserInfoEndpoint {


    @Autowired
    private SecurityService securityService;


    @GetMapping("userinfo")
    public UserJsonDto userinfo() {
        return securityService.loadCurrentUserJsonDto();
    }


}
