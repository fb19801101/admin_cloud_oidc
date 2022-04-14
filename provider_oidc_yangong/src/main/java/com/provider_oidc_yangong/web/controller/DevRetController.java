package com.provider_oidc_yangong.web.controller;

import com.provider_oidc_yangong.domain.DateUtils;
import com.provider_oidc_yangong.domain.DiscoveryEndpointInfo;
import com.provider_oidc_yangong.domain.RPHolder;
import com.provider_oidc_yangong.domain.TokenHolder;
import com.provider_oidc_yangong.domain.shared.Application;
import com.provider_oidc_yangong.entity.ResultCode;
import com.provider_oidc_yangong.entity.ResultData;
import com.provider_oidc_yangong.service.OIDCClientService;
import com.provider_oidc_yangong.service.dto.AccessTokenDto;
import com.provider_oidc_yangong.service.dto.AuthorizationCodeDto;
import com.provider_oidc_yangong.service.dto.UserInfoDto;
import com.provider_oidc_yangong.web.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Handle 'authorization_code'  type actions
 *
 * From spring-oauth-client(https://gitee.com/mkk/spring-oauth-client)
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@RestController
@RequestMapping("/api")
public class DevRetController {

    @Autowired
    private OIDCClientService clientService;

    /**
     * 获取API 应用 TOKEN信息
     *
     * @return
     */
    @GetMapping(value = "access_token")
    public ResultData<AccessTokenDto> accessToken() {
        RPHolder rpHolder = Application.getApiRPHolder();
        AccessTokenDto accessTokenDto = TokenHolder.loadAccessToken(rpHolder);

        if (accessTokenDto != null) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), accessTokenDto, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取登录用户信息
     * @return
     */
    @GetMapping(value = "user_info")
    public ResultData<UserInfoDto> userInfo() {
        RPHolder rpHolder = Application.getApiRPHolder();
        UserInfoDto userInfoDto = new UserInfoDto(rpHolder.getClientProviderId(),
                Integer.parseInt(rpHolder.getClientUserId()),
                rpHolder.getClientUserAuth(),
                rpHolder.getClientUserType());

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), userInfoDto, 1);
    }

    /**
     * 用户登录
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("redirect_login")
    public ResultData<String> redirectLogin(HttpServletRequest request) throws Exception {
        System.out.println("Redirect Login Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        RPHolder rpHolder = clientService.loadRPHolder();

        //异常跳转回主页
        if (!rpHolder.isConfigRP()) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), "redirect:/", 0);
        }

        Application.setApiRPHolder(rpHolder);

        DiscoveryEndpointInfo endpointInfo = rpHolder.getDiscoveryEndpointInfo();
        AuthorizationCodeDto authorizationCodeDto = new AuthorizationCodeDto();
        authorizationCodeDto.setUserAuthorizationUri(endpointInfo.getAuthorization_endpoint());
        authorizationCodeDto.setResponseType("code");
        authorizationCodeDto.setScope("openid");

        if(rpHolder.getApiName() == null) {
            authorizationCodeDto.setRedirectUri(Application.host("authorization_callback"));
        } else {
            authorizationCodeDto.setRedirectUri(Application.host("auth_callback"));
        }

        authorizationCodeDto.setClientId(rpHolder.getClientId());
        authorizationCodeDto.setState(UUID.randomUUID().toString());

        WebUtils.saveState(request, authorizationCodeDto.getState());
        String url = authorizationCodeDto.getFullUri();

        if(url != null && url.length() > 0) {
            VueRetController.initOrganization();
            System.out.println("Redirect Login Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), url, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), "redirect:/", 0);
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @GetMapping("redirect_logout")
    public ResultData<String> redirectLogout(HttpServletRequest request)  {
        HttpSession session = request.getSession();
        session.invalidate();

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), "https://g1openid.crcc.cn/logout", 1);
    }


}