package com.consumer_oidc_ldsc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.consumer_oidc_ldsc.service.ProviderService;
import com.provider_oidc_ldsc.domain.RPHolder;
import com.provider_oidc_ldsc.entity.ResultCode;
import com.provider_oidc_ldsc.entity.ResultData;
import com.provider_oidc_ldsc.service.dto.AccessTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-10-30 7:56
 */
@RestController
@RequestMapping("/oidc-ldsc")
public class ConsumerController {
    @Autowired
    private ProviderService providerService;

    @Autowired
    private HttpServletRequest request;

    public ConsumerController() {}

    /**
     * 验证 AccessToKen
     * @return
     */
    public boolean checkToken() {
        AccessTokenDto accessToken = JSON.parseObject(request.getHeader("Authorization"), AccessTokenDto.class);

        if(accessToken == null) {
            return true;
        }

        return false;
    }

    @GetMapping(value = "/getApiInfo")
    public ResultData getApiInfo () {
        RPHolder rpHolder = providerService.apiInfo();

        if(rpHolder.isConfigRP()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), rpHolder.toJSON(), 1);
        }

        return new ResultData<>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), rpHolder.toJSON(), 1);
    }

    @GetMapping(value = "/getApiLogin")
    public ResultData getApiLogin () {
        String login = providerService.apiLogin();

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), login, 1);
    }

    @GetMapping(value = "/getApiEcho")
    public ResultData getApiEcho (HttpServletResponse response) {
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
        request.setAttribute("Authorization", userInfo.toJSONString());

        String echo = providerService.apiEcho();

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), echo, 1);
    }
}