package com.provider_oidc_laowu.infrastructure.jdbc;


import com.provider_oidc_laowu.entity.OrgLogEntity;
import com.provider_oidc_laowu.entity.OrgLogEntityRepository;
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
public class OrgLogEntityRepositoryJdbc implements OrgLogEntityRepository {

    private static OrgLogEntityRowMapper orgLogEntityRowMapper = new OrgLogEntityRowMapper();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public OrgLogEntity getOrgLogEntityById(Integer id) {
        final String sql = " select * from demo_org_log where  id = ? ";
        final List<OrgLogEntity> list = this.jdbcTemplate.query(sql, orgLogEntityRowMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<OrgLogEntity> getAllOrgLogEntity() {
        final String sql = " select * from demo_org_log order by node_index, create_time desc ";
        return this.jdbcTemplate.query(sql, orgLogEntityRowMapper);
    }

    @Override
    public List<OrgLogEntity> queryOrgLogEntity(String str) {
        final String sql = " select * from demo_org_log where  content like ? or node_path like ? order by node_index, create_time desc ";
        return this.jdbcTemplate.query(sql, orgLogEntityRowMapper, str+"%", str+"%");
    }

    @Override
    public List<OrgLogEntity> queryOrgLogEntityByNodeId(String nodeProvider, Integer nodeId) {
        final String sql = " select * from demo_org_log where node_provider like ? and node_id = ? order by node_index, create_time desc ";
        return this.jdbcTemplate.query(sql, orgLogEntityRowMapper, nodeProvider+"%", nodeId);
    }

    @Override
    public List<OrgLogEntity> queryOrgLogEntityByNodeCode(String nodeProvider, String nodeCode) {
        final String sql = " select * from demo_org_log where node_provider like ? and node_code like ? order by node_index, create_time desc ";
        return this.jdbcTemplate.query(sql, orgLogEntityRowMapper, nodeProvider+"%", nodeCode+"%");
    }

    @Override
    public void insertOrgLogEntity(final OrgLogEntity orgLogEntity) {
        final String sql = " insert into demo_org_log(node_provider,node_id,node_code,node_path,node_index,content,obj_name,create_time) values (?,?,?,?,?,?,?,?) ";

        this.jdbcTemplate.update(sql, ps -> {
            ps.setString(1, orgLogEntity.getNodeProvider());
            ps.setInt(2, orgLogEntity.getNodeId());
            ps.setString(3, orgLogEntity.getNodeCode());
            ps.setString(4, orgLogEntity.getNodePath());
            ps.setInt(5, orgLogEntity.getNodeIndex());
            ps.setString(6, orgLogEntity.getContent());
            ps.setString(7, orgLogEntity.getObjName());

            ps.setTimestamp(8, Timestamp.valueOf(orgLogEntity.getCreateTime()));
        });
    }

    @Override
    public void insertOrgLogEntityList(List<OrgLogEntity> listOrgLogEntity) {
        for(OrgLogEntity x: listOrgLogEntity) {
            insertOrgLogEntity(x);
        }
    }

    @Override
    public void updateOrgLogEntity(final OrgLogEntity orgLogEntity) {
        final String sql = " update demo_org_log set node_provider=?,node_id=?,node_code=?,node_path = ?, node_index=?, content = ?,obj_name = ?,create_time = ? where id = ? ";
        this.jdbcTemplate.update(sql,
                orgLogEntity.getNodeProvider(),
                orgLogEntity.getNodeId(),
                orgLogEntity.getNodeCode(),
                orgLogEntity.getNodePath(),
                orgLogEntity.getNodeIndex(),
                orgLogEntity.getContent(),
                orgLogEntity.getObjName(),

                Timestamp.valueOf(orgLogEntity.getCreateTime()),

                orgLogEntity.getId());
    }

    @Override
    public void updateOrgLogEntityList(List<OrgLogEntity> listOrgLogEntity) {
        for(OrgLogEntity x: listOrgLogEntity) {
            updateOrgLogEntity(x);
        }
    }

}
