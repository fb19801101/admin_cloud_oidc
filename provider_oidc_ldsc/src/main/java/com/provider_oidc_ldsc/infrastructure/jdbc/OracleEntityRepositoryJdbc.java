package com.provider_oidc_ldsc.infrastructure.jdbc;


import com.provider_oidc_ldsc.entity.OracleEntity;
import com.provider_oidc_ldsc.entity.OracleEntityRepository;
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
public class OracleEntityRepositoryJdbc implements OracleEntityRepository {

    private static OracleEntityRowMapper oracleEntityRowMapper = new OracleEntityRowMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<OracleEntity> getAllOracleEntity() {
        final String sql = " select * from rtb_user ";
        return this.jdbcTemplate.query(sql, oracleEntityRowMapper);
    }
}
