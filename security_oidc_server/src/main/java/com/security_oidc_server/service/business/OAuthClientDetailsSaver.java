package com.security_oidc_server.service.business;

import com.security_oidc_server.domain.oauth.OauthClientDetails;
import com.security_oidc_server.domain.oauth.OauthRepository;
import com.security_oidc_server.domain.shared.BeanProvider;
import com.security_oidc_server.web.WebUtils;
import com.security_oidc_server.service.dto.OauthClientDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class OAuthClientDetailsSaver {


    private static final Logger LOG = LoggerFactory.getLogger(OAuthClientDetailsSaver.class);

    private OauthRepository oauthRepository = BeanProvider.getBean(OauthRepository.class);

    private OauthClientDetailsDto formDto;

    public OAuthClientDetailsSaver(OauthClientDetailsDto formDto) {
        this.formDto = formDto;
    }

    public String save() {
        OauthClientDetails clientDetails = formDto.createDomain();
        oauthRepository.saveOauthClientDetails(clientDetails);
        LOG.debug("{}|Save OauthClientDetails: {}", WebUtils.getIp(), clientDetails);
        return clientDetails.clientId();
    }
}
