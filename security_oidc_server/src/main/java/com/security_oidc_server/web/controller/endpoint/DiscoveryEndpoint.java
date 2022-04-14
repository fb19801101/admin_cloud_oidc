package com.security_oidc_server.web.controller.endpoint;

import com.security_oidc_server.domain.shared.Application;
import com.security_oidc_server.infrastructure.oidc.OIDCUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.security_oidc_server.Constants.ID_TOKEN;
import static com.security_oidc_server.Constants.OIDC_ALG;

/**
 * Discovery API
 *
 * https://openid.net/specs/openid-connect-discovery-1_0.html#ProviderMetadata
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@RestController
public class DiscoveryEndpoint {


    /**
     * https://openid.net/specs/openid-connect-discovery-1_0.html#ProviderConfigurationRequest
     *
     * @return view
     * @throws Exception Exception
     */
    @GetMapping("/.well-known/openid-configuration")
    public Map<String, Object> configuration() throws Exception {
        Map<String, Object> model = new HashMap<>();
        String host = Application.host();
        model.put("issuer", host);
        model.put("authorization_endpoint", OIDCUtils.authorizeEndpoint(host));
        model.put("token_endpoint", OIDCUtils.tokenEndpoint(host));
        model.put("userinfo_endpoint", OIDCUtils.userinfoEndpoint(host));

        model.put("jwks_uri", OIDCUtils.jwksURI(host));
        model.put("registration_endpoint", OIDCUtils.registrationEndpoint(host));

        model.put("scopes_supported", Arrays.asList(OIDCUtils.SCOPE_OPENID, OIDCUtils.SCOPE_READ, OIDCUtils.SCOPE_WRITE));
        model.put("grant_types_supported", OIDCUtils.GrantType.types());
        model.put("response_types_supported", Arrays.asList("token", "code", ID_TOKEN));
        //ALG:
        model.put("id_token_signing_alg_values_supported", Collections.singletonList(OIDC_ALG));
        // "pairwise",
        model.put("subject_types_supported", Arrays.asList("public"));
        model.put("claims_supported", Arrays.asList("sub", "aud", "scope", "iss", "exp", "iat", "client_id", "authorities", "user_name"));
        return model;
    }


}
