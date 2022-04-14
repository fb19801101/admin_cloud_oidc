package com.provider_oidc_shebeigys.infrastructure.jdbc;


import com.provider_oidc_shebeigys.entity.PostChangeLogEntity;
import com.provider_oidc_shebeigys.entity.PostChangeLogEntityRepository;
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
public class PostChangeLogEntityRepositoryJdbc implements PostChangeLogEntityRepository {

    private static PostChangeLogEntityRowMapper postChangeLogEntityRowMapper = new PostChangeLogEntityRowMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public PostChangeLogEntity getPostChangeLogEntityById(Integer id) {
        final String sql = " select * from demo_post_log where  id = ? ";
        final List<PostChangeLogEntity> list = this.jdbcTemplate.query(sql, postChangeLogEntityRowMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<PostChangeLogEntity> getAllPostChangeLogEntity() {
        final String  sql = " select * from demo_post_log order by node_index, create_time desc ";
        return this.jdbcTemplate.query(sql, postChangeLogEntityRowMapper);
    }

    @Override
    public List<PostChangeLogEntity> queryPostChangeLogEntity(String str) {
        final String sql = " select * from demo_post_log where  content like ? or post_old like ? or post_new like ? or user_name like ? or node_path like ? order by node_index, create_time desc ";
        return this.jdbcTemplate.query(sql, postChangeLogEntityRowMapper, str+"%",str+"%",str+"%",str+"%",str+"%");
    }

    @Override
    public List<PostChangeLogEntity> queryPostChangeLogEntityByNodeId(String nodeProvider, Integer nodeId) {
        final String sql = " select * from demo_post_log where node_provider like ? and node_id = ? order by node_index, create_time desc ";
        return this.jdbcTemplate.query(sql, postChangeLogEntityRowMapper, nodeProvider+"%", nodeId);
    }

    @Override
    public List<PostChangeLogEntity> queryPostChangeLogEntityByNodeCode(String nodeProvider, String nodeCode) {
        final String sql = " select * from demo_post_log where node_provider like ? and node_code like ? order by node_index, create_time desc ";
        return this.jdbcTemplate.query(sql, postChangeLogEntityRowMapper, nodeProvider+"%", nodeCode+"%");
    }

    @Override
    public void insertPostChangeLogEntity(final PostChangeLogEntity postChangeLogEntity) {
        final String sql = " insert into demo_post_log(node_provider,node_id,node_code,node_path,node_index,content,post_old,post_new,user_name,create_time) values (?,?,?,?,?,?,?,?,?,?) ";

        this.jdbcTemplate.update(sql, ps -> {
            ps.setString(1, postChangeLogEntity.getNodeProvider());
            ps.setInt(2, postChangeLogEntity.getNodeId());
            ps.setString(3, postChangeLogEntity.getNodeCode());
            ps.setString(4, postChangeLogEntity.getNodePath());
            ps.setInt(5, postChangeLogEntity.getNodeIndex());
            ps.setString(6, postChangeLogEntity.getContent());
            ps.setString(7, postChangeLogEntity.getPostOld());
            ps.setString(8, postChangeLogEntity.getPostNew());
            ps.setString(9, postChangeLogEntity.getUserName());

            ps.setTimestamp(10, Timestamp.valueOf(postChangeLogEntity.getCreateTime()));
        });
    }

    @Override
    public void insertPostChangeLogEntityList(List<PostChangeLogEntity> listPostChangeLogEntity) {
        for(PostChangeLogEntity x: listPostChangeLogEntity) {
            insertPostChangeLogEntity(x);
        }
    }

    @Override
    public void updatePostChangeLogEntity(final PostChangeLogEntity postChangeLogEntity) {
        final String sql = " update demo_post_log set node_provider=?,node_id=?,node_code=?,node_path = ?, node_index=?, content = ?,post_old = ?,post_new = ?,user_name = ?,create_time = ? where id = ? ";
        this.jdbcTemplate.update(sql,
                postChangeLogEntity.getNodeProvider(),
                postChangeLogEntity.getNodeId(),
                postChangeLogEntity.getNodeCode(),
                postChangeLogEntity.getNodePath(),
                postChangeLogEntity.getNodeIndex(),
                postChangeLogEntity.getContent(),
                postChangeLogEntity.getPostOld(),
                postChangeLogEntity.getPostNew(),
                postChangeLogEntity.getUserName(),

                Timestamp.valueOf(postChangeLogEntity.getCreateTime()),

                postChangeLogEntity.getId());
    }

    @Override
    public void updatePostChangeLogEntityList(List<PostChangeLogEntity> listPostChangeLogEntity) {
        for(PostChangeLogEntity x: listPostChangeLogEntity) {
            updatePostChangeLogEntity(x);
        }
    }

}
