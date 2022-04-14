package com.provider_oidc_jiliang.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 股份公司userinfo对象
 * 示例数据： {"sub":"hr|318751"}
 * 用户信息为单位代码和用户id的组合
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class UserInfoDto extends AbstractOauthDto {

    private static final long serialVersionUID = -4028887815415395655L;

    @JsonProperty("create_time")
    private long createTime;
    private String email;
    private String openid;

    private String phone;
    private String username;

    private List<String> privileges = new ArrayList<>();

    private String sub;
    private String providerId;
    private Integer userId;
    private String userAuth;
    private String userType;


    public UserInfoDto() {
    }

    public UserInfoDto(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public UserInfoDto(String value) {
        this.sub=value;
        // “.”和“|”都是转义字符，必须得加"\\";
        String[] strings = value.split("\\|");
        this.providerId = strings[0];
        this.userId = Integer.parseInt(strings[1]);
    }

    public UserInfoDto(String providerId, Integer userId) {
        this.providerId = providerId;
        this.userId = userId;

        this.sub=String.format("%s|%d",providerId,userId);
    }

    public UserInfoDto(String providerId, Integer userId, String userAuth, String userType) {
        this.providerId = providerId;
        this.userId = userId;
        this.userAuth = userAuth;
        this.userType = userType;

        this.sub=String.format("%s|%d",providerId,userId);
    }


    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }


    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(String userAuth) {
        this.userAuth = userAuth;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserInfoDto{");
        sb.append("createTime=").append(createTime);
        sb.append(", email='").append(email).append('\'');
        sb.append(", openid='").append(openid).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", privileges=").append(privileges);
        sb.append(", sub='").append(sub).append('\'');
        sb.append(", providerId='").append(providerId).append('\'');
        sb.append(", userId=").append(userId);
        sb.append(", userAuth='").append(userAuth).append('\'');
        sb.append(", userType='").append(userType).append('\'');
        sb.append(", errorDescription='").append(errorDescription).append('\'');
        sb.append(", error='").append(error).append('\'');
        sb.append(", originalText='").append(originalText).append('\'');
        sb.append('}');
        return sb.toString();
    }

}