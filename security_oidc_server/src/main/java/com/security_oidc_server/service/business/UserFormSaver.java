package com.security_oidc_server.service.business;

import com.security_oidc_server.domain.security.SecurityUtils;
import com.security_oidc_server.domain.shared.BeanProvider;
import com.security_oidc_server.domain.user.OauthPrivilege;
import com.security_oidc_server.domain.user.OauthUser;
import com.security_oidc_server.domain.user.Privilege;
import com.security_oidc_server.domain.user.UserRepository;
import com.security_oidc_server.web.WebUtils;
import com.security_oidc_server.service.dto.UserFormDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class UserFormSaver {

    private static final Logger LOG = LoggerFactory.getLogger(UserFormSaver.class);


    private UserRepository userRepository = BeanProvider.getBean(UserRepository.class);

//    private PasswordEncoder passwordEncoder = BeanProvider.getBean(PasswordEncoder.class);


    private UserFormDto formDto;

    public UserFormSaver(UserFormDto formDto) {
        this.formDto = formDto;
    }

    public String save() {

        OauthUser user = formDto.newUser();
        user.creator(SecurityUtils.currentUser());
//        user.password(passwordEncoder.encode(formDto.getPassword()));
        userRepository.saveOrUpdate(user);

        List<Privilege> privileges = formDto.getPrivileges();
        for (Privilege privilege : privileges) {
            OauthPrivilege userPrivilege = new OauthPrivilege(user, privilege);
            userRepository.saveOrUpdate(userPrivilege);
        }

        LOG.debug("{}|Save User: {}", WebUtils.getIp(), user);
        return user.uuid();
    }
}
