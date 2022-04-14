package com.security_oidc_server.service.impl;

import com.security_oidc_server.domain.user.OauthUser;
import com.security_oidc_server.domain.user.UserRepository;
import com.security_oidc_server.service.UserService;
import com.security_oidc_server.service.business.UserFormSaver;
import com.security_oidc_server.service.dto.UserListDto;
import com.security_oidc_server.web.WebUtils;
import com.security_oidc_server.service.dto.UserFormDto;
import com.security_oidc_server.service.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional(readOnly = true)
    public UserListDto loadUserListDto(UserListDto listDto) {
        //暂不支持分页
        List<OauthUser> users = userRepository.findUsersByUsername(listDto.getUsername());
        listDto.setUserDtos(UserDto.toDtos(users));
        return listDto;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistedUsername(String username) {
        final OauthUser user = userRepository.findUserByUsernameNoArchived(username);
        return user != null;
    }

    @Override
    @Transactional()
    public String saveUserForm(UserFormDto formDto) {
        UserFormSaver saver = new UserFormSaver(formDto);
        return saver.save();
    }

    @Override
    @Transactional()
    public boolean archiveUserByUuid(String uuid) {
        OauthUser user = userRepository.findByUuid(OauthUser.class, uuid);
        if (user == null || user.defaultUser()) {
            LOG.debug("{}|Null or defaultUser: {}", WebUtils.getIp(), user);
            return false;
        }
        user.archiveMe();
        return true;
    }
}
