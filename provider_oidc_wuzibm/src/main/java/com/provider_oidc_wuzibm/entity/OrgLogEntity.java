package com.provider_oidc_wuzibm.entity;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author 薛朝阳
 * @date 2021-03-18 8:56
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class OrgLogEntity {
    private Integer id;
    private String nodeProvider;
    private Integer nodeId;
    private String nodeCode;
    private String nodePath;
    private Integer nodeIndex;
    private String content;
    private String objName;
    private LocalDateTime createTime;

    public OrgLogEntity() {

    }

    public OrgLogEntity(String nodeProvider, Integer nodeId, String nodeCode, String nodePath, Integer nodeIndex, String content, String objName) {
        this.nodeProvider = nodeProvider;
        this.nodeId = nodeId;
        this.nodeCode = nodeCode;
        this.nodePath = nodePath;
        this.nodeIndex = nodeIndex;
        this.content = content;
        this.objName = objName;
        this.createTime = LocalDateTime.now();
    }
}
