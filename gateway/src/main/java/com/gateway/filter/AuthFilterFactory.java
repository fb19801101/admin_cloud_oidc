package com.gateway.filter;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 局部过滤器
 * 网关处添加过滤器，拦截登陆请求的响应信息
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-11-04 9:54
 */
@Service
public class AuthFilterFactory extends AbstractGatewayFilterFactory<Object> {
    private Logger logger = LoggerFactory.getLogger(AuthFilterFactory.class);
    @Override
    public GatewayFilter apply(Object config) {
       
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
                List<String> accountInfo = exchange.getResponse().getHeaders().get("Authorization");
                exchange.getResponse().getHeaders().remove("Authorization");//移除包头中的用户信息不需要返回给客户端
                if(accountInfo != null && accountInfo.size() > 0) {
                    String gmAccountInfoJson = accountInfo.get(0);//获取header中的用户信息
                    //将信息放在session中，在后面认证的请求中使用
                    exchange.getSession().block().getAttributes().put("Authorization", gmAccountInfoJson);
                }

                logger.debug("登陆返回信息:{}",accountInfo);
        }));
    }
}
