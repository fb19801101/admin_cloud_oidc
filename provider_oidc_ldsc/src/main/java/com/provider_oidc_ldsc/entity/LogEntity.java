package com.provider_oidc_ldsc.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author 薛朝阳
 * @date 2021-03-15 17:17
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class LogEntity {
    private Integer id;
    private String postProvider;
    private Integer postId;
    private String postCode;
    private String postPath;
    private String content;
    private String userName;
    private String userPath;
    private LocalDateTime createTime;

    public LogEntity() {

    }

    public LogEntity(Integer id, String content, String userName, String userPath, String postProvider, Integer postId, String postCode, String postPath) {
        this.id = id;
        this.content = content;
        this.userName = userName;
        this.userPath = userPath;
        this.postProvider = postProvider;
        this.postId = postId;
        this.postCode = postCode;
        this.postPath = postPath;
        this.createTime = LocalDateTime.now();
    }

}
