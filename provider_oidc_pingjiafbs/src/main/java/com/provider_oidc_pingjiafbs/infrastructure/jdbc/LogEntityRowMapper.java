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
package com.provider_oidc_pingjiafbs.infrastructure.jdbc;


import com.provider_oidc_pingjiafbs.entity.LogEntity;
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
public class LogEntityRowMapper implements RowMapper<LogEntity> {


    public LogEntityRowMapper() {
    }

    @Override
    public LogEntity mapRow(ResultSet rs, int i) throws SQLException {
        LogEntity logEntity = new LogEntity();

        logEntity.setId(getInteger(rs,"id"));

        logEntity.setPostProvider(rs.getString("post_provider"));
        logEntity.setPostId(getInteger(rs,"post_id"));
        logEntity.setPostCode(rs.getString("post_code"));
        logEntity.setPostPath(rs.getString("post_path"));

        logEntity.setContent(rs.getString("content"));
        logEntity.setUserName(rs.getString("user_name"));
        logEntity.setUserPath(rs.getString("user_path"));

        logEntity.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());

        return logEntity;
    }


    private Integer getInteger(ResultSet rs, String columnName) throws SQLException {
        final Object object = rs.getObject(columnName);
        if (object != null) {
            return (Integer) object;
        }
        return null;
    }

}
