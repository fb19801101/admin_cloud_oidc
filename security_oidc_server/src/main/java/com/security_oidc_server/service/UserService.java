package com.security_oidc_server.service;

import com.security_oidc_server.service.dto.UserFormDto;
import com.security_oidc_server.service.dto.UserListDto;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public interface UserService {


    UserListDto loadUserListDto(UserListDto listDto);

    boolean isExistedUsername(String username);

    String saveUserForm(UserFormDto formDto);

    boolean archiveUserByUuid(String uuid);
}
