package com.security_oidc_server.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class UserListDto implements Serializable {
    private static final long serialVersionUID = 2023379587030489248L;


    private String username;


    private List<UserDto> userDtos = new ArrayList<>();


    public UserListDto() {
    }

    public int getSize() {
        return userDtos.size();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<UserDto> getUserDtos() {
        return userDtos;
    }

    public void setUserDtos(List<UserDto> userDtos) {
        this.userDtos = userDtos;
    }
}
