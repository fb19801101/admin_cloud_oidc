/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.provider_oidc_wuzigys.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.provider_oidc_wuzigys.domain.DateTimeSerializer;
import com.provider_oidc_wuzigys.domain.DatetimeDeserializer;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lengleng
 * @date 2019/2/1 RestTemplate
 */
@Configuration
@Component
@ConditionalOnClass(value = {RestTemplate.class, CloseableHttpClient.class})
public class RestTemplateConfigurer {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 启动的时候要注意，由于我们在服务中注入了RestTemplate，所以启动的时候需要实例化该类的一个实例
	 */
	@Autowired
	private RestTemplateBuilder builder;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private HttpClientPoolConfigurer httpClientPoolConfig;


	/**
	 * 使用RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例
	 */
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = builder.build();

		//注册model,用于实现jackson joda time序列化和反序列化
		SimpleModule module = new SimpleModule();
		module.addSerializer(DateTime.class, new DateTimeSerializer());
		module.addDeserializer(DateTime.class, new DatetimeDeserializer());
		objectMapper.registerModule(module);

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(objectMapper);

		//不加可能会出现异常
		//Could not extract response: no suitable HttpMessageConverter found for response type [class ]
		MediaType[] mediaTypes = new MediaType[] {
				MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_OCTET_STREAM,

				MediaType.TEXT_HTML,
				MediaType.TEXT_PLAIN,
				MediaType.TEXT_XML,
				MediaType.APPLICATION_STREAM_JSON,
				MediaType.APPLICATION_ATOM_XML,
				MediaType.APPLICATION_FORM_URLENCODED,
				MediaType.APPLICATION_JSON_UTF8,
				MediaType.APPLICATION_PDF,
		};

		converter.setSupportedMediaTypes(Arrays.asList(mediaTypes));

		try {
			//通过反射设置MessageConverters
			Field field = restTemplate.getClass().getDeclaredField("messageConverters");
			field.setAccessible(true);

			List<HttpMessageConverter<?>> orgConverterList = (List<HttpMessageConverter<?>>) field.get(restTemplate);

			Optional<HttpMessageConverter<?>> opConverter = orgConverterList.stream()
					.filter(x -> x.getClass()
							.getName()
							.equalsIgnoreCase(MappingJackson2HttpMessageConverter.class.getName()))
					.findFirst();

			if (!opConverter.isPresent()) {
				return restTemplate;
			}

			//添加MappingJackson2HttpMessageConverter
			messageConverters.add(converter);

			//添加原有的剩余的HttpMessageConverter
			List<HttpMessageConverter<?>> leftConverters = orgConverterList.stream()

					.filter(x -> !x.getClass()
							.getName()
							.equalsIgnoreCase(MappingJackson2HttpMessageConverter.class.getName()))
					.collect(Collectors.toList());

			messageConverters.addAll(leftConverters);

			System.out.println(String.format("【HttpMessageConverter】原有数量：%s，重新构造后数量：%s",
					orgConverterList.size(),
					messageConverters.size()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		restTemplate.setMessageConverters(messageConverters);
		restTemplate.setRequestFactory(clientHttpRequestFactory());

		//我们采用RestTemplate内部的MessageConverter
		//重新设置StringHttpMessageConverter字符集，解决中文乱码问题
		modifyDefaultCharset(restTemplate);

		//设置错误处理器
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler());


		return restTemplate;
	}

	/**
	 * 创建HTTP客户端工厂
	 */
	@Bean(name = "clientHttpRequestFactory")
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		// maxTotalConnection 和 maxConnectionPerRoute 必须要配
		if (httpClientPoolConfig.getMaxTotalConnect() <= 0) {
			throw new IllegalArgumentException("invalid maxTotalConnection: " + httpClientPoolConfig.getMaxTotalConnect());
		}
		if (httpClientPoolConfig.getMaxConnectPerRoute() <= 0) {
			throw new IllegalArgumentException("invalid maxConnectionPerRoute: " + httpClientPoolConfig.getMaxConnectPerRoute());
		}
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
		// 连接超时
		clientHttpRequestFactory.setConnectTimeout(httpClientPoolConfig.getConnectTimeout());
		// 数据读取超时时间，即SocketTimeout
		clientHttpRequestFactory.setReadTimeout(httpClientPoolConfig.getReadTimeout());
		// 从连接池获取请求连接的超时时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
		clientHttpRequestFactory.setConnectionRequestTimeout(httpClientPoolConfig.getConnectionRequestTimeout());

		return clientHttpRequestFactory;
	}

	/**
	 * 配置httpClient
	 *
	 * @return
	 */
	@Bean(name = "httpClient")
	public HttpClient httpClient() {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		try {
			//设置信任ssl访问
			SSLContext sslContext = new SSLContextBuilder()
					.loadTrustMaterial(null, (arg0, arg1) -> true)
					.build();

			httpClientBuilder.setSSLContext(sslContext);
			HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					// 注册http和https请求
					.register("http", PlainConnectionSocketFactory.getSocketFactory())
					.register("https", sslConnectionSocketFactory).build();

			//使用Httpclient连接池的方式配置(推荐)，同时支持netty，okHttp以及其他http框架
			PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			// 最大连接数
			poolingHttpClientConnectionManager.setMaxTotal(httpClientPoolConfig.getMaxTotalConnect());
			// 同路由并发数
			poolingHttpClientConnectionManager.setDefaultMaxPerRoute(httpClientPoolConfig.getMaxConnectPerRoute());
			// 设置长连接保持时间
			poolingHttpClientConnectionManager.setValidateAfterInactivity(httpClientPoolConfig.getValidateAfterInactivity());

			//配置连接池
			httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
			// 重试次数
			httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(httpClientPoolConfig.getRetryTimes(), true));

			//设置默认请求头
			List<Header> headers = getDefaultHeaders();
			httpClientBuilder.setDefaultHeaders(headers);

			//设置长连接保持策略
			httpClientBuilder.setKeepAliveStrategy(connectionKeepAliveStrategy());
			return httpClientBuilder.build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			log.error("初始化HTTP连接池出错", e);
		}
		return null;
	}


	/**
	 * 配置长连接保持策略
	 * @return
	 */
	public ConnectionKeepAliveStrategy connectionKeepAliveStrategy(){
		return (response, context) -> {
			// Honor 'keep-alive' header
			HeaderElementIterator it = new BasicHeaderElementIterator(
					response.headerIterator(HTTP.CONN_KEEP_ALIVE));
			while (it.hasNext()) {
				HeaderElement he = it.nextElement();
				String param = he.getName();
				String value = he.getValue();
				if (value != null && "timeout".equalsIgnoreCase(param)) {
					try {
						return Long.parseLong(value) * 1000;
					} catch(NumberFormatException ignore) {
						log.error("解析长连接过期时间异常",ignore);
					}
				}
			}
			HttpHost target = (HttpHost) context.getAttribute(
					HttpClientContext.HTTP_TARGET_HOST);

			//如果请求目标地址,单独配置了长连接保持时间,使用该配置
			Optional<Map.Entry<String, Integer>> any = Optional.ofNullable(httpClientPoolConfig.getKeepAliveTargetHost()).orElseGet(HashMap::new)
					.entrySet().stream().filter(
							e -> e.getKey().equalsIgnoreCase(target.getHostName())).findAny();

			//否则使用默认长连接保持时间
			return any.map(en -> en.getValue() * 1000L).orElse(httpClientPoolConfig.getKeepAliveTime() * 1000L);
		};
	}

	/**
	 * 设置请求头
	 *
	 * @return
	 */
	private List<Header> getDefaultHeaders() {
		List<Header> headers = new ArrayList<>();
		headers.add(new BasicHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
		headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
		headers.add(new BasicHeader("Accept-Language", "zh-CN"));
		headers.add(new BasicHeader("Connection", "Keep-Alive"));
		return headers;
	}

	/**
	 * 修改默认的字符集类型为utf-8
	 *
	 * @param restTemplate
	 */
	private void modifyDefaultCharset(RestTemplate restTemplate) {
		List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
		HttpMessageConverter<?> converterTarget = null;
		for (HttpMessageConverter<?> item : converterList) {
			if (StringHttpMessageConverter.class == item.getClass()) {
				converterTarget = item;
				break;
			}
		}
		if (null != converterTarget) {
			converterList.remove(converterTarget);
		}
		Charset defaultCharset = Charset.forName(httpClientPoolConfig.getCharset());
		converterList.add(1, new StringHttpMessageConverter(defaultCharset));
	}
}
