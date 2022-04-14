package com.security_oidc_server.infrastructure.oidc;

import com.google.common.collect.ImmutableMap;
import com.security_oidc_server.Constants;
import com.security_oidc_server.web.WebUtils;
import org.jose4j.jwk.PublicJsonWebKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;

/**
 * 此处扩展点有：
 * 1. 解决  jwt中无 kid 问题
 * 2.  scope= openid 时增加  id_token
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class OIDCJwtAccessTokenConverter extends JwtAccessTokenConverter {

    private static final Logger LOG = LoggerFactory.getLogger(OIDCJwtAccessTokenConverter.class);

    private JsonParser objectMapper = JsonParserFactory.create();

    private Signer signer;

    private PublicJsonWebKey publicJsonWebKey;

    public OIDCJwtAccessTokenConverter(PublicJsonWebKey publicJsonWebKey) {
        super();
        this.publicJsonWebKey = publicJsonWebKey;
        this.initialJWK();
    }


    private void initialJWK() {
        PrivateKey privateKey = publicJsonWebKey.getPrivateKey();
        KeyPair keyPair = new KeyPair(publicJsonWebKey.getPublicKey(), privateKey);
        this.setKeyPair(keyPair);

        signer = new RsaSigner((RSAPrivateKey) privateKey);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Initialized self-signer: {}", signer);
        }
    }


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        OAuth2AccessToken enhanceToken = super.enhance(accessToken, authentication);

        //if have openid, add id_token
        if (authentication.getOAuth2Request().getScope().contains(OIDCUtils.SCOPE_OPENID)) {
            ImmutableMap<String, String> extHeader = ImmutableMap.of(
                    Constants.KEY_ID, publicJsonWebKey.getKeyId());
            String idToken = encodeWithHeader(accessToken, authentication, extHeader);
            enhanceToken.getAdditionalInformation().put(Constants.ID_TOKEN, idToken);
            if (LOG.isDebugEnabled()) {
                LOG.debug("{}|Added id_token: {} to accessToken: {}", WebUtils.getIp(), idToken, enhanceToken);
            }
        }

        return enhanceToken;
    }

    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//        return accessToken.getValue();
        ImmutableMap<String, String> extHeader = ImmutableMap.of(Constants.KEY_ID, publicJsonWebKey.getKeyId());
        return encodeWithHeader(accessToken, authentication, extHeader);
    }


    /**
     * 扩展 header
     */
    private String encodeWithHeader(OAuth2AccessToken accessToken, OAuth2Authentication authentication, ImmutableMap<String, String> extHeader) {
        String content;
        try {
            content = objectMapper.formatMap(getAccessTokenConverter().convertAccessToken(accessToken, authentication));
        } catch (Exception e) {
            throw new IllegalStateException("Cannot convert access token to JSON", e);
        }

        return JwtHelper.encode(content, signer, extHeader).getEncoded();
    }
}
