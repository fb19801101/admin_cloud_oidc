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
public interface LogEntityRepository {

    LogEntity getLogEntityById(Integer id);

    List<LogEntity> getAllLogEntity();

    List<LogEntity> queryLogEntity(String str);

    List<LogEntity> queryLogEntityByNodeId(String nodeProvider, Integer nodeId);

    List<LogEntity> queryLogEntityByNodeCode(String nodeProvider, String nodeCode);

    void insertLogEntity(LogEntity logEntity);

    void insertLogEntityList(List<LogEntity> listLogEntity);

    void updateLogEntity(LogEntity logEntity);

    void updateLogEntityList(List<LogEntity> listLogEntity);
}