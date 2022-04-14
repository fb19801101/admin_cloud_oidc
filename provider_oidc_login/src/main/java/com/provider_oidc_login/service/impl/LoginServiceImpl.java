package com.provider_oidc_login.service.impl;

import com.provider_oidc_login.entity.Login;
import com.provider_oidc_login.mapper.LoginMapper;
import com.provider_oidc_login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-10-30 7:56
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public List<Login> getAllLogin() {
        return loginMapper.selectAllModel();
    }

    @Override
    public List<Login> getAllLogin(Integer page, Integer limit) {
        return loginMapper.selectAllModel((page-1)*limit,limit);
    }

    @Override
    public Login getLoginById(Integer id) {
        return loginMapper.selectByPrimaryKey(id);
    }

    @Override
    public Login getLoginByMinId() {
        return loginMapper.selectByMinPrimaryKey();
    }

    @Override
    public Login getLoginByMaxId() {
        return loginMapper.selectByMaxPrimaryKey();
    }

    @Override
    public List<Login> getLoginByName(String name) {
        return loginMapper.selectByName(name);
    }

    @Override
    public Login getLoginByUsername(String username) {
        return loginMapper.selectByUsername(username);
    }

    @Override
    public List<Login> getLoginByParam(Login record) {
        return loginMapper.selectByParam(record);
    }

    @Override
    public List<Login> queryLoginByParam(Login record) {
        return loginMapper.queryByParam(record);
    }

    @Override
    public List<Login> getLoginByCondition(String field, Object value) {
        return loginMapper.selectByCondition(field,value);
    }

    @Override
    public long countLoginBySelectCondition(String field, Object value) {
        return loginMapper.countBySelectCondition(field,value);
    }

    @Override
    public List<Login> getLoginByCondition(String field, Object value, Integer page, Integer limit) {
        return loginMapper.selectByCondition(field,value,(page-1)*limit,limit);
    }

    @Override
    public List<Login> queryLoginByCondition(String field, Object value) {
        return loginMapper.queryByCondition(field,value);
    }

    @Override
    public long countLoginByQueryCondition(String field, Object value) {
        return loginMapper.countByQueryCondition(field,value);
    }

    @Override
    public List<Login> queryLoginByCondition(String field, Object value, Integer page, Integer limit) {
        return loginMapper.queryByCondition(field,value,(page-1)*limit,limit);
    }

    @Override
    public List<Login> getLoginFieldsByCondition(String fields, String field, Object value) {
        return loginMapper.selectFieldsByCondition(fields,field,value);
    }

    @Override
    public List<Login> getLoginFieldsByCondition(String fields, String field, Object value, Integer page, Integer limit) {
        return loginMapper.selectFieldsByCondition(fields,field,value,(page-1)*limit,limit);
    }

    @Override
    public List<Login> queryLoginFieldsByCondition(String fields, String field, Object value) {
        return loginMapper.queryFieldsByCondition(fields,field,value);
    }

    @Override
    public List<Login> queryLoginFieldsByCondition(String fields, String field, Object value, Integer page, Integer limit) {
        return loginMapper.queryFieldsByCondition(fields,field,value,(page-1)*limit,limit);
    }

    @Override
    public List<Login> getLoginBySql(String sql) {
        return loginMapper.selectBySql(sql);
    }

    @Override
    public List<Login> getLoginFieldsBySql(String field, String sql) {
        return loginMapper.selectFieldsBySql(field,sql);
    }

    @Override
    public int insertLogin(Login record) {
        return loginMapper.insert(record);
    }

    @Override
    public int setLoginById(Login record) {
        return loginMapper.updateByPrimaryKey(record);
    }

    @Override
    public int delLoginById(Integer id) {
        return loginMapper.deleteByPrimaryKey(id);
    }

    @Override
    public long countAllLogin() {
        return loginMapper.countAllModel();
    }

    @Override
    public int delAllLogin() {
        return loginMapper.deleteAllModel();
    }
}