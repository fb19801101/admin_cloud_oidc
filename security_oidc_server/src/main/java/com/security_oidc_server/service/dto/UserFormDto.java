package com.security_oidc_server.service.dto;


import com.security_oidc_server.domain.user.OauthUser;
import com.security_oidc_server.domain.user.Privilege;
import com.security_oidc_server.infrastructure.PasswordHandler;
import com.security_oidc_server.service.validation.UsernameValidation;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * From spring-oauth-server
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class UserFormDto implements Serializable {
    private static final long serialVersionUID = 7959857016962260738L;

    private String uuid;


    @UsernameValidation()
    private String username;


    private String phone;

    @Email(message = "邮箱格式不对")
    private String email;

    //密码要求至少 10位
    @NotBlank(message = "密码不能为空")
    @Length(min = 10, message = "密码长度最少10位")
    private String password;


    @Size(min = 1, message = "至少选择一个权限")
    private List<Privilege> privileges = new ArrayList<>();


    public UserFormDto() {
    }


    public Privilege[] getAllPrivileges() {
        return Privilege.values();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OauthUser newUser() {
        final OauthUser user = new OauthUser()
                .username(getUsername())
                .phone(getPhone())
                .email(getEmail())
                .password(PasswordHandler.encode(getPassword()));
        user.privileges().addAll(getPrivileges());
        return user;
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
}
