package com.provider_oidc_wuzi.service.dto;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * From spring-oauth-client(https://gitee.com/mkk/spring-oauth-client)
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class AuthCallbackDto implements Serializable {


    private static final long serialVersionUID = 4713732199062099307L;
    private String code;
    private String state;

    /*
    * Server response error,
    * For example: Click 'Deny' button
    * */
    private String error;
    private String error_description;


    public AuthCallbackDto() {
    }

    public String getError() {
        return error;
    }

    public boolean error() {
        return StringUtils.isNotEmpty(error) || StringUtils.isNotEmpty(error_description);
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}