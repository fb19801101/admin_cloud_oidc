package com.provider_oidc_gongchengbm.domain.auth;


/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public enum AuthRole {

    VIEW("View"),  //浏览权限
    MODIFY("Modify"),  //管理浏览权限增删改
    NONE("None");  //无浏览权限

    private String label;

    AuthRole(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}