package com.provider_oidc_login.service;

import com.provider_oidc_login.entity.Login;

import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-10-30 7:56
 */
public interface LoginService {
    List<Login> getAllLogin();

    List<Login> getAllLogin(Integer page, Integer limit);

    Login getLoginById(Integer id);

    Login getLoginByMinId();

    Login getLoginByMaxId();

    List<Login> getLoginByName(String name);

    Login getLoginByUsername(String username);

    List<Login> getLoginByParam(Login record);

    List<Login> queryLoginByParam(Login record);

    List<Login> getLoginByCondition(String field, Object value);

    long countLoginBySelectCondition(String field, Object value);

    List<Login> getLoginByCondition(String field, Object value, Integer page, Integer limit);

    List<Login> queryLoginByCondition(String field, Object value);

    long countLoginByQueryCondition(String field, Object value);

    List<Login> queryLoginByCondition(String field, Object value, Integer page, Integer limit);

    List<Login> getLoginFieldsByCondition(String fields, String field, Object value);

    List<Login> getLoginFieldsByCondition(String fields, String field, Object value, Integer page, Integer limit);

    List<Login> queryLoginFieldsByCondition(String fields, String field, Object value);

    List<Login> queryLoginFieldsByCondition(String fields, String field, Object value, Integer page, Integer limit);

    List<Login> getLoginBySql(String sql);

    List<Login> getLoginFieldsBySql(String fields, String sql);

    int insertLogin(Login record);

    int setLoginById(Login record);

    int delLoginById(Integer id);

    long countAllLogin();

    int delAllLogin();
}