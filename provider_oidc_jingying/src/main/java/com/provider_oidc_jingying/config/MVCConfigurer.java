package com.provider_oidc_jingying.config;



import com.provider_oidc_jingying.domain.shared.Application;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Spring MVC 扩展配置
 * 如果在配置类上添加了@Configuration注解，并且该类在@ComponentScan所扫描的包中，那么该类中的配置信息就会被所有的@FeignClient共享。
 * 最佳实践是：不指定@Configuration注解（或者指定configuration，用注解忽略），而是手动：
 * @FeignClient(value = "provider-other", configuration = FeignAuthConfiguration.class)
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
     * 解决乱码问题
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
                        .allowedMethods("*")  //"GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"
                        .allowedHeaders("*")  //允许的请求头
                        .maxAge(3600);  // 跨域允许时间
            }
        };
    }

}
