package com.security_oidc_server.service.impl;

import com.security_oidc_server.domain.oauth.OauthClientDetails;
import com.security_oidc_server.domain.oauth.OauthRepository;
import com.security_oidc_server.service.business.OAuthClientDetailsSaver;
import com.security_oidc_server.web.WebUtils;
import com.security_oidc_server.service.OauthService;
import com.security_oidc_server.service.dto.OauthClientDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * from <a href="https://gitee.com/shengzhao/spring-oauth-server">spring-oauth-server</a>
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Service("oauthService")
public class OauthServiceImpl implements OauthService {

    private static final Logger LOG = LoggerFactory.getLogger(OauthServiceImpl.class);


    @Autowired
    private OauthRepository oauthRepository;


    @Transactional(readOnly = true)
    @Override
    public OauthClientDetails loadOauthClientDetails(String clientId) {
        return oauthRepository.findOauthClientDetails(clientId);
    }


    @Transactional()
    @Override
    public void archiveOauthClientDetails(String clientId) {
        int i = oauthRepository.updateOauthClientDetailsArchive(clientId, true);
        LOG.debug("{}|Archived client: {}, {}", WebUtils.getIp(), clientId, i);
    }

    @Transactional(readOnly = true)
    @Override
    public List<OauthClientDetailsDto> loadOauthClientDetailsDtos(String clientId) {
        //暂时不加分页
        List<OauthClientDetails> clientDetailses = oauthRepository.findAllOauthClientDetails(clientId);
        return OauthClientDetailsDto.toDtos(clientDetailses);
    }

    @Transactional()
    @Override
    public String saveOAuthClientDetails(OauthClientDetailsDto formDto) {
        OAuthClientDetailsSaver saver = new OAuthClientDetailsSaver(formDto);
        return saver.save();
    }

    @Transactional(readOnly = true)
    @Override
    public OauthClientDetailsDto loadOauthClientDetailsDto(String clientId) {
        final OauthClientDetails oauthClientDetails = oauthRepository.findOauthClientDetails(clientId);
        return oauthClientDetails != null ? new OauthClientDetailsDto(oauthClientDetails) : null;
    }
}
