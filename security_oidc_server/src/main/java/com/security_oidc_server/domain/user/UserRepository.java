package com.security_oidc_server.domain.user;


import com.security_oidc_server.domain.shared.Repository;

import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public interface UserRepository extends Repository {


    OauthUser findLoginUserByUsername(String username);

    List<Privilege> findUserPrivileges(String userUuid);

    List<OauthUser> findUsersByUsername(String username);

    /**
     * 无 archived 条件
     *
     * @param username username
     * @return User
     * @since 1.1.1
     */
    OauthUser findUserByUsernameNoArchived(String username);
}