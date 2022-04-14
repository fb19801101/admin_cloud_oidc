package com.gateway.service.impl;

import com.gateway.entity.OauthUserPrivilege;
import com.gateway.mapper.OauthUserPrivilegeMapper;
import com.gateway.service.OauthUserPrivilegeService;
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
public class OauthUserPrivilegeServiceImpl implements OauthUserPrivilegeService {
    @Autowired
    private OauthUserPrivilegeMapper oauthUserPrivilegeMapper;

    @Override
    public List<OauthUserPrivilege> getAllOauthUserPrivilege() {
        return oauthUserPrivilegeMapper.selectAllModel();
    }

    @Override
    public List<OauthUserPrivilege> getAllOauthUserPrivilege(Integer page, Integer limit) {
        return oauthUserPrivilegeMapper.selectAllModel((page-1)*limit,limit);
    }

    @Override
    public OauthUserPrivilege getOauthUserPrivilegeById(Integer id) {
        return oauthUserPrivilegeMapper.selectByPrimaryKey(id);
    }

    @Override
    public OauthUserPrivilege getOauthUserPrivilegeByMinId() {
        return oauthUserPrivilegeMapper.selectByMinPrimaryKey();
    }

    @Override
    public OauthUserPrivilege getOauthUserPrivilegeByMaxId() {
        return oauthUserPrivilegeMapper.selectByMaxPrimaryKey();
    }

    @Override
    public List<OauthUserPrivilege> getOauthUserPrivilegeByParam(OauthUserPrivilege record) {
        return oauthUserPrivilegeMapper.selectByParam(record);
    }

    @Override
    public List<OauthUserPrivilege> queryOauthUserPrivilegeByParam(OauthUserPrivilege record) {
        return oauthUserPrivilegeMapper.queryByParam(record);
    }

    @Override
    public List<OauthUserPrivilege> getOauthUserPrivilegeByCondition(String field, Object value) {
        return oauthUserPrivilegeMapper.selectByCondition(field,value);
    }

    @Override
    public long countOauthUserPrivilegeBySelectCondition(String field, Object value) {
        return oauthUserPrivilegeMapper.countBySelectCondition(field,value);
    }

    @Override
    public List<OauthUserPrivilege> getOauthUserPrivilegeByCondition(String field, Object value, Integer page, Integer limit) {
        return oauthUserPrivilegeMapper.selectByCondition(field,value,(page-1)*limit,limit);
    }

    @Override
    public List<OauthUserPrivilege> queryOauthUserPrivilegeByCondition(String field, Object value) {
        return oauthUserPrivilegeMapper.queryByCondition(field,value);
    }

    @Override
    public long countOauthUserPrivilegeByQueryCondition(String field, Object value) {
        return oauthUserPrivilegeMapper.countByQueryCondition(field,value);
    }

    @Override
    public List<OauthUserPrivilege> queryOauthUserPrivilegeByCondition(String field, Object value, Integer page, Integer limit) {
        return oauthUserPrivilegeMapper.queryByCondition(field,value,(page-1)*limit,limit);
    }

    @Override
    public List<OauthUserPrivilege> getOauthUserPrivilegeFieldsByCondition(String fields, String field, Object value) {
        return oauthUserPrivilegeMapper.selectFieldsByCondition(fields,field,value);
    }

    @Override
    public List<OauthUserPrivilege> getOauthUserPrivilegeFieldsByCondition(String fields, String field, Object value, Integer page, Integer limit) {
        return oauthUserPrivilegeMapper.selectFieldsByCondition(fields,field,value,(page-1)*limit,limit);
    }

    @Override
    public List<OauthUserPrivilege> queryOauthUserPrivilegeFieldsByCondition(String fields, String field, Object value) {
        return oauthUserPrivilegeMapper.queryFieldsByCondition(fields,field,value);
    }

    @Override
    public List<OauthUserPrivilege> queryOauthUserPrivilegeFieldsByCondition(String fields, String field, Object value, Integer page, Integer limit) {
        return oauthUserPrivilegeMapper.queryFieldsByCondition(fields,field,value,(page-1)*limit,limit);
    }

    @Override
    public List<OauthUserPrivilege> getOauthUserPrivilegeBySql(String sql) {
        return oauthUserPrivilegeMapper.selectBySql(sql);
    }

    @Override
    public List<OauthUserPrivilege> getOauthUserPrivilegeFieldsBySql(String field, String sql) {
        return oauthUserPrivilegeMapper.selectFieldsBySql(field,sql);
    }

    @Override
    public int insertOauthUserPrivilege(OauthUserPrivilege record) {
        return oauthUserPrivilegeMapper.insert(record);
    }

    @Override
    public int setOauthUserPrivilegeById(OauthUserPrivilege record) {
        return oauthUserPrivilegeMapper.updateByPrimaryKey(record);
    }

    @Override
    public long countAllOauthUserPrivilege() {
        return oauthUserPrivilegeMapper.countAllModel();
    }

    @Override
    public int delOauthUserPrivilegeById(Integer id) {
        return oauthUserPrivilegeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int delAllOauthUserPrivilege() {
        return oauthUserPrivilegeMapper.deleteAllModel();
    }

    @Override
    public List<OauthUserPrivilege> queryByUserId(Integer userId) {
        return oauthUserPrivilegeMapper.queryByUserId(userId);
    }
}