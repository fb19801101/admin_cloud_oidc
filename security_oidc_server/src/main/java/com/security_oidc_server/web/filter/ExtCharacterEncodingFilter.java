package com.security_oidc_server.web.filter;


import com.security_oidc_server.domain.shared.Application;
import com.security_oidc_server.web.WebUtils;
import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  Wrap the spring <i>CharacterEncodingFilter</i>, add retrieve client ip action
 *
 *  扩展 默认的 CharacterEncodingFilter, 添加对IP 地址的获取
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class ExtCharacterEncodingFilter extends OrderedCharacterEncodingFilter {


    public ExtCharacterEncodingFilter() {
        setEncoding(Application.ENCODING);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        persistIp(request);
        super.doFilterInternal(request, response, filterChain);

    }

    /**
     * 将IP地址 放置在 ThreadLocal 中
     */
    private void persistIp(HttpServletRequest request) {
        final String clientIp = WebUtils.retrieveClientIp(request);
        WebUtils.setIp(clientIp);
    }


}