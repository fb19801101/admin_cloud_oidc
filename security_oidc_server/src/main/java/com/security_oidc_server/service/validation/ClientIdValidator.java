package com.security_oidc_server.service.validation;

import com.security_oidc_server.service.OauthService;
import com.security_oidc_server.service.dto.OauthClientDetailsDto;
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
public class ClientIdValidator implements ConstraintValidator<ClientIdValidation, String> {


    private static final Logger LOG = LoggerFactory.getLogger(ClientIdValidator.class);

    @Autowired
    private OauthService oauthService;

    @Override
    public boolean isValid(String clientId, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(clientId)) {
            LOG.debug("ClientId is blank");
            return false;
        }


        OauthClientDetailsDto detailsDto = oauthService.loadOauthClientDetailsDto(clientId);
        if (detailsDto != null) {
            LOG.warn("ClientId: {} existed", clientId);
            return false;
        }

        return true;
    }
}
