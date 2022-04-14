package com.provider_oidc_jingying.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * http-client
 * java配置的优先级低于yml配置；如果yml配置不存在，会采用java配置
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-05-08 16:30
 */
@Component
@ConfigurationProperties(prefix = "spring.http-client.pool")
public class HttpClientPoolConfigurer {

    /**
     * 连接池的最大连接数
     */
    private Integer maxTotalConnect ;
    /**
     * 同路由的并发数
     */
    private Integer maxConnectPerRoute ;
    /**
     * 客户端和服务器建立连接超时，默认2s
     */
    private Integer connectTimeout = 2 * 1000;
    /**
     * 指客户端从服务器读取数据包的间隔超时时间,不是总读取时间，默认30s
     */
    private Integer readTimeout = 30 * 1000;
    /**
     * 连接池编码
     */
    private String charset = "UTF-8";
    /**
     * 重试次数,默认2次
     */
    private Integer retryTimes = 2;
    /**
     * 从连接池获取连接的超时时间,不宜过长,单位ms
     */
    private Integer connectionRequestTimeout = 200;
    /**
     * 服务器返回数据(response)的时间，超过抛出read timeout
     */
    private Integer socketTimeout = 5000;
    /**
     * 针对不同的地址,特别设置不同的长连接保持时间
     */
    private Map<String, Integer> keepAliveTargetHost;
    /**
     * 针对不同的地址,特别设置不同的长连接保持时间,单位 s
     */
    private Integer keepAliveTime = 60;
    /**
     * 用于校验线程空闲的时间
     */
    private Integer validateAfterInactivity = 7000;
    /**
     * 开启异步线程池
     */
    private Boolean async = false;

    public Integer getMaxTotalConnect() {
        return maxTotalConnect;
    }

    public void setMaxTotalConnect(Integer maxTotalConnect) {
        this.maxTotalConnect = maxTotalConnect;
    }

    public Integer getMaxConnectPerRoute() {
        return maxConnectPerRoute;
    }

    public void setMaxConnectPerRoute(Integer maxConnectPerRoute) {
        this.maxConnectPerRoute = maxConnectPerRoute;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Integer getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Map<String, Integer> getKeepAliveTargetHost() {
        return keepAliveTargetHost;
    }

    public void setKeepAliveTargetHost(Map<String, Integer> keepAliveTargetHost) {
        this.keepAliveTargetHost = keepAliveTargetHost;
    }

    public Integer getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Integer keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public Integer getValidateAfterInactivity() {
        return validateAfterInactivity;
    }

    public void setValidateAfterInactivity(Integer validateAfterInactivity) {
        this.validateAfterInactivity = validateAfterInactivity;
    }

    public Boolean getAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

}
