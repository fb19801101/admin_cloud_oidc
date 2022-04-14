package com.provider_oidc_ldsc.service;


import com.provider_oidc_ldsc.domain.RPHolder;
import com.provider_oidc_ldsc.service.dto.*;

import java.util.List;
import java.util.Map;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public interface OIDCClientService {

    RPHolder loadRPHolder();

    RPHolder loadApiRPHolder(String apiName);

    List<RPHolder> loadApiRPHolders();

    boolean saveRPHolder(RPHolder rpHolder);

    boolean saveApiRPHolder(String apiName, RPHolder apiRpHolder);

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
