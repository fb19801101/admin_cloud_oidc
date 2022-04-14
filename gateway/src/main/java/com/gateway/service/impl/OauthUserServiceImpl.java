package com.gateway.service.impl;

import com.gateway.mapper.OauthUserMapper;
import com.gateway.entity.OauthUser;
import com.gateway.service.OauthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-02-13 13:11
 */
@Service
public class OauthUserServiceImpl implements OauthUserService {
    @Autowired
    private OauthUserMapper oauthUserMapper;

    @Override
    public List<OauthUser> getAllOauthUser() {
        return oauthUserMapper.selectAllModel();
    }

    @Override
    public List<OauthUser> getAllOauthUser(Integer page, Integer limit) {
        return oauthUserMapper.selectAllModel((page-1)*limit,limit);
    }

    @Override
    public OauthUser getOauthUserById(Integer id) {
        return oauthUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public OauthUser getOauthUserByMinId() {
        return oauthUserMapper.selectByMinPrimaryKey();
    }

    @Override
    public OauthUser getOauthUserByMaxId() {
        return oauthUserMapper.selectByMaxPrimaryKey();
    }

    @Override
    public List<OauthUser> getOauthUserByParam(OauthUser record) {
        return oauthUserMapper.selectByParam(record);
    }

    @Override
    public List<OauthUser> queryOauthUserByParam(OauthUser record) {
        return oauthUserMapper.queryByParam(record);
    }

    @Override
    public List<OauthUser> getOauthUserByCondition(String field, Object value) {
        return oauthUserMapper.selectByCondition(field,value);
    }

    @Override
    public long countOauthUserBySelectCondition(String field, Object value) {
        return oauthUserMapper.countBySelectCondition(field,value);
    }

    @Override
    public List<OauthUser> getOauthUserByCondition(String field, Object value, Integer page, Integer limit) {
        return oauthUserMapper.selectByCondition(field,value,(page-1)*limit,limit);
    }

    @Override
    public List<OauthUser> queryOauthUserByCondition(String field, Object value) {
        return oauthUserMapper.queryByCondition(field,value);
    }

    @Override
    public long countOauthUserByQueryCondition(String field, Object value) {
        return oauthUserMapper.countByQueryCondition(field,value);
    }

    @Override
    public List<OauthUser> queryOauthUserByCondition(String field, Object value, Integer page, Integer limit) {
        return oauthUserMapper.queryByCondition(field,value,(page-1)*limit,limit);
    }

    @Override
    public List<OauthUser> getOauthUserFieldsByCondition(String fields, String field, Object value) {
        return oauthUserMapper.selectFieldsByCondition(fields,field,value);
    }

    @Override
    public List<OauthUser> getOauthUserFieldsByCondition(String fields, String field, Object value, Integer page, Integer limit) {
        return oauthUserMapper.selectFieldsByCondition(fields,field,value,(page-1)*limit,limit);
    }

    @Override
    public List<OauthUser> queryOauthUserFieldsByCondition(String fields, String field, Object value) {
        return oauthUserMapper.queryFieldsByCondition(fields,field,value);
    }

    @Override
    public List<OauthUser> queryOauthUserFieldsByCondition(String fields, String field, Object value, Integer page, Integer limit) {
        return oauthUserMapper.queryFieldsByCondition(fields,field,value,(page-1)*limit,limit);
    }

    @Override
    public List<OauthUser> getOauthUserBySql(String sql) {
        return oauthUserMapper.selectBySql(sql);
    }

    @Override
    public List<OauthUser> getOauthUserFieldsBySql(String field, String sql) {
        return oauthUserMapper.selectFieldsBySql(field,sql);
    }

    @Override
    public int insertOauthUser(OauthUser record) {
        return oauthUserMapper.insert(record);
    }

    @Override
    public int setOauthUserById(OauthUser record) {
        return oauthUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public long countAllOauthUser() {
        return oauthUserMapper.countAllModel();
    }

    @Override
    public int delOauthUserById(Integer id) {
        return oauthUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int delAllOauthUser() {
        return oauthUserMapper.deleteAllModel();
    }
}