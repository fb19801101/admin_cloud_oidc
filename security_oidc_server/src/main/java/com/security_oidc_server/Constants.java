package com.security_oidc_server;

import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.Use;
import org.jose4j.jws.AlgorithmIdentifiers;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public interface Constants {

    /*Fixed,  resource-id */
    String RESOURCE_ID = "oidc-resource";

    /**
     * Fixed  keyId
     *
     * @since 1.1.0
     */
    String DEFAULT_KEY_ID = "oidc-keyid";

    /**
     * 长度 至少 1024, 建议 2048
     *
     * @since 1.1.0
     */
    int DEFAULT_KEY_SIZE = 2048;


    /**
     * keystore file name
     *
     * @since 1.1.0
     */
    String KEYSTORE_NAME = "jwks.json";

    /**
     * Default ALG: RS256
     *
     * @since 1.1.0
     */
    String OIDC_ALG = AlgorithmIdentifiers.RSA_USING_SHA256;

    /**
     * OIDC key use: sig or enc
     *
     * @since 1.1.0
     */
    String USE_SIG = Use.SIGNATURE;
    String USE_ENC = Use.ENCRYPTION;

    /**
     * id_token constants
     *
     * @since 1.1.0
     */
    String ID_TOKEN = "id_token";

    /**
     * JWT keyid
     *
     * @since 1.1.0
     */
    String KEY_ID = JsonWebKey.KEY_ID_PARAMETER;


    //系统字符编码
    String ENCODING = "UTF-8";

    String PROJECT_HOME = "https://github.com/monkeyk/MyOIDC";

    //Current  version
    String VERSION = "1.1.1";


}
