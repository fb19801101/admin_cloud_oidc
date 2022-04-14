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
package com.provider_oidc_wuzibm.infrastructure.jdbc;


import com.provider_oidc_wuzibm.domain.auth.AuthRole;
import com.provider_oidc_wuzibm.domain.auth.AuthRoleDetails;
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
public class AuthRoleDetailsRowMapper implements RowMapper<AuthRoleDetails> {


    public AuthRoleDetailsRowMapper() {
    }

    @Override
    public AuthRoleDetails mapRow(ResultSet rs, int i) throws SQLException {
        AuthRoleDetails authRoleDetails = new AuthRoleDetails();

        authRoleDetails.setId(getInteger(rs,"id"));
        authRoleDetails.setProviderId(rs.getString("provider_id"));
        authRoleDetails.setUserId(getInteger(rs,"user_id"));

        authRoleDetails.setAuthNode(getInteger(rs,"auth_node"));
        authRoleDetails.setAuthCode(rs.getString("auth_code"));
        authRoleDetails.setAuthPath(rs.getString("auth_path"));
        authRoleDetails.setAuthType(rs.getBoolean("auth_type"));
        authRoleDetails.setAuthRole(AuthRole.valueOf(rs.getString("auth_role")));
        authRoleDetails.setAuthFlag(rs.getBoolean("auth_flag"));

        authRoleDetails.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());

        return authRoleDetails;
    }


    private Integer getInteger(ResultSet rs, String columnName) throws SQLException {
        final Object object = rs.getObject(columnName);
        if (object != null) {
            return (Integer) object;
        }
        return null;
    }

}
