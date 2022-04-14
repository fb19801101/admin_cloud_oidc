package com.provider_oidc_gongchengwbs.infrastructure.jdbc;


import com.provider_oidc_gongchengwbs.domain.auth.AuthRoleDetails;
import com.provider_oidc_gongchengwbs.domain.auth.AuthRoleDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Repository()
public class AuthRoleDetailsRepositoryJdbc implements AuthRoleDetailsRepository {

    private static AuthRoleDetailsRowMapper authRoleDetailsRowMapper = new AuthRoleDetailsRowMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public AuthRoleDetails getAuthRoleDetailsById(Integer id) {
        final String sql = " select * from auth_roles where  id = ? ";
        final List<AuthRoleDetails> list = this.jdbcTemplate.query(sql, authRoleDetailsRowMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public AuthRoleDetails getCurrentAuthRoleDetailsByUserId(Integer userId) {
        final String sql = " select * from auth_roles where  user_id = ? and auth_flag = 0 ";
        final List<AuthRoleDetails> list = this.jdbcTemplate.query(sql, authRoleDetailsRowMapper, userId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<AuthRoleDetails> getCurrentAuthRoleDetailsByAuthNode(Integer authNode) {
        final String sql = " select * from auth_roles where  atuh_node = ? and auth_flag = 0 order by create_time desc ";
        return this.jdbcTemplate.query(sql, authRoleDetailsRowMapper, authNode);
    }

    @Override
    public List<AuthRoleDetails> getCurrentAuthRoleDetailsByAuthCode(String authCode) {
        final String sql = " select * from auth_roles where  auth_code like ? and auth_flag = 0 order by create_time desc ";
        return this.jdbcTemplate.query(sql, authRoleDetailsRowMapper, authCode+"%");
    }

    @Override
    public List<AuthRoleDetails> getHistoryAuthRoleDetailsByUserId(Integer userId) {
        final String  sql = " select * from auth_roles where user_id = ? and auth_flag = 1 order by create_time desc ";
        return this.jdbcTemplate.query(sql, authRoleDetailsRowMapper, userId);
    }

    @Override
    public List<AuthRoleDetails> getHistoryAuthRoleDetailsByAuthCode(String authCode) {
        final String  sql = " select * from auth_roles where auth_code like ? and auth_flag = 1 order by create_time desc ";
        return this.jdbcTemplate.query(sql, authRoleDetailsRowMapper, authCode+"%");
    }

    @Override
    public void insertAuthRoleDetails(final AuthRoleDetails authRoleDetails) {
        final String sql = " insert into auth_roles(provider_id,user_id,auth_node,auth_code,auth_path,auth_type,auth_role,auth_flag,create_time) values (?,?,?,?,?,?,?,?,?) ";

        this.jdbcTemplate.update(sql, ps -> {
            ps.setString(1, authRoleDetails.getProviderId());
            ps.setInt(2, authRoleDetails.getUserId());

            ps.setInt(3, authRoleDetails.getAuthNode());
            ps.setString(4, authRoleDetails.getAuthCode());
            ps.setString(5, authRoleDetails.getAuthPath());
            ps.setBoolean(6, authRoleDetails.getAuthType());
            ps.setString(7, authRoleDetails.getAuthRole().name());
            ps.setBoolean(8, authRoleDetails.getAuthFlag());

            ps.setTimestamp(9, Timestamp.valueOf(authRoleDetails.getCreateTime()));
        });
    }

    @Override
    public void updateAuthRoleDetails(final AuthRoleDetails authRoleDetails) {
        final String sql = " update auth_roles set provider_id = ?,user_id = ?,auth_node = ?,auth_code = ?,auth_path = ?,auth_type = ?,auth_role = ?,auth_flag = ?,create_time = ? where id = ? ";
        this.jdbcTemplate.update(sql,
                authRoleDetails.getProviderId(),
                authRoleDetails.getUserId(),

                authRoleDetails.getAuthNode(),
                authRoleDetails.getAuthCode(),
                authRoleDetails.getAuthPath(),
                authRoleDetails.getAuthType(),
                authRoleDetails.getAuthRole().name(),
                authRoleDetails.getAuthFlag(),

                Timestamp.valueOf(authRoleDetails.getCreateTime()),

                authRoleDetails.getId());
    }
}
