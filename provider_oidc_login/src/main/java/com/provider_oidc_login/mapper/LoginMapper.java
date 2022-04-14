package com.provider_oidc_login.mapper;

import com.provider_oidc_login.entity.Login;
import com.provider_oidc_login.entity.LoginExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-10-30 7:56
 */
@Mapper
public interface LoginMapper extends MyBatisBaseMapper<Login, Integer, LoginExample> {
    @Select("select * from tb_login where name = #{name}")
    List<Login> selectByName(@Param("name") String name);

    @Select("select * from tb_login where username = #{username}")
    Login selectByUsername(@Param("username") String username);
}