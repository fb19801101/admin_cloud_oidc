package com.security_oidc_server.service.impl;

import com.security_oidc_server.domain.security.OIDCUserDetails;
import com.security_oidc_server.domain.user.OauthUser;
import com.security_oidc_server.domain.user.UserRepository;
import com.security_oidc_server.service.SecurityService;
import com.security_oidc_server.service.business.ClientRegistrationFormSaver;
import com.security_oidc_server.service.oauth.CurrentUserJsonDtoLoader;
import com.security_oidc_server.service.dto.ClientRegistrationFormDto;
import com.security_oidc_server.service.dto.OauthClientDetailsDto;
import com.security_oidc_server.service.dto.UserJsonDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Service()
public class SecurityServiceImpl implements SecurityService {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    private UserRepository userRepository;


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.debug("Try load User by username: {}", username);
        OauthUser user = userRepository.findLoginUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Not found by username: " + username);
        }
        return new OIDCUserDetails(user);
    }


    /**
     * 获取当前登录用户 的信息  , JSON
     */
    @Override
    @Transactional(readOnly = true)
    public UserJsonDto loadCurrentUserJsonDto() {
        CurrentUserJsonDtoLoader loader = new CurrentUserJsonDtoLoader();
        return loader.load();
    }

    @Override
    @Transactional()
    public OauthClientDetailsDto saveClientRegistrationForm(ClientRegistrationFormDto formDto) {
        ClientRegistrationFormSaver formSaver = new ClientRegistrationFormSaver(formDto);
        return formSaver.save();
    }


}
