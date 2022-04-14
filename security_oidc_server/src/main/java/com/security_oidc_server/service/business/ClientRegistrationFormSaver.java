package com.security_oidc_server.service.business;

import com.security_oidc_server.Constants;
import com.security_oidc_server.domain.oauth.OauthClientDetails;
import com.security_oidc_server.domain.oauth.OauthRepository;
import com.security_oidc_server.domain.shared.BeanProvider;
import com.security_oidc_server.domain.user.Privilege;
import com.security_oidc_server.infrastructure.PasswordHandler;
import com.security_oidc_server.infrastructure.oidc.OIDCUseScene;
import com.security_oidc_server.infrastructure.oidc.OIDCUtils;
import com.security_oidc_server.web.WebUtils;
import com.security_oidc_server.service.dto.ClientRegistrationFormDto;
import com.security_oidc_server.service.dto.OauthClientDetailsDto;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwt.JwtClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class ClientRegistrationFormSaver {


    private static final Logger LOG = LoggerFactory.getLogger(ClientRegistrationFormSaver.class);

    private OauthRepository oauthRepository = BeanProvider.getBean(OauthRepository.class);


    private ClientRegistrationFormDto formDto;

    public ClientRegistrationFormSaver(ClientRegistrationFormDto formDto) {
        this.formDto = formDto;
    }


    public OauthClientDetailsDto save() {

        OauthClientDetails clientDetails = createDomain();
        String clientSecret = OIDCUtils.generateClientSecret();
        // encrypted client secret
        clientDetails.clientSecret(PasswordHandler.encode(clientSecret));

        oauthRepository.saveOauthClientDetails(clientDetails);
        LOG.debug("{}|Register OauthClientDetails: {}", WebUtils.getIp(), clientDetails);

        OauthClientDetails oauthClientDetails = oauthRepository.findOauthClientDetails(clientDetails.clientId());
        OauthClientDetailsDto detailsDto = new OauthClientDetailsDto(oauthClientDetails);
        detailsDto.setClientSecret(clientSecret);
        return detailsDto;
    }


    OauthClientDetails createDomain() {
        OIDCUseScene useScene = formDto.getUseScene();
        OauthClientDetails clientDetails = new OauthClientDetails()
                .clientId(OIDCUtils.generateClientId())
                .resourceIds(Constants.RESOURCE_ID)
                .authorizedGrantTypes(StringUtils.join(useScene.grantTypes(), ","));


        //判断scope
        if (useScene.isServer()) {
            clientDetails.scope(OIDCUtils.SCOPE_READ);
        } else {
            clientDetails.scope(OIDCUtils.SCOPE_OPENID);
        }
        clientDetails.webServerRedirectUri(formDto.getWebServerRedirectUri());

        //权限默认 CLIENT
        clientDetails.authorities(Privilege.CLIENT.name());

        //固定值
        clientDetails.accessTokenValidity(OIDCUtils.ACCESS_TOKEN_VALIDITY)
                .refreshTokenValidity(OIDCUtils.REFRESH_TOKEN_VALIDITY)
                .trusted(false);

        JwtClaims claims = new JwtClaims();
        claims.setStringClaim("appName", formDto.getAppName());
        clientDetails.additionalInformation(claims.toJson());

        return clientDetails;
    }
}
