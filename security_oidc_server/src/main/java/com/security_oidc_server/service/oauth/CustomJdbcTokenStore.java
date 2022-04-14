package com.security_oidc_server.service.oauth;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;



/**
 * 扩展默认的 TokenStore, 增加对缓存的支持
 *
 * from <a href="https://gitee.com/shengzhao/spring-oauth-server">spring-oauth-server</a>
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class CustomJdbcTokenStore extends JdbcTokenStore {


    public CustomJdbcTokenStore(DataSource dataSource) {
        super(dataSource);
    }


//    @Cacheable(value = ACCESS_TOKEN_CACHE, key = "#tokenValue")
    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return super.readAccessToken(tokenValue);
    }

//    @CacheEvict(value = ACCESS_TOKEN_CACHE, key = "#token.value")
    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        super.removeAccessToken(token);
    }

//    @Cacheable(value = REFRESH_TOKEN_CACHE, key = "#token")
    @Override
    public OAuth2RefreshToken readRefreshToken(String token) {
        return super.readRefreshToken(token);
    }

//    @CacheEvict(value = REFRESH_TOKEN_CACHE, key = "#token.value")
    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        super.removeRefreshToken(token);
    }

}
