package com.provider_oidc_hrext.domain.auth;


import java.util.List;

/**
 * 处理 OAuth 相关业务的 Repository
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public interface AuthRoleDetailsRepository {

    AuthRoleDetails getAuthRoleDetailsById(Integer id);

    AuthRoleDetails getCurrentAuthRoleDetailsByUserId(Integer userId);

    List<AuthRoleDetails> getCurrentAuthRoleDetailsByAuthNode(Integer authNode);

    List<AuthRoleDetails> getCurrentAuthRoleDetailsByAuthCode(String authCode);

    List<AuthRoleDetails> getHistoryAuthRoleDetailsByUserId(Integer userId);

    List<AuthRoleDetails> getHistoryAuthRoleDetailsByAuthCode(String authCode);

    void insertAuthRoleDetails(AuthRoleDetails authRoleDetails);

    void updateAuthRoleDetails(AuthRoleDetails authRoleDetails);
}