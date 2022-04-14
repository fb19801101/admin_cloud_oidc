package com.security_oidc_server.service;


import com.security_oidc_server.domain.oauth.OauthClientDetails;
import com.security_oidc_server.service.dto.OauthClientDetailsDto;

import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public interface OauthService {

    OauthClientDetails loadOauthClientDetails(String clientId);

    void archiveOauthClientDetails(String clientId);

    List<OauthClientDetailsDto> loadOauthClientDetailsDtos(String clientId);

    String saveOAuthClientDetails(OauthClientDetailsDto formDto);

    OauthClientDetailsDto loadOauthClientDetailsDto(String clientId);

}