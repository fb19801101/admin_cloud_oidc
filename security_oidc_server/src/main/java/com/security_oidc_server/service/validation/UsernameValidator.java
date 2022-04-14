package com.security_oidc_server.service.validation;

import com.security_oidc_server.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class UsernameValidator implements ConstraintValidator<UsernameValidation, String> {


    private static final Logger LOG = LoggerFactory.getLogger(UsernameValidator.class);

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(username)) {
            LOG.debug("Username is blank");
            return false;
        }

        boolean existed = userService.isExistedUsername(username);
        if (existed) {
            LOG.debug("Username: {} existed", username);
            return false;
        }

        return true;
    }
}
