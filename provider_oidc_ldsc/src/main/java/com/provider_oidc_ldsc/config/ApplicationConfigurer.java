package com.provider_oidc_ldsc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * application
 * java配置的优先级低于yml配置；如果yml配置不存在，会采用java配置
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-05-08 16:30
 */
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationConfigurer {

    /**
     * 本服务器 host
     */
    private String host ;

    /**
     * SSO登录 openid-config-url
     */
    private String openidConfigUrl;

    /**
     * SSO退出 openid-logout-url
     */
    private String openidLogoutUrl;

    /**
     * API注册 reg-api-url
     */
    private String regApiUrl;

    /**
     * 人力资源 hr-api-url
     */
    private String hrApiUrl;

    /**
     * 门户认证 mh-certs-url
     */
    private String mhCertsUrl;

    /**
     * 门户认证KID mh-certs-kid
     */
    private String mhCertsKid;

    /**
     * API认证 reg-certs-url
     */
    private String regCertsUrl;

    /**
     * API认证KID reg-certs-kid
     */
    private String regCertsKid;

    /**
     * host 拆分数组
     */
    private String[] split;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host.endsWith("/") ? host : host + "/";
        split = host.split(":");
    }

    public String host() {
        return host;
    }

    public String host(Integer port) {
        if(split.length > 2) {
            return split[0]+":"+split[1]+":"+port+"/";
        }
        return host;
    }

    public String host(String param) {
        int i = param.indexOf("/");
        return i == 0 ? host+param.substring(1) : host+param;
    }

    public String ip() {
        if(split.length > 2) {
            int i = split[1].lastIndexOf("/");
            return i != -1 ? split[1].substring(i+1) : host;
        }
        return host;
    }

    public Integer port() {
        if(split.length > 2) {
            int i = split[2].indexOf("/");
            return Integer.parseInt(i != -1 ? split[2].substring(0,i) : "-1");
        }
        return -1;
    }


    public String getOpenidConfigUrl() {
        return openidConfigUrl;
    }

    public void setOpenidConfigUrl(String openidConfigUrl) {
        this.openidConfigUrl = openidConfigUrl;
    }

    public String getOpenidLogoutUrl() {
        return openidLogoutUrl;
    }

    public void setOpenidLogoutUrl(String openidLogoutUrl) {
        this.openidLogoutUrl = openidLogoutUrl;
    }

    public String getRegApiUrl() {
        return regApiUrl;
    }

    public void setRegApiUrl(String regApiUrl) {
        this.regApiUrl = regApiUrl;
    }

    public String getHrApiUrl() {
        return hrApiUrl;
    }

    public void setHrApiUrl(String hrApiUrl) {
        this.hrApiUrl = hrApiUrl;
    }

    public String getMhCertsUrl() {
        return mhCertsUrl;
    }

    public void setMhCertsUrl(String mhCertsUrl) {
        this.mhCertsUrl = mhCertsUrl;
    }

    public String getMhCertsKid() {
        return mhCertsKid;
    }

    public void setMhCertsKid(String mhCertsKid) {
        this.mhCertsKid = mhCertsKid;
    }

    public String getRegCertsUrl() {
        return regCertsUrl;
    }

    public void setRegCertsUrl(String regCertsUrl) {
        this.regCertsUrl = regCertsUrl;
    }

    public String getRegCertsKid() {
        return regCertsKid;
    }

    public void setRegCertsKid(String regCertsKid) {
        this.regCertsKid = regCertsKid;
    }
}
