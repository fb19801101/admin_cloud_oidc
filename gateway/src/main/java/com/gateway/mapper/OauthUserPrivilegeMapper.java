package com.gateway.mapper;

import com.gateway.entity.OauthUserPrivilege;
import com.gateway.entity.OauthUserPrivilegeExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 通用返回对象
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-11-04 9:54
 */
@Mapper
public interface OauthUserPrivilegeMapper extends MyBatisBaseMapper<OauthUserPrivilege, Integer, OauthUserPrivilegeExample> {
//    @ResultMap("BaseResultMap")
//    @Select("<script>\n"+
//            "select * from oauth_user_privilege\n" +
//            "<where>\n" +
//            "  <if test=\"_parameter != null and _parameter.containsKey('userId') and userId != null\">\n" +
//            "    user_id like concat('%', #{userId}, '%')\n" +
//            "  </if>\n" +
//            "</where>\n" +
//            "</script>")
    List<OauthUserPrivilege> queryByUserId(@Param("userId") Integer userId);
}