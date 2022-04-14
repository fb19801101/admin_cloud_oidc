package com.gateway.service;

import com.gateway.entity.OauthUserPrivilege;

import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-02-13 13:09
 */
public interface OauthUserPrivilegeService {
    List<OauthUserPrivilege> getAllOauthUserPrivilege();

    List<OauthUserPrivilege> getAllOauthUserPrivilege(Integer page, Integer limit);

    OauthUserPrivilege getOauthUserPrivilegeById(Integer id);

    OauthUserPrivilege getOauthUserPrivilegeByMinId();

    OauthUserPrivilege getOauthUserPrivilegeByMaxId();

    List<OauthUserPrivilege> getOauthUserPrivilegeByParam(OauthUserPrivilege record);

    List<OauthUserPrivilege> queryOauthUserPrivilegeByParam(OauthUserPrivilege record);

    List<OauthUserPrivilege> getOauthUserPrivilegeByCondition(String field, Object value);

    long countOauthUserPrivilegeBySelectCondition(String field, Object value);

    List<OauthUserPrivilege> getOauthUserPrivilegeByCondition(String field, Object value, Integer page, Integer limit);

    List<OauthUserPrivilege> queryOauthUserPrivilegeByCondition(String field, Object value);

    long countOauthUserPrivilegeByQueryCondition(String field, Object value);

    List<OauthUserPrivilege> queryOauthUserPrivilegeByCondition(String field, Object value, Integer page, Integer limit);

    List<OauthUserPrivilege> getOauthUserPrivilegeFieldsByCondition(String fields, String field, Object value);

    List<OauthUserPrivilege> getOauthUserPrivilegeFieldsByCondition(String fields, String field, Object value, Integer page, Integer limit);

    List<OauthUserPrivilege> queryOauthUserPrivilegeFieldsByCondition(String fields, String field, Object value);

    List<OauthUserPrivilege> queryOauthUserPrivilegeFieldsByCondition(String fields, String field, Object value, Integer page, Integer limit);

    List<OauthUserPrivilege> getOauthUserPrivilegeBySql(String sql);

    List<OauthUserPrivilege> getOauthUserPrivilegeFieldsBySql(String fields, String sql);

    int insertOauthUserPrivilege(OauthUserPrivilege record);

    int setOauthUserPrivilegeById(OauthUserPrivilege record);

    int delOauthUserPrivilegeById(Integer id);

    long countAllOauthUserPrivilege();

    int delAllOauthUserPrivilege();

    List<OauthUserPrivilege> queryByUserId(Integer userId);
}