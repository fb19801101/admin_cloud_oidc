package com.security_oidc_server.service.dto;


import com.security_oidc_server.domain.user.OauthUser;
import com.security_oidc_server.domain.user.Privilege;

import java.io.Serializable;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class UserJsonDto implements Serializable {


    private static final long serialVersionUID = 2310668385840569887L;

    private String openid;
//    private boolean archived;

    private String username;
    private String phone;
    private String email;

    private long create_time;

    private List<String> privileges = new ArrayList<>();

    public UserJsonDto() {
    }

    public UserJsonDto(OauthUser user) {
        this.openid = user.uuid();
//        this.archived = user.archived();
        this.username = user.username();
        this.create_time = user.createTime().getLong(ChronoField.MILLI_OF_SECOND);

        this.phone = user.phone();
        this.email = user.email();

        final List<Privilege> privilegeList = user.privileges();
        for (Privilege privilege : privilegeList) {
            this.privileges.add(privilege.name());
        }
    }


    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

//    public boolean isArchived() {
//        return archived;
//    }

//    public void setArchived(boolean archived) {
//        this.archived = archived;
//    }


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

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }
}