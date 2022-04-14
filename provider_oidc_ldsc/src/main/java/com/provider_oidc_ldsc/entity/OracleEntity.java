package com.provider_oidc_ldsc.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author 薛朝阳
 * @date 2021-03-15 17:17
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class OracleEntity {
    private String id;
    private String name;
    private LocalDateTime createTime;
    private String userInfo;

    public OracleEntity() {

    }

    public OracleEntity(String id, String name, String userInfo) {
        this.id = id;
        this.name = name;
        this.createTime = LocalDateTime.now();
        this.userInfo = userInfo;
    }

}
