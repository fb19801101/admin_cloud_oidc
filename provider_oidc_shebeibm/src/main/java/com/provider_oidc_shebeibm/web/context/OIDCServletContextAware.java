package com.provider_oidc_shebeibm.web.context;

import com.provider_oidc_shebeibm.domain.shared.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Component
public class OIDCServletContextAware implements ServletContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(OIDCServletContextAware.class);

    public OIDCServletContextAware() {
    }

    @Override
    public void setServletContext(ServletContext servletContext) {

        //主版本号
        servletContext.setAttribute("mainVersion", Application.VERSION);
        LOG.debug("Initialed: {}, mainVersion: {}", this, Application.VERSION);
    }
}
