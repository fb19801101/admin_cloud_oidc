package com.security_oidc_server.web.controller.endpoint;

import com.security_oidc_server.web.WebUtils;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@RestController
public class JWKSEndpoint {


    private static final Logger LOG = LoggerFactory.getLogger(JWKSEndpoint.class);

    @Autowired
    private JsonWebKeySet jsonWebKeySet;


    @GetMapping(value = "/public/jwks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String jwks() throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Call 'jwks' API from IP: {}", WebUtils.getIp());
        }
        return jsonWebKeySet.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
    }

    @GetMapping(value = "/public/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String auth() throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Call 'jwks' API from IP: {}", WebUtils.getIp());
        }
        return jsonWebKeySet.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
    }

}
