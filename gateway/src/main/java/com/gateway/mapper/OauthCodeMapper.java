package com.gateway.mapper;

import com.gateway.entity.OauthCode;
import com.gateway.entity.OauthCodeExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 通用返回对象
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-11-04 9:54
 */
@Mapper
public interface OauthCodeMapper extends MyBatisBaseMapper<OauthCode, String, OauthCodeExample> {
}