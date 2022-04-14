package com.provider_oidc_shebei.infrastructure.jdbc;


import com.provider_oidc_shebei.entity.AuthEntity;
import com.provider_oidc_shebei.entity.AuthEntityRepository;
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
public class AuthEntityRepositoryJdbc implements AuthEntityRepository {

    private static AuthEntityRowMapper authEntityRowMapper = new AuthEntityRowMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public AuthEntity getAuthEntityById(Integer id) {
        final String sql = " select * from demo_auth where  id = ? ";
        final List<AuthEntity> list = this.jdbcTemplate.query(sql, authEntityRowMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<AuthEntity> getAllAuthEntity() {
        final String sql = " select * from demo_auth order by node_index, obj_index, create_time desc ";
        return this.jdbcTemplate.query(sql, authEntityRowMapper);
    }

    @Override
    public List<AuthEntity> getAllAuthEntityByObjType(Integer objType) {
        final String sql = " select * from demo_auth where obj_type = ? order by node_index, obj_index, create_time desc ";
        return this.jdbcTemplate.query(sql, authEntityRowMapper, objType);
    }

    @Override
    public List<AuthEntity> getAuthEntityByObjId(String objProvider, Integer objId) {
        final String sql = " select * from demo_auth where obj_provider like ? and obj_id = ? ";
        return this.jdbcTemplate.query(sql, authEntityRowMapper, objProvider+"%", objId);
    }

    @Override
    public List<AuthEntity> getAuthEntityByNodeCode(String nodeProvider, String nodeCode) {
        final String sql = " select * from demo_auth where node_provider like ? and node_code like ? order by node_index, obj_index, create_time desc ";
        return this.jdbcTemplate.query(sql, authEntityRowMapper, nodeProvider+"%", nodeCode+"%");
    }

    @Override
    public List<AuthEntity> getAuthEntityByNodeId(String nodeProvider, Integer nodeId) {
        final String sql = " select * from demo_auth where node_provider like ? and node_id = ? order by node_index, obj_index, create_time desc ";
        return this.jdbcTemplate.query(sql, authEntityRowMapper, nodeProvider+"%", nodeId);
    }

    @Override
    public AuthEntity getCurrentAuthEntityByObjId(String objProvider, Integer objId) {
        final String sql = " select * from demo_auth where obj_provider like ? and obj_id = ? and auth_flag = 0 ";
        final List<AuthEntity> list = this.jdbcTemplate.query(sql, authEntityRowMapper, objProvider+"%", objId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public AuthEntity getCurrentAuthEntityByNodeCode(String nodeProvider, String nodeCode) {
        final String sql = " select * from demo_auth where node_provider like ? and node_code like ? and auth_flag = 0 order by node_index, obj_index, create_time desc ";
        final List<AuthEntity> list = this.jdbcTemplate.query(sql, authEntityRowMapper, nodeProvider+"%", nodeCode+"%");
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public AuthEntity getCurrentAuthEntityByNodeId(String nodeProvider, Integer nodeId) {
        final String sql = " select * from demo_auth where node_provider like ? and node_id = ? and auth_flag = 0 order by node_index, obj_index, create_time desc ";
        final List<AuthEntity> list = this.jdbcTemplate.query(sql, authEntityRowMapper, nodeProvider+"%", nodeId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<AuthEntity> getHistoryAuthEntityByObjId(String objProvider, Integer objId) {
        final String sql = " select * from demo_auth where obj_provider like ? and obj_id = ? and auth_flag = 1 order by node_index, obj_index, create_time desc ";
        return this.jdbcTemplate.query(sql, authEntityRowMapper, objProvider+"%", objId);
    }

    @Override
    public List<AuthEntity> getHistoryAuthEntityByNodeCode(String nodeProvider, String nodeCode) {
        final String  sql = " select * from demo_auth where node_provider like ? and node_code like ? and auth_flag = 1 order by node_index, obj_index, create_time desc ";
        return this.jdbcTemplate.query(sql, authEntityRowMapper, nodeProvider+"%", nodeCode+"%");
    }

    @Override
    public List<AuthEntity> getHistoryAuthEntityByNodeId(String nodeProvider, Integer nodeId) {
        final String sql = " select * from demo_auth where  node_provider like ? and node_id = ? and auth_flag = 1 order by node_index, obj_index, create_time desc ";
        return this.jdbcTemplate.query(sql, authEntityRowMapper, nodeProvider+"%", nodeId);
    }

    @Override
    public void insertAuthEntity(final AuthEntity authEntity) {
        final String sql = " insert into demo_auth(provider_id,operator_id," +
                "obj_provider,obj_id,obj_type,obj_node,obj_name,obj_post_id,obj_post_name,obj_post_code,obj_path,obj_index," +
                "node_provider,node_id,node_code,node_type,node_path,node_index," +
                "node_parent_provider,node_parent_id,node_parent_code,node_parent_type,node_parent_path,node_parent_index," +
                "auth_role,auth_flag,auth_org,auth_op_auth,auth_query_auth,auth_log,auth_post_change,auth_post_delete," +
                "create_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

        this.jdbcTemplate.update(sql, ps -> {
            ps.setString(1, authEntity.getProviderId());
            ps.setInt(2, authEntity.getOperatorId());

            ps.setString(3, authEntity.getObjProvider());
            ps.setInt(4, authEntity.getObjId());
            ps.setInt(5, authEntity.getObjType());
            ps.setInt(6, authEntity.getObjNode());
            ps.setString(7, authEntity.getObjName());
            ps.setInt(8, authEntity.getObjPostId());
            ps.setString(9, authEntity.getObjPostName());
            ps.setString(10, authEntity.getObjPostCode());
            ps.setString(11, authEntity.getObjPath());
            ps.setInt(12, authEntity.getObjIndex());

            ps.setString(13, authEntity.getNodeProvider());
            ps.setInt(14, authEntity.getNodeId());
            ps.setString(15, authEntity.getNodeCode());
            ps.setInt(16, authEntity.getNodeType());
            ps.setString(17, authEntity.getNodePath());
            ps.setInt(18, authEntity.getNodeIndex());

            ps.setString(19, authEntity.getNodeParentProvider());
            ps.setInt(20, authEntity.getNodeParentId());
            ps.setString(21, authEntity.getNodeParentCode());
            ps.setInt(22, authEntity.getNodeParentType());
            ps.setString(23, authEntity.getNodeParentPath());
            ps.setInt(24, authEntity.getNodeParentIndex());

            ps.setString(25, authEntity.getAuthRole().name());
            ps.setBoolean(26, authEntity.getAuthFlag());
            ps.setInt(27, authEntity.getAuthOrg());
            ps.setInt(28, authEntity.getAuthOpAuth());
            ps.setInt(29, authEntity.getAuthQueryAuth());
            ps.setInt(30, authEntity.getAuthLog());
            ps.setInt(31, authEntity.getAuthPostChange());
            ps.setInt(32, authEntity.getAuthPostDelete());

            ps.setTimestamp(33, Timestamp.valueOf(authEntity.getCreateTime()));
        });
    }

    @Override
    public void insertAuthEntityList(List<AuthEntity> listAuthEntity) {
        for(AuthEntity x: listAuthEntity) {
            insertAuthEntity(x);
        }
    }

    @Override
    public void updateAuthEntity(final AuthEntity authEntity) {
        final String sql = " update demo_auth set provider_id=?, operator_id=?, " +
                "obj_provider=?, obj_id=?, obj_type=?, obj_node=?, obj_name=?, obj_post_id=?, obj_post_name=?, obj_post_code=?, obj_path=?, obj_index=?, " +
                "node_provider=?, node_id=?, node_code=?, node_type=?, node_path=?, node_index=?, " +
                "node_parent_provider=?, node_parent_id=?, node_parent_code=?, node_parent_type=?, node_parent_path=?, node_parent_index=?, " +
                "auth_role=?, auth_flag=?, auth_org=?, auth_op_auth=?, auth_query_auth=?, auth_log=?, auth_post_change=?, auth_post_delete=?, " +
                "create_time=? " +
                "where id = ?";
        this.jdbcTemplate.update(sql,
                authEntity.getProviderId(),
                authEntity.getOperatorId(),

                authEntity.getObjProvider(),
                authEntity.getObjId(),
                authEntity.getObjType(),
                authEntity.getObjNode(),
                authEntity.getObjName(),
                authEntity.getObjPostId(),
                authEntity.getObjPostName(),
                authEntity.getObjPostCode(),
                authEntity.getObjPath(),
                authEntity.getObjIndex(),

                authEntity.getNodeProvider(),
                authEntity.getNodeId(),
                authEntity.getNodeCode(),
                authEntity.getNodeType(),
                authEntity.getNodePath(),
                authEntity.getNodeIndex(),

                authEntity.getNodeParentProvider(),
                authEntity.getNodeParentId(),
                authEntity.getNodeParentCode(),
                authEntity.getNodeParentType(),
                authEntity.getNodeParentPath(),
                authEntity.getNodeParentIndex(),

                authEntity.getAuthRole().name(),
                authEntity.getAuthFlag(),
                authEntity.getAuthOrg(),
                authEntity.getAuthOpAuth(),
                authEntity.getAuthQueryAuth(),
                authEntity.getAuthLog(),
                authEntity.getAuthPostChange(),
                authEntity.getAuthPostDelete(),

                Timestamp.valueOf(authEntity.getCreateTime()),

                authEntity.getId());
    }

    @Override
    public void updateAuthEntityList(List<AuthEntity> listAuthEntity) {
        for(AuthEntity x: listAuthEntity) {
            updateAuthEntity(x);
        }
    }

    @Override
    public void deleteAuthEntity(final AuthEntity authEntity) {
        final String sql = " delete from demo_auth where obj_id = ? ";
        this.jdbcTemplate.update(sql, authEntity.getObjId());
    }

    @Override
    public void deleteAuthEntityList(List<AuthEntity> listAuthEntity) {
        for(AuthEntity x: listAuthEntity) {
            deleteAuthEntity(x);
        }
    }

    @Override
    public void deleteAuthEntityByObjId(String objProvider, Integer objId) {
        final String sql = " delete from demo_auth where obj_provider like ? and obj_id = ? ";
        this.jdbcTemplate.update(sql, objProvider+"%", objId);
    }
}
