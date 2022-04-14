package com.security_oidc_server.infrastructure.oidc;

import com.security_oidc_server.domain.security.OIDCUserDetails;
import com.security_oidc_server.domain.shared.Application;
import org.jose4j.jwt.ReservedClaimNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Ext. default
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class OIDCUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    private static final Logger LOG = LoggerFactory.getLogger(OIDCUserAuthenticationConverter.class);

    public OIDCUserAuthenticationConverter() {
    }

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> oidcMap = new HashMap<>();
        Map<String, ?> oldMap = super.convertUserAuthentication(authentication);
        oidcMap.putAll(oldMap);

        //按协议规范增加 required 属性
        // https://openid.net/specs/openid-connect-core-1_0.html#IDToken
        oidcMap.put(ReservedClaimNames.ISSUER, Application.host());
        oidcMap.put(ReservedClaimNames.ISSUED_AT, System.currentTimeMillis() / 1000);

        Object details = authentication.getDetails();
        if (details instanceof OIDCUserDetails) {
            OIDCUserDetails userDetails = (OIDCUserDetails) details;
            oidcMap.put(ReservedClaimNames.SUBJECT, userDetails.user().uuid());
        } else {
            oidcMap.put(ReservedClaimNames.SUBJECT, oidcMap.get(USERNAME));
        }
        return oidcMap;
    }
}
