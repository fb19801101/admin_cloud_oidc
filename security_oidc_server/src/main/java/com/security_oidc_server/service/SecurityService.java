package com.security_oidc_server.service;

import com.security_oidc_server.service.dto.ClientRegistrationFormDto;
import com.security_oidc_server.service.dto.OauthClientDetailsDto;
import com.security_oidc_server.service.dto.UserJsonDto;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public interface SecurityService extends UserDetailsService {

    UserJsonDto loadCurrentUserJsonDto();

    OauthClientDetailsDto saveClientRegistrationForm(ClientRegistrationFormDto formDto);
}
