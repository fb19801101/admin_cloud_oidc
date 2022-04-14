package com.provider_oidc_login.mapper;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-10-30 7:56
 */
public interface MyBatisBaseMapper<Model, PK extends Serializable, E> {
    List<Model> selectByExample(E example);

    Model selectByPrimaryKey(PK id);

    Model selectByMinPrimaryKey();

    Model selectByMaxPrimaryKey();

    int deleteByExample(E example);

    int deleteByPrimaryKey(PK id);

    int insert(Model record);

    int insertSelective(Model record);

    long countByExample(E example);

    long countByModel(Model record);

    int updateByExampleSelective(@Param("record") Model record, @Param("example") E example);

    int updateByExample(@Param("record") Model record, @Param("example") E example);

    int updateByPrimaryKeySelective(Model record);

    int updateByPrimaryKey(Model record);

    int deleteAllModel();

    List<Model> selectAllModel();

    List<Model> selectAllModel(@Param("offset") Integer offset, @Param("limit") Integer limit);

    long countAllModel();

    List<Model> selectByParam(@Param("record") Model record);

    List<Model> selectByParam(@Param("record") Model record, @Param("offset") Integer offset, @Param("limit") Integer limit);

    long countBySelectParam(@Param("record") Model record);

    List<Model> queryByParam(@Param("record") Model record);

    long countByQueryParam(@Param("record") Model record);

    List<Model> queryByParam(@Param("record") Model record, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Model> selectByCondition(@Param("field") String field, @Param("value") Object value);

    long countBySelectCondition(@Param("field") String field, @Param("value") Object value);

    List<Model> selectByCondition(@Param("field") String field, @Param("value") Object value, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Model> selectByCondition(@Param("field") String field, @Param("value1") Object value1, @Param("value2") Object value2);

    long countBySelectCondition(@Param("field") String field, @Param("value1") Object value1, @Param("value2") Object value2);

    List<Model> selectByCondition(@Param("field") String field, @Param("value1") Object value1, @Param("value2") Object value2, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Model> queryByCondition(@Param("field") String field, @Param("value") Object value);

    long countByQueryCondition(@Param("field") String field, @Param("value") Object value);

    List<Model> queryByCondition(@Param("field") String field, @Param("value") Object value, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Model> selectFieldsByParam(@Param("fields") String fields, @Param("record") Model record);

    List<Model> selectFieldsByParam(@Param("fields") String fields, @Param("record") Model record, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Model> queryFieldsByParam(@Param("fields") String fields, @Param("record") Model record);

    List<Model> queryFieldsByParam(@Param("fields") String fields, @Param("record") Model record, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Model> selectFieldsByCondition(@Param("fields") String fields, @Param("field") String field, @Param("value") Object value);

    List<Model> selectFieldsByCondition(@Param("fields") String fields, @Param("field") String field, @Param("value") Object value, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Model> selectFieldsByCondition(@Param("fields") String fields, @Param("field") String field, @Param("value1") Object value1, @Param("value2") Object value2);

    List<Model> selectFieldsByCondition(@Param("fields") String fields, @Param("field") String field, @Param("value1") Object value1, @Param("value2") Object value2, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Model> queryFieldsByCondition(@Param("fields") String fields, @Param("field") String field, @Param("value") Object value);

    List<Model> queryFieldsByCondition(@Param("fields") String fields, @Param("field") String field, @Param("value") Object value, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Model> selectBySql(@Param("sql") String sql);

    List<Model> selectFieldsBySql(@Param("fields") String fields, @Param("sql") String sql);
}