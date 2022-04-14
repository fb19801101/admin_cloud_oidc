package com.provider_oidc_login.web.controller;

import com.provider_oidc_login.domain.shared.Application;
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
public class OIDCController {
    /**
     * 首页
     */
    @RequestMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("host", Application.host());
        return "index";
    }

    /**
     * Go login
     *
     * @param model
     * @return
     */
    @GetMapping(value = {"/login"})
    public String login(Model model) {
        model.addAttribute("host", Application.host());
        return "login";
    }

}
