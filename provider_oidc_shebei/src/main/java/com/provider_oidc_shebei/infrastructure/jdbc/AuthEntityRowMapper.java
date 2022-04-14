/*
 * Copyright (c) 2015 MONKEYK Information Technology Co. Ltd
 * www.monkeyk.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * MONKEYK Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with MONKEYK Information Technology Co. Ltd.
 */
package com.provider_oidc_shebei.infrastructure.jdbc;


import com.provider_oidc_shebei.domain.auth.AuthRole;
import com.provider_oidc_shebei.entity.AuthEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * from <a href="https://gitee.com/shengzhao/spring-oauth-server">spring-oauth-server</a>
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class AuthEntityRowMapper implements RowMapper<AuthEntity> {


    public AuthEntityRowMapper() {
    }

    @Override
    public AuthEntity mapRow(ResultSet rs, int i) throws SQLException {
        AuthEntity authEntity = new AuthEntity();

        authEntity.setId(getInteger(rs,"id"));

        authEntity.setProviderId(rs.getString("provider_id"));
        authEntity.setOperatorId(getInteger(rs,"operator_id"));

        authEntity.setObjProvider(rs.getString("obj_provider"));
        authEntity.setObjId(getInteger(rs,"obj_id"));
        authEntity.setObjType(getInteger(rs,"obj_type"));
        authEntity.setObjNode(getInteger(rs,"obj_node"));
        authEntity.setObjName(rs.getString("obj_name"));
        authEntity.setObjPostId(getInteger(rs,"obj_post_id"));
        authEntity.setObjPostName(rs.getString("obj_post_name"));
        authEntity.setObjPostCode(rs.getString("obj_post_code"));
        authEntity.setObjPath(rs.getString("obj_path"));
        authEntity.setObjIndex(getInteger(rs,"obj_index"));

        authEntity.setNodeProvider(rs.getString("node_provider"));
        authEntity.setNodeId(getInteger(rs,"node_id"));
        authEntity.setNodeCode(rs.getString("node_code"));
        authEntity.setNodeType(getInteger(rs,"node_type"));
        authEntity.setNodePath(rs.getString("node_path"));
        authEntity.setNodeIndex(getInteger(rs,"node_index"));

        authEntity.setNodeParentProvider(rs.getString("node_parent_provider"));
        authEntity.setNodeParentId(getInteger(rs,"node_parent_id"));
        authEntity.setNodeParentCode(rs.getString("node_parent_code"));
        authEntity.setNodeParentType(getInteger(rs,"node_parent_type"));
        authEntity.setNodeParentPath(rs.getString("node_parent_path"));
        authEntity.setNodeParentIndex(getInteger(rs,"node_parent_index"));

        authEntity.setAuthRole(AuthRole.valueOf(rs.getString("auth_role")));
        authEntity.setAuthFlag(rs.getBoolean("auth_flag"));
        authEntity.setAuthOrg(getInteger(rs,"auth_org"));
        authEntity.setAuthOpAuth(getInteger(rs,"auth_op_auth"));
        authEntity.setAuthQueryAuth(getInteger(rs,"auth_query_auth"));
        authEntity.setAuthLog(getInteger(rs,"auth_log"));
        authEntity.setAuthPostChange(getInteger(rs,"auth_post_change"));
        authEntity.setAuthPostDelete(getInteger(rs,"auth_post_delete"));

        authEntity.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());


        return authEntity;
    }


    private Integer getInteger(ResultSet rs, String columnName) throws SQLException {
        final Object object = rs.getObject(columnName);
        if (object != null) {
            return (Integer) object;
        }
        return null;
    }

}
