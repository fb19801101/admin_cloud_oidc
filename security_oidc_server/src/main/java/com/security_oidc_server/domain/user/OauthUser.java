package com.security_oidc_server.domain.user;


import com.security_oidc_server.domain.AbstractDomain;
import com.security_oidc_server.domain.security.SecurityUtils;
import com.security_oidc_server.domain.shared.BeanProvider;
import com.security_oidc_server.infrastructure.PasswordHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * From spring-oauth-server
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Entity
@Table(name = "oauth_user")
public class OauthUser extends AbstractDomain {

    private static final long serialVersionUID = -7873396364481790308L;

    private static final Logger LOG = LoggerFactory.getLogger(OauthUser.class);

    private transient UserRepository userRepository = BeanProvider.getBean(UserRepository.class);

    @Column(name = "username", unique = true)
    private String username;

    /**
     * Encrypted
     */
    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    //Default user is initial when create database, do not delete
    @Column(name = "default_user")
    private boolean defaultUser = false;

    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    /*
    * Register is null; otherwise is current logged user
    * */
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private OauthUser creator;


    public OauthUser() {
    }

    public boolean registerUser() {
        return !defaultUser && creator == null;
    }

    public OauthUser creator() {
        return creator;
    }

    public OauthUser creator(OauthUser creator) {
        this.creator = creator;
        return this;
    }

    public List<Privilege> privileges() {
        if (this.defaultUser) {
            LOG.debug("Default user: {}, all-privileges", this);
            return Arrays.asList(Privilege.values());
        }
        return userRepository.findUserPrivileges(this.uuid);
    }

    public boolean defaultUser() {
        return defaultUser;
    }

    public boolean defaultUser(boolean defaultUser) {
        this.defaultUser = defaultUser;
        return defaultUser;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public OauthUser password(String password) {
        this.password = password;
        return this;
    }

    public String phone() {
        return phone;
    }

    public String email() {
        return email;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{username='").append(username).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", defaultUser='").append(defaultUser).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public OauthUser email(String email) {
        this.email = email;
        return this;
    }

    public OauthUser phone(String phone) {
        this.phone = phone;
        return this;
    }

    public OauthUser username(String username) {
        this.username = username;
        return this;
    }


    /**
     * 重置密码
     *
     * @return newPassword
     * @since 1.1.0
     */
    public String resetPassword() {
        String newOriginalPass = PasswordHandler.randomPassword();
        this.password = PasswordHandler.encode(newOriginalPass);
        LOG.debug("<{}> reset User [username={},uuid={}] password", SecurityUtils.currentUsername(), username, uuid);
        return newOriginalPass;
    }

    public LocalDateTime lastLoginTime() {
        return lastLoginTime;
    }

    public void lastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }


    /**
     * 逻辑删除
     *
     * @return this
     * @since 1.1.0
     */
    public OauthUser archiveMe() {
        this.archived(true);
        LOG.debug("<{}> archived User: {} [Logic delete]", SecurityUtils.currentUsername(), this);
        return this;
    }

    /**
     * 更新密码
     *
     * @param newPassword 新密码
     * @return this
     * @since 1.1.0
     */
    public OauthUser updatePassword(String newPassword) {
        this.password = PasswordHandler.encode(newPassword);
        LOG.debug("<{}> update user[{}] password", SecurityUtils.currentUsername(), this);
        return this;
    }

}