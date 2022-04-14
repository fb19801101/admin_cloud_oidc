package com.security_oidc_server.service.dto;

import java.io.Serializable;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class OAuthResourceDto implements Serializable {
    private static final long serialVersionUID = -7266690020307910479L;


    private String resourceId;

    private String description;


    public OAuthResourceDto() {
    }

    public OAuthResourceDto(String resourceId, String description) {
        this.resourceId = resourceId;
        this.description = description;
    }


    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
