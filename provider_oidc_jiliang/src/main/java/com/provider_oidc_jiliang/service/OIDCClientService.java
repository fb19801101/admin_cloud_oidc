package com.provider_oidc_jiliang.service;


import com.provider_oidc_jiliang.domain.RPHolder;
import com.provider_oidc_jiliang.service.dto.*;

import java.util.Map;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public interface OIDCClientService {

    RPHolder loadRPHolder();

    boolean saveRPHolder(RPHolder rpHolder);

    AuthAccessTokenDto createAuthAccessTokenDto(AuthCallbackDto callbackDto);

    AccessTokenDto retrieveAccessTokenDto(AuthAccessTokenDto tokenDto);

    AccessTokenDto retrieveAccessTokenDto_(AuthAccessTokenDto tokenDto);

    UserInfoDto loadUserInfoDto(String access_token);

    UserInfoDto loadUserInfoDto_(String access_token);

    Map<String,Object> verifyToken(String token);

    AccessTokenDto retrieveCredentialsAccessTokenDto(AuthAccessTokenDto tokenDto);

    AccessTokenDto retrievePasswordAccessTokenDto(AuthAccessTokenDto authAccessTokenDto);

    AccessTokenDto refreshAccessTokenDto(RefreshAccessTokenDto refreshAccessTokenDto);

}
