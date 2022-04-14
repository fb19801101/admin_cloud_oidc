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
package com.provider_oidc_jingying.infrastructure.jdbc;


import com.provider_oidc_jingying.entity.OrgLogEntity;
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
public class OrgLogEntityRowMapper implements RowMapper<OrgLogEntity> {


    public OrgLogEntityRowMapper() {
    }

    @Override
    public OrgLogEntity mapRow(ResultSet rs, int i) throws SQLException {
        OrgLogEntity orgLogEntity = new OrgLogEntity();

        orgLogEntity.setId(getInteger(rs,"id"));

        orgLogEntity.setNodeProvider(rs.getString("node_provider"));
        orgLogEntity.setNodeId(getInteger(rs,"node_id"));
        orgLogEntity.setNodeCode(rs.getString("node_code"));
        orgLogEntity.setNodePath(rs.getString("node_path"));
        orgLogEntity.setNodeIndex(getInteger(rs,"node_index"));

        orgLogEntity.setContent(rs.getString("content"));
        orgLogEntity.setObjName(rs.getString("obj_name"));

        orgLogEntity.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());

        return orgLogEntity;
    }


    private Integer getInteger(ResultSet rs, String columnName) throws SQLException {
        final Object object = rs.getObject(columnName);
        if (object != null) {
            return (Integer) object;
        }
        return null;
    }

}
