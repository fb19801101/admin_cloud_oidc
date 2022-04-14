package com.security_oidc_client.domain;

import lombok.*;

/**
 * @Author QiYuan
 * @Date 2021/3/10
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TokenEntity {
    private String accessToken;
    private String tokenType;
    private String expiresIn;
    private String scope;
    private String jti;

}
