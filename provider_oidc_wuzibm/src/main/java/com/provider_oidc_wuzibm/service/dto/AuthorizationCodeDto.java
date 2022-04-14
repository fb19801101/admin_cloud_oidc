package com.provider_oidc_wuzibm.service.dto;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * * <p>
 * From spring-oauth-client(https://gitee.com/mkk/spring-oauth-client)
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class AuthorizationCodeDto implements Serializable {


    private static final long serialVersionUID = -5293876788733247369L;
    private String userAuthorizationUri;
    private String responseType;
    private String scope;
    private String clientId;
    private String redirectUri;
    private String state;


    public AuthorizationCodeDto() {
    }


    public String getUserAuthorizationUri() {
        return userAuthorizationUri;
    }

    public void setUserAuthorizationUri(String userAuthorizationUri) {
        this.userAuthorizationUri = userAuthorizationUri;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /*
    * http://localhost:8080/oauth/authorize?client_id=unity-client&redirect_uri=http%3a%2f%2flocalhost%3a8080%2funity%2fdashboard.htm&response_type=code&scope=read
    * */
    public String getFullUri() throws UnsupportedEncodingException {
        String redirect = URLEncoder.encode(redirectUri, "UTF-8");
        return String.format("%s?response_type=%s&scope=%s&client_id=%s&redirect_uri=%s&state=%s", userAuthorizationUri, responseType, scope, clientId, redirect, state);
    }
}