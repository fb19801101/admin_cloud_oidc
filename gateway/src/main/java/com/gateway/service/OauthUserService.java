package com.gateway.service;

import com.gateway.entity.OauthUser;

import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-02-13 13:09
 */
public interface OauthUserService {
    List<OauthUser> getAllOauthUser();

    List<OauthUser> getAllOauthUser(Integer page, Integer limit);

    OauthUser getOauthUserById(Integer id);

    OauthUser getOauthUserByMinId();

    OauthUser getOauthUserByMaxId();

    List<OauthUser> getOauthUserByParam(OauthUser record);

    List<OauthUser> queryOauthUserByParam(OauthUser record);

    List<OauthUser> getOauthUserByCondition(String field, Object value);

    long countOauthUserBySelectCondition(String field, Object value);

    List<OauthUser> getOauthUserByCondition(String field, Object value, Integer page, Integer limit);

    List<OauthUser> queryOauthUserByCondition(String field, Object value);

    long countOauthUserByQueryCondition(String field, Object value);

    List<OauthUser> queryOauthUserByCondition(String field, Object value, Integer page, Integer limit);

    List<OauthUser> getOauthUserFieldsByCondition(String fields, String field, Object value);

    List<OauthUser> getOauthUserFieldsByCondition(String fields, String field, Object value, Integer page, Integer limit);

    List<OauthUser> queryOauthUserFieldsByCondition(String fields, String field, Object value);

    List<OauthUser> queryOauthUserFieldsByCondition(String fields, String field, Object value, Integer page, Integer limit);

    List<OauthUser> getOauthUserBySql(String sql);

    List<OauthUser> getOauthUserFieldsBySql(String fields, String sql);

    int insertOauthUser(OauthUser record);

    int setOauthUserById(OauthUser record);

    int delOauthUserById(Integer id);

    long countAllOauthUser();

    int delAllOauthUser();
}