package com.provider_oidc_login.web.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-10-30 7:56
 */
@Controller
@RequestMapping("auth-api")
public class AuthController {
    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping("login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("auth")
    public @ResponseBody Object auth(HttpServletResponse response) {
        JSONObject userInfo = new JSONObject();
        userInfo.put("admin", "RP-OIDC-2021");
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        roles.add("CLIENT");
        roles.add("USER");
        roles.add("UNITY");
        roles.add("MOBILE");
        userInfo.put("roles", roles);
        response.addHeader("Authorization", userInfo.toJSONString());
        return userInfo;
    }

    @RequestMapping("info")
    public @ResponseBody Object getAccountInfo() {
        JSONObject userInfo = new JSONObject();
        userInfo.put("unity", "RP-OIDC-2021");
        List<String> roles = new ArrayList<>();
        roles.add("CLIENT");
        roles.add("USER");
        roles.add("UNITY");
        userInfo.put("roles", roles);
        return userInfo;
    }
}
