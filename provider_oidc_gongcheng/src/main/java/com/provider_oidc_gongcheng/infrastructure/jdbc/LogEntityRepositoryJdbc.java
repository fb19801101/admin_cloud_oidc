package com.provider_oidc_gongcheng.infrastructure.jdbc;


import com.provider_oidc_gongcheng.entity.LogEntity;
import com.provider_oidc_gongcheng.entity.LogEntityRepository;
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
public class LogEntityRepositoryJdbc implements LogEntityRepository {

    private static LogEntityRowMapper logEntityRowMapper = new LogEntityRowMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public LogEntity getLogEntityById(Integer id) {
        final String sql = " select * from demo_log where  id = ? ";
        final List<LogEntity> list = this.jdbcTemplate.query(sql, logEntityRowMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<LogEntity> getAllLogEntity() {
        final String sql = " select * from demo_log order by create_time desc ";
        return this.jdbcTemplate.query(sql, logEntityRowMapper);
    }

    @Override
    public List<LogEntity> queryLogEntity(String str) {
        final String sql = " select * from demo_log where  content like ? or user_name like ? or user_path like ? order by create_time desc ";
        return this.jdbcTemplate.query(sql, logEntityRowMapper, str+"%",str+"%",str+"%");
    }

    @Override
    public List<LogEntity> queryLogEntityByNodeId(String postProvider, Integer postId) {
        final String sql = " select * from demo_log where post_provider like ? and post_id = ? order by create_time desc ";
        return this.jdbcTemplate.query(sql, logEntityRowMapper, postProvider+"%", postId);
    }

    @Override
    public List<LogEntity> queryLogEntityByNodeCode(String postProvider, String postCode) {
        final String sql = " select * from demo_log where post_provider like ? and post_code like ? order by create_time desc ";
        return this.jdbcTemplate.query(sql, logEntityRowMapper, postProvider+"%", postCode+"%");
    }

    @Override
    public void insertLogEntity(final LogEntity logEntity) {
        final String sql = " insert into demo_log(post_provider,post_id,post_code,post_path,content, user_name, user_path, create_time) values (?,?,?,?,?,?,?,?) ";

        this.jdbcTemplate.update(sql, ps -> {
            ps.setString(1, logEntity.getPostProvider());
            ps.setInt(2, logEntity.getPostId());
            ps.setString(3, logEntity.getPostCode());
            ps.setString(4, logEntity.getPostPath());
            ps.setString(5, logEntity.getContent());
            ps.setString(6, logEntity.getUserName());
            ps.setString(7, logEntity.getUserPath());

            ps.setTimestamp(8, Timestamp.valueOf(logEntity.getCreateTime()));
        });
    }

    @Override
    public void insertLogEntityList(List<LogEntity> listLogEntity) {
        for(LogEntity x: listLogEntity) {
            insertLogEntity(x);
        }
    }

    @Override
    public void updateLogEntity(final LogEntity logEntity) {
        final String sql = " update demo_log set post_provider=?,post_id=?,post_code=?,post_path = ?,content = ?,user_name = ?,user_path = ?,create_time = ? where id = ? ";
        this.jdbcTemplate.update(sql,
                logEntity.getPostProvider(),
                logEntity.getPostId(),
                logEntity.getPostCode(),
                logEntity.getPostPath(),
                logEntity.getContent(),
                logEntity.getUserName(),
                logEntity.getUserPath(),

                Timestamp.valueOf(logEntity.getCreateTime()),

                logEntity.getId());
    }

    @Override
    public void updateLogEntityList(List<LogEntity> listLogEntity) {
        for(LogEntity x: listLogEntity) {
            updateLogEntity(x);
        }
    }
}
