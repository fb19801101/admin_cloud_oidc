package com.security_oidc_server.config;


import com.security_oidc_server.domain.shared.Application;
import com.security_oidc_server.web.filter.ExtCharacterEncodingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring MVC 扩展配置
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Configuration
public class MVCConfigurer implements WebMvcConfigurer {


    /**
     * 扩展拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        WebMvcConfigurer.super.addInterceptors(registry);
    }


    /**
     * 字符编码配置 UTF-8
     */
    @Bean
    public FilterRegistrationBean encodingFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ExtCharacterEncodingFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }


    /**
     * 解决乱码问题
     * For UTF-8
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
        converters.add(new StringHttpMessageConverter(Charset.forName(Application.ENCODING)));
    }

    /**
     * 全局CORS配置
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  //项目中的所有接口都支持跨域
                        .allowedOrigins("*")  //所有地址都可以访问，也可以配置具体地址
                        .allowCredentials(true) //是否允许请求带有验证信息
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")  //"GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"
                        .allowedHeaders("*")  //允许的请求头
                        .maxAge(3600);  // 跨域允许时间
            }
        };
    }


    @Resource(name="thymeleafViewResolver")
    private ThymeleafViewResolver thymeleafViewResolver;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        if (thymeleafViewResolver != null) {
            String host = Application.host();
            host = host.substring(0,host.lastIndexOf('/'));
            Map<String, Object> vars = new HashMap<>();
            vars.put("static_path", host);
            vars.put("public_path", host);
            thymeleafViewResolver.setStaticVariables(vars);
        }

        WebMvcConfigurer.super.configureViewResolvers(registry);
    }

}
