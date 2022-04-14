package com.security_oidc_server.infrastructure.jdbc;


import com.security_oidc_server.domain.oauth.OauthClientDetails;
import com.security_oidc_server.domain.oauth.OauthRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Repository()
public class OauthRepositoryJdbc implements OauthRepository {


    private static OauthClientDetailsRowMapper oauthClientDetailsRowMapper = new OauthClientDetailsRowMapper();


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public OauthClientDetails findOauthClientDetails(String clientId) {
        final String sql = " select * from oauth_client_details where  client_id = ? ";
        final List<OauthClientDetails> list = this.jdbcTemplate.query(sql, new Object[]{clientId}, oauthClientDetailsRowMapper);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<OauthClientDetails> findAllOauthClientDetails(String clientId) {
        String sql;
        if (StringUtils.isNoneBlank(clientId)) {
            sql = " select * from oauth_client_details where archived = 0 and client_id like ? order by create_time desc ";
            return this.jdbcTemplate.query(sql, oauthClientDetailsRowMapper, clientId + "%");
        } else {
            sql = " select * from oauth_client_details where archived = 0 order by create_time desc ";
            return this.jdbcTemplate.query(sql, oauthClientDetailsRowMapper);
        }
    }

    @Override
//    @CacheEvict(value = CLIENT_DETAILS_CACHE, key = "#clientId")
    public int updateOauthClientDetailsArchive(String clientId, boolean archive) {
        final String sql = " update oauth_client_details set archived = ? where client_id = ? ";
        return this.jdbcTemplate.update(sql, archive, clientId);
    }

    @Override
    public void saveOauthClientDetails(final OauthClientDetails clientDetails) {
        final String sql = " insert into oauth_client_details(client_id,resource_ids,client_secret,scope,authorized_grant_types,web_server_redirect_uri," +
                " authorities,access_token_validity,refresh_token_validity,additional_information,trusted,autoapprove) values (?,?,?,?,?,?,?,?,?,?,?,?)";

        this.jdbcTemplate.update(sql, ps -> {
            ps.setString(1, clientDetails.clientId());
            ps.setString(2, clientDetails.resourceIds());

            ps.setString(3, clientDetails.clientSecret());
            ps.setString(4, clientDetails.scope());

            ps.setString(5, clientDetails.authorizedGrantTypes());
            ps.setString(6, clientDetails.webServerRedirectUri());

            ps.setString(7, clientDetails.authorities());
            ps.setObject(8, clientDetails.accessTokenValidity());

            ps.setObject(9, clientDetails.refreshTokenValidity());
            ps.setString(10, clientDetails.additionalInformation());

            ps.setBoolean(11, clientDetails.trusted());
            ps.setString(12, clientDetails.autoApprove());

        });
    }
}
