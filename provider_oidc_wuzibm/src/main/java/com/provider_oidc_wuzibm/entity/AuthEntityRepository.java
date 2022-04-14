package com.provider_oidc_wuzibm.entity;


import java.util.List;

/**
 * 处理 OAuth 相关业务的 Repository
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public interface AuthEntityRepository {

    AuthEntity getAuthEntityById(Integer id);

    List<AuthEntity> getAllAuthEntity();

    List<AuthEntity> getAllAuthEntityByObjType(Integer objType);

    List<AuthEntity> getAuthEntityByObjId(String objProvider, Integer objId);

    List<AuthEntity> getAuthEntityByNodeCode(String nodeProvider, String nodeCode);

    List<AuthEntity> getAuthEntityByNodeId(String nodeProvider, Integer nodeId);

    AuthEntity getCurrentAuthEntityByObjId(String objProvider, Integer objId);

    AuthEntity getCurrentAuthEntityByNodeCode(String nodeProvider, String nodeCode);

    AuthEntity getCurrentAuthEntityByNodeId(String nodeProvider, Integer nodeId);

    List<AuthEntity> getHistoryAuthEntityByObjId(String objProvider, Integer objId);

    List<AuthEntity> getHistoryAuthEntityByNodeCode(String nodeProvider, String nodeCode);

    List<AuthEntity> getHistoryAuthEntityByNodeId(String nodeProvider, Integer nodeId);

    void insertAuthEntity(AuthEntity authEntity);

    void insertAuthEntityList(List<AuthEntity> listAuthEntity);

    void updateAuthEntity(AuthEntity authEntity);

    void updateAuthEntityList(List<AuthEntity> listAuthEntity);

    void deleteAuthEntity(AuthEntity authEntity);

    void deleteAuthEntityList(List<AuthEntity> listAuthEntity);

    void deleteAuthEntityByObjId(String objProvider, Integer objId);
}