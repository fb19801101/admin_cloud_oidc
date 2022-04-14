package com.security_oidc_server.domain.user;


/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public enum Privilege {

    //Any user have the default privilege
    USER("User"),

    ADMIN("Admin"),  //管理 权限

    CLIENT("Client"),  //注册 client
    UNITY("Unity"),  //资源 权限
    MOBILE("Mobile"),  //资源 权限
    CRCC("crcc");  //资源 权限


    private String label;

    Privilege(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}