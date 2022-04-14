package com.provider_oidc_pingjiafbs.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author 薛朝阳
 * @date 2021-03-18 8:48
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class PostChangeLogEntity {
    private Integer id;
    private String nodeProvider;
    private Integer nodeId;
    private String nodeCode;
    private String nodePath;
    private Integer nodeIndex;
    private String content;
    private String postOld;
    private String postNew;
    private String userName;
    private LocalDateTime createTime;

    public PostChangeLogEntity() {

    }

    public PostChangeLogEntity(String nodeProvider, Integer nodeId, String nodeCode, String nodePath, Integer nodeIndex, String content, String postOld, String postNew, String userName) {
        this.nodeProvider = nodeProvider;
        this.nodeId = nodeId;
        this.nodeCode = nodeCode;
        this.nodePath = nodePath;
        this.nodeIndex = nodeIndex;
        this.content = content;
        this.postOld = postOld;
        this.postNew = postNew;
        this.userName = userName;
        this.createTime = LocalDateTime.now();
    }
}
