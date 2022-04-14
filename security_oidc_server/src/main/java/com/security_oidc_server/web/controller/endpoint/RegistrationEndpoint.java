package com.security_oidc_server.web.controller.endpoint;

import com.security_oidc_server.domain.shared.Application;
import com.security_oidc_server.service.SecurityService;
import com.security_oidc_server.infrastructure.oidc.OIDCUseScene;
import com.security_oidc_server.service.dto.ClientRegistrationFormDto;
import com.security_oidc_server.service.dto.OauthClientDetailsDto;
import com.security_oidc_server.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Registration client API
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Controller
@RequestMapping("/public/")
public class RegistrationEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationEndpoint.class);


    @Autowired
    private SecurityService securityService;


    // 引导 注册
    @GetMapping("registration")
    public String preRegistration(Model model) {
        LOG.debug("{}|Pre registration, model: {}", WebUtils.getIp(), model);
        return "registration_pre";
    }


    /**
     * 开始注册 form
     *
     * @param useScene 使用场景, 默认为 WEB
     * @param model    Model
     * @return view
     */
    @GetMapping("registration_form")
    public String registration(@RequestParam("scene") OIDCUseScene useScene, Model model) {
        ClientRegistrationFormDto formDto = new ClientRegistrationFormDto(useScene);
        model.addAttribute("formDto", formDto);
        return "registration_form";
    }

    /**
     * 注册客户端提交
     */
    @PostMapping("registration_form")
    public String submitRegistration(@ModelAttribute("formDto") @Valid ClientRegistrationFormDto formDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration_form";
        }
        OauthClientDetailsDto clientDetailsDto = securityService.saveClientRegistrationForm(formDto);
        model.addAttribute("clientDetailsDto", clientDetailsDto);
        model.addAttribute("host", Application.host());
        return "registration_success";
    }

}
