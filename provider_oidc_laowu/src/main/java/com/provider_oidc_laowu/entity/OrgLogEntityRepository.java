package com.provider_oidc_laowu.entity;

import java.util.List;

/**
 * 处理 OAuth 相关业务的 Repository
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public interface OrgLogEntityRepository {

    OrgLogEntity getOrgLogEntityById(Integer id);

    List<OrgLogEntity> getAllOrgLogEntity();

    List<OrgLogEntity> queryOrgLogEntity(String str);

    List<OrgLogEntity> queryOrgLogEntityByNodeId(String nodeProvider, Integer nodeId);

    List<OrgLogEntity> queryOrgLogEntityByNodeCode(String nodeProvider, String nodeCode);

    void insertOrgLogEntity(OrgLogEntity orgLogEntity);

    void insertOrgLogEntityList(List<OrgLogEntity> listOrgLogEntity);

    void updateOrgLogEntity(OrgLogEntity orgLogEntity);

    void updateOrgLogEntityList(List<OrgLogEntity> listOrgLogEntity);

}