package com.consumer_oidc_ldsc.config;

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

}
