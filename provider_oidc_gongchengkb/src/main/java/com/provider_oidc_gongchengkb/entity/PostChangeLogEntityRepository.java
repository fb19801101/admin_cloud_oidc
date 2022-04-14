package com.provider_oidc_gongchengkb.entity;

import java.util.List;

/**
 * 处理 OAuth 相关业务的 Repository
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public interface PostChangeLogEntityRepository {

    PostChangeLogEntity getPostChangeLogEntityById(Integer id);

    List<PostChangeLogEntity> getAllPostChangeLogEntity();

    List<PostChangeLogEntity> queryPostChangeLogEntity(String str);

    List<PostChangeLogEntity> queryPostChangeLogEntityByNodeId(String nodeProvider, Integer nodeId);

    List<PostChangeLogEntity> queryPostChangeLogEntityByNodeCode(String nodeProvider, String nodeCode);

    void insertPostChangeLogEntity(PostChangeLogEntity postChangeEntity);

    void insertPostChangeLogEntityList(List<PostChangeLogEntity> listPostChangeEntity);

    void updatePostChangeLogEntity(PostChangeLogEntity postChangeEntity);

    void updatePostChangeLogEntityList(List<PostChangeLogEntity> listPostChangeEntity);

}