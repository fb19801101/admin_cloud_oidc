package com.security_oidc_client.web.controller;

import com.security_oidc_client.service.OIDCClientService;
import com.security_oidc_client.service.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Controller
public class UserInfoController {


    @Autowired
    private OIDCClientService clientService;


    /**
     * user_info API
     */
    @GetMapping("user_info")
    public String unityUserInfo(String access_token, Model model) {
        UserInfoDto userDto = clientService.loadUserInfoDto(access_token);

        if (userDto.error()) {
            //error
            model.addAttribute("message", userDto.getErrorDescription());
            model.addAttribute("error", userDto.getError());
            return "redirect:oauth_error";
        } else {
            model.addAttribute("userDto", userDto);
            return "user_info";
        }

    }

}
