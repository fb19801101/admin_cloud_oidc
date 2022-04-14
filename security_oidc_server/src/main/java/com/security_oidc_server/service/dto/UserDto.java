package com.security_oidc_server.service.dto;


import com.security_oidc_server.domain.user.OauthUser;
import com.security_oidc_server.domain.user.Privilege;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * * From spring-oauth-server
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class UserDto implements Serializable {
    private static final long serialVersionUID = -2502329463915439215L;


    private String uuid;

    private String username;

    private String phone;
    private String email;

    private String createTime;

    private List<Privilege> privileges = new ArrayList<>();

    private boolean defaultUser = false;

    private String lastLoginTime;

    private String creatorName;

    public UserDto() {
    }


    public UserDto(OauthUser user) {
        this.uuid = user.uuid();
        this.username = user.username();
        this.phone = user.phone();
        this.email = user.email();

        this.privileges = user.privileges();
        this.createTime = user.createTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.defaultUser = user.defaultUser();

        LocalDateTime lastLoginTime = user.lastLoginTime();
        if (lastLoginTime != null) {
            this.lastLoginTime = lastLoginTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
        OauthUser creator = user.creator();
        if (creator != null) {
            this.createTime = creator.username();
        }
    }

    public boolean isDefaultUser() {
        return defaultUser;
    }

    public void setDefaultUser(boolean defaultUser) {
        this.defaultUser = defaultUser;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public static List<UserDto> toDtos(List<OauthUser> users) {
        List<UserDto> dtos = new ArrayList<>(users.size());
        for (OauthUser user : users) {
            dtos.add(new UserDto(user));
        }
        return dtos;
    }
}
