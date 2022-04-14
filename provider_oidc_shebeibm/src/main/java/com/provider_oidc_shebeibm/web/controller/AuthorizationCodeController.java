package com.provider_oidc_shebeibm.web.controller;


import com.alibaba.fastjson.JSON;
import com.provider_oidc_shebeibm.domain.DateUtils;
import com.provider_oidc_shebeibm.domain.DiscoveryEndpointInfo;
import com.provider_oidc_shebeibm.domain.RPHolder;
import com.provider_oidc_shebeibm.domain.TokenHolder;
import com.provider_oidc_shebeibm.domain.shared.Application;
import com.provider_oidc_shebeibm.entity.AuthEntityRepository;
import com.provider_oidc_shebeibm.entity.OrgLogEntityRepository;
import com.provider_oidc_shebeibm.entity.PostChangeLogEntityRepository;
import com.provider_oidc_shebeibm.service.OIDCClientService;
import com.provider_oidc_shebeibm.service.dto.*;
import com.provider_oidc_shebeibm.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
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
@Controller
public class AuthorizationCodeController {


    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationCodeController.class);


    @Autowired
    private OIDCClientService clientService;

    @Autowired
    private AuthEntityRepository authEntityRepository;

    @Autowired
    private PostChangeLogEntityRepository postChangeLogEntityRepository;

    @Autowired
    private OrgLogEntityRepository orgLogEntityRepository;



    /**
     * Entrance:   step-1
	 *
	 * @param model
	 * @return
	 */
	@GetMapping(value = "authorization_code")
    public String authorizationCode(Model model) {
        RPHolder rpHolder = Application.getApiRPHolder();
        //异常跳转回主页
        if (!rpHolder.isConfigRP()) {
            return "redirect:/";
        }
        DiscoveryEndpointInfo endpointInfo = rpHolder.getDiscoveryEndpointInfo();
        model.addAttribute("rpHoldDto", rpHolder);
        model.addAttribute("endpointInfo", endpointInfo);
        model.addAttribute("host", Application.host());
        model.addAttribute("state", UUID.randomUUID().toString());
        return "authorization_code";
    }


    /**
     * Save state firstly
     * Redirect to oauth-server login page:   step-2
	 *
	 * @param codeDto
	 * @param request
	 * @return
	 * @throws Exception
	 */
    @PostMapping(value = "authorization_code")
    public String submitAuthorizationCode(AuthorizationCodeDto codeDto, HttpServletRequest request) throws Exception {
        //save stats  firstly
        WebUtils.saveState(request, codeDto.getState());

        final String fullUri = codeDto.getFullUri();
        LOG.debug("Redirect to OIDC-Server URL: {}", fullUri);
        return "redirect:" + fullUri;
    }


    /**
     * OAuth callback (redirectUri):   step-3
     * <p>
     * Handle 'code', go to 'access_token' ,validation oauth-server response data
     * <p>
     * authorization_code_callback
	 *
	 * @param callbackDto
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
//    @GetMapping(value = "authorization_callback")
    public String authorizationCallback(AuthCallbackDto callbackDto, HttpServletRequest request, Model model) {
        if (callbackDto.error()) {
            //Server response error
            model.addAttribute("message", callbackDto.getError_description());
            model.addAttribute("error", callbackDto.getError());
            return "redirect:oauth_error";
        } else if (correctState(callbackDto, request)) {
            //Go to retrieve access_token form
            AuthAccessTokenDto accessTokenDto = clientService.createAuthAccessTokenDto(callbackDto);

            RPHolder rpHolder = Application.getApiRPHolder();
            model.addAttribute("rpHoldDto", rpHolder);
            model.addAttribute("accessTokenDto", accessTokenDto);
            model.addAttribute("host", Application.host());
            return "code_access_token";
        } else {
            //illegal state
            model.addAttribute("message", "Illegal \"state\": " + callbackDto.getState());
            model.addAttribute("error", "Invalid state");
            return "redirect:oauth_error";
        }

    }


    /**
     * Use HttpClient to get access_token :   step-4
     * <p/>
     * Then, 'authorization_code' flow is finished,  use 'access_token'  visit resources now
     *
     * @param tokenDto AuthAccessTokenDto
     * @param model    Model
     * @return View
     * @throws Exception e
     */
    @PostMapping("code_access_token")
    public String codeAccessToken(AuthAccessTokenDto tokenDto, Model model) {
        final AccessTokenDto accessTokenDto = clientService.retrieveAccessTokenDto(tokenDto);
        if (accessTokenDto.error()) {
            model.addAttribute("message", accessTokenDto.getErrorDescription());
            model.addAttribute("error", accessTokenDto.getError());
            return "oauth_error";
        } else {
            model.addAttribute("accessTokenDto", accessTokenDto);
            RPHolder rpHolder = Application.getApiRPHolder();
            model.addAttribute("rpHoldDto", rpHolder);
            model.addAttribute("endpointInfo", rpHolder.getDiscoveryEndpointInfo());
            return "access_token_result";
        }
    }


	/**
	 * Check the state is correct or not after redirect from Oauth Server.
	 * @param callbackDto
	 * @param request
	 * @return
	 */
    private boolean correctState(AuthCallbackDto callbackDto, HttpServletRequest request) {
        final String state = callbackDto.getState();
        return WebUtils.validateState(request, state);
    }


	/**
	 * clientLogin
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
    @GetMapping(value = "client_login")
    public  String clientLogin(HttpServletRequest request) throws Exception {
        RPHolder rpHolder = Application.getApiRPHolder();
        //异常跳转回主页
        if (!rpHolder.isConfigRP()) {
            return "redirect:/";
        }
        DiscoveryEndpointInfo endpointInfo = rpHolder.getDiscoveryEndpointInfo();
        AuthorizationCodeDto authorizationCodeDto = new AuthorizationCodeDto();
        authorizationCodeDto.setUserAuthorizationUri(endpointInfo.getAuthorization_endpoint());
        authorizationCodeDto.setResponseType("code");
        authorizationCodeDto.setScope("openid");
        authorizationCodeDto.setRedirectUri(Application.host("authorization_callback"));
        authorizationCodeDto.setClientId(rpHolder.getClientId());
        authorizationCodeDto.setState(UUID.randomUUID().toString());

        WebUtils.saveState(request, authorizationCodeDto.getState());
        final String fullUri = authorizationCodeDto.getFullUri();
        return "redirect:" + fullUri;
    }


	/**
	 * OAuth callback (redirectUri):   step-3
	 * <p>
	 * Handle 'code', go to 'access_token' ,validation oauth-server response data
	 * <p>
	 * accessToken_callback
	 *
	 * @param callbackDto
	 * @param model
	 * @return
	 */
    @GetMapping(value = "access_token_callback")
    public String accessTokenCallback(AuthCallbackDto callbackDto, Model model) {
        RPHolder rpHolder = Application.getApiRPHolder();
        //异常跳转回主页
        if (!rpHolder.isConfigRP()) {
//                return new CRCCUserInfoDto();
            model.addAttribute("host", Application.host());
            model.addAttribute("formDto", rpHolder);
            return "index";
        }

        //Go to retrieve access_token form
        AuthAccessTokenDto authAccessTokenDto = clientService.createAuthAccessTokenDto(callbackDto);
        authAccessTokenDto.setClientId(rpHolder.getClientId());
        authAccessTokenDto.setClientSecret(rpHolder.getClientSecret());
        authAccessTokenDto.setScope("openid");
        authAccessTokenDto.setRedirectUri(Application.host("authorization_callback"));
        AccessTokenDto accessTokenDto = clientService.retrieveAccessTokenDto_(authAccessTokenDto);
        UserInfoDto userInfoDto = clientService.loadUserInfoDto_(accessTokenDto.getAccessToken());
        rpHolder.setClientUserId(userInfoDto.getUserId().toString());
        rpHolder.setClientProviderId(userInfoDto.getProviderId());
        clientService.saveRPHolder(rpHolder);
        model.addAttribute("host", Application.host());
        model.addAttribute("formDto", rpHolder);
        model.addAttribute("userDto", userInfoDto);
        return "index";
    }


	/**
	 * currentUserInfo
	 *
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @GetMapping(value = "current_user_info")
    public String currentUserInfo(Model model) throws Exception {
        RPHolder rpHolder = Application.getApiRPHolder();
        //异常跳转回主页
        if (!rpHolder.isConfigRP()) {
//                return new CRCCUserInfoDto();
            model.addAttribute("host", Application.host());
            model.addAttribute("formDto", rpHolder);
            model.addAttribute("userDto", new CRCCUserInfoDto());
            return "index";
        }

        String providerId = rpHolder.getClientProviderId();
        Integer userId = Integer.parseInt(rpHolder.getClientUserId());
        if (TokenHolder.tokenEntity == null||providerId==null||providerId.length()==0||userId==null) {
            return "index";
        }

        String json = TokenHolder.doGet("/org/"+providerId+"/user/"+userId);
        CRCCUserInfoDto crccUserInfoDto = JSON.parseObject(json, CRCCUserInfoDto.class);
        model.addAttribute("host", Application.host());
        model.addAttribute("formDto", rpHolder);
        model.addAttribute("userDto", crccUserInfoDto);
        return "index";
    }


	/**
	 * clientLogout
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
    @GetMapping(value = "client_logout")
    public  String clientLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:https://g1openid.crcc.cn/logout";
    }


    /**
     * OAuth callback (redirectUri):   step-3
     * <p>
     * Handle 'code', go to 'access_token' ,validation oauth-server response data
     * <p>
     * authorization_code_callback
     *
     * @param callbackDto
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @GetMapping(value = "authorization_callback")
    public RedirectView authorizationCallback(AuthCallbackDto callbackDto, final RedirectAttributes redirectAttributes) throws Exception {
        System.out.println("RedirectView " + Application.host() + " Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        RPHolder rpHolder = Application.getApiRPHolder();

        //异常跳转回主页
        if (!rpHolder.isConfigRP()) {
            // 以Post方式传参   addFlashAttribute， 以 Get方式传参 addAttribute
            redirectAttributes.addAttribute("auth", "auth_fail");
            return new RedirectView("redirect:/");
        }

        //Go to retrieve access_token form
        AuthAccessTokenDto authAccessTokenDto = clientService.createAuthAccessTokenDto(callbackDto);
        authAccessTokenDto.setClientId(rpHolder.getClientId());
        authAccessTokenDto.setClientSecret(rpHolder.getClientSecret());
        authAccessTokenDto.setScope("openid");
        authAccessTokenDto.setRedirectUri(Application.host("authorization_callback"));
        AccessTokenDto accessTokenDto = clientService.retrieveAccessTokenDto_(authAccessTokenDto);
        UserInfoDto userInfoDto = clientService.loadUserInfoDto_(accessTokenDto.getAccessToken());
        if(userInfoDto != null) {
            String providerId = userInfoDto.getProviderId();
            Integer userId = userInfoDto.getUserId();

            if(providerId != null && providerId.length() > 0 && userId != null) {
                if (VueRetController.asyncAuthLoginOrgDelete(providerId, userId, authEntityRepository, postChangeLogEntityRepository, orgLogEntityRepository, true)) {
                    VueRetController.asyncAuthLoginPostChange(providerId, userId, authEntityRepository, postChangeLogEntityRepository, orgLogEntityRepository, false);
                    redirectAttributes.addAttribute("auth", "has_auth");
                    redirectAttributes.addAttribute("providerId", providerId);
                    redirectAttributes.addAttribute("userId", userId);
                    rpHolder.setClientUserAuth("has_auth");
                } else {
                    redirectAttributes.addAttribute("auth", "no_auth");
                    rpHolder.setClientUserAuth("no_auth");
                }

                rpHolder.setClientUserType("sso");
                rpHolder.setClientProviderId(providerId);
                rpHolder.setClientUserId(userId.toString());
                clientService.saveRPHolder(rpHolder);
            }
        }

        List<String> uris = rpHolder.getVueRedirectUris();
        for(String x: uris) {
            if(x.contains(Application.ip())) {
                System.out.println("RedirectView " + x + " Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
                return new RedirectView(x);
            }
        }

        return new RedirectView("redirect:/");
    }


    /**
     * OAuth callback (redirectUri):   step-3
     * <p>
     * Handle 'code', go to 'access_token' ,validation oauth-server response data
     * <p>
     * authorization_code_callback
     *
     * @param callbackDto
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @GetMapping(value = "auth_callback")
    public RedirectView authCallback(AuthCallbackDto callbackDto, final RedirectAttributes redirectAttributes) throws Exception {
        System.out.println("RedirectView " + Application.host() + " Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        RPHolder rpHolder = Application.getApiRPHolder();

        //异常跳转回主页
        if (!rpHolder.isConfigRP()) {
            // 以Post方式传参   addFlashAttribute， 以 Get方式传参 addAttribute
            redirectAttributes.addAttribute("auth", "auth_fail");
            return new RedirectView("redirect:/");
        }

        //Go to retrieve access_token form
        AuthAccessTokenDto authAccessTokenDto = clientService.createAuthAccessTokenDto(callbackDto);
        authAccessTokenDto.setClientId(rpHolder.getClientId());
        authAccessTokenDto.setClientSecret(rpHolder.getClientSecret());
        authAccessTokenDto.setScope("openid");
        authAccessTokenDto.setRedirectUri(Application.host("auth_callback"));
        AccessTokenDto accessTokenDto = clientService.retrieveAccessTokenDto_(authAccessTokenDto);
        UserInfoDto userInfoDto = clientService.loadUserInfoDto_(accessTokenDto.getAccessToken());
        if(userInfoDto != null) {
            String providerId = userInfoDto.getProviderId();
            Integer userId = userInfoDto.getUserId();

            if(providerId != null && providerId.length() > 0 && userId != null) {
                if (VueRetController.asyncAuthLoginOrgDelete(providerId, userId, authEntityRepository, postChangeLogEntityRepository, orgLogEntityRepository, true)) {
                    VueRetController.asyncAuthLoginPostChange(providerId, userId, authEntityRepository, postChangeLogEntityRepository, orgLogEntityRepository, false);
                    redirectAttributes.addAttribute("auth", "has_auth");
                    redirectAttributes.addAttribute("providerId", providerId);
                    redirectAttributes.addAttribute("userId", userId);
                    rpHolder.setClientUserAuth("has_auth");
                } else {
                    redirectAttributes.addAttribute("auth", "no_auth");
                    rpHolder.setClientUserAuth("no_auth");
                }

                rpHolder.setClientUserType("sso");
                rpHolder.setClientProviderId(providerId);
                rpHolder.setClientUserId(userId.toString());
                clientService.saveRPHolder(rpHolder);
            }
        }

        List<String> uris = rpHolder.getVueRedirectUris();
        for(String x: uris) {
            if(x.contains(Application.ip())) {
                System.out.println("RedirectView " + x + " Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
                return new RedirectView(x);
            }
        }

        return new RedirectView("redirect:/");
    }
}