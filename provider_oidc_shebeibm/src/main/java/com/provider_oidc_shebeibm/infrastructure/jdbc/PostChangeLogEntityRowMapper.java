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
package com.provider_oidc_shebeibm.infrastructure.jdbc;


import com.provider_oidc_shebeibm.entity.PostChangeLogEntity;
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
public class PostChangeLogEntityRowMapper implements RowMapper<PostChangeLogEntity> {


    public PostChangeLogEntityRowMapper() {
    }

    @Override
    public PostChangeLogEntity mapRow(ResultSet rs, int i) throws SQLException {
        PostChangeLogEntity postChangeLogEntity = new PostChangeLogEntity();

        postChangeLogEntity.setId(getInteger(rs,"id"));

        postChangeLogEntity.setNodeProvider(rs.getString("node_provider"));
        postChangeLogEntity.setNodeId(getInteger(rs,"node_id"));
        postChangeLogEntity.setNodeCode(rs.getString("node_code"));
        postChangeLogEntity.setNodePath(rs.getString("node_path"));
        postChangeLogEntity.setNodeIndex(getInteger(rs,"node_index"));

        postChangeLogEntity.setContent(rs.getString("content"));
        postChangeLogEntity.setPostOld(rs.getString("post_old"));
        postChangeLogEntity.setPostNew(rs.getString("post_new"));
        postChangeLogEntity.setUserName(rs.getString("user_name"));

        postChangeLogEntity.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());

        return postChangeLogEntity;
    }


    private Integer getInteger(ResultSet rs, String columnName) throws SQLException {
        final Object object = rs.getObject(columnName);
        if (object != null) {
            return (Integer) object;
        }
        return null;
    }

}
