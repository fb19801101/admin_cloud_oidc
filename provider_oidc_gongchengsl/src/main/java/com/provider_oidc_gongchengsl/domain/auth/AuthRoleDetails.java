package com.provider_oidc_gongchengsl.domain.auth;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 定义Auth中的 Role信息
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class AuthRoleDetails implements Serializable {

    private static final long serialVersionUID = -6947822646185526939L;

    private Integer id;
    private String providerId;
    private Integer userId;
    private Integer authNode;
    private String authCode;
    private String authPath;
    private Boolean authType;
    private AuthRole authRole;
    private Boolean authFlag;
    private LocalDateTime createTime = LocalDateTime.now();

}