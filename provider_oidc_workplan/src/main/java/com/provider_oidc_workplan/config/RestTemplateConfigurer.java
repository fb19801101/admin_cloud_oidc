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

package com.provider_oidc_workplan.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.provider_oidc_workplan.domain.DateTimeSerializer;
import com.provider_oidc_workplan.domain.DatetimeDeserializer;
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
	 * ????????????????????????????????????????????????????????????RestTemplate????????????????????????????????????????????????????????????
	 */
	@Autowired
	private RestTemplateBuilder builder;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private HttpClientPoolConfigurer httpClientPoolConfig;


	/**
	 * ??????RestTemplateBuilder????????????RestTemplate?????????spring?????????????????????RestTemplateBuilder??????
	 */
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = builder.build();

		//??????model,????????????jackson joda time????????????????????????
		SimpleModule module = new SimpleModule();
		module.addSerializer(DateTime.class, new DateTimeSerializer());
		module.addDeserializer(DateTime.class, new DatetimeDeserializer());
		objectMapper.registerModule(module);

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(objectMapper);

		//???????????????????????????
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
			//??????????????????MessageConverters
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

			//??????MappingJackson2HttpMessageConverter
			messageConverters.add(converter);

			//????????????????????????HttpMessageConverter
			List<HttpMessageConverter<?>> leftConverters = orgConverterList.stream()

					.filter(x -> !x.getClass()
							.getName()
							.equalsIgnoreCase(MappingJackson2HttpMessageConverter.class.getName()))
					.collect(Collectors.toList());

			messageConverters.addAll(leftConverters);

			System.out.println(String.format("???HttpMessageConverter??????????????????%s???????????????????????????%s",
					orgConverterList.size(),
					messageConverters.size()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		restTemplate.setMessageConverters(messageConverters);
		restTemplate.setRequestFactory(clientHttpRequestFactory());

		//????????????RestTemplate?????????MessageConverter
		//????????????StringHttpMessageConverter????????????????????????????????????
		modifyDefaultCharset(restTemplate);

		//?????????????????????
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler());


		return restTemplate;
	}

	/**
	 * ??????HTTP???????????????
	 */
	@Bean(name = "clientHttpRequestFactory")
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		// maxTotalConnection ??? maxConnectionPerRoute ????????????
		if (httpClientPoolConfig.getMaxTotalConnect() <= 0) {
			throw new IllegalArgumentException("invalid maxTotalConnection: " + httpClientPoolConfig.getMaxTotalConnect());
		}
		if (httpClientPoolConfig.getMaxConnectPerRoute() <= 0) {
			throw new IllegalArgumentException("invalid maxConnectionPerRoute: " + httpClientPoolConfig.getMaxConnectPerRoute());
		}
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
		// ????????????
		clientHttpRequestFactory.setConnectTimeout(httpClientPoolConfig.getConnectTimeout());
		// ??????????????????????????????SocketTimeout
		clientHttpRequestFactory.setReadTimeout(httpClientPoolConfig.getReadTimeout());
		// ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
		clientHttpRequestFactory.setConnectionRequestTimeout(httpClientPoolConfig.getConnectionRequestTimeout());

		return clientHttpRequestFactory;
	}

	/**
	 * ??????httpClient
	 *
	 * @return
	 */
	@Bean(name = "httpClient")
	public HttpClient httpClient() {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		try {
			//????????????ssl??????
			SSLContext sslContext = new SSLContextBuilder()
					.loadTrustMaterial(null, (arg0, arg1) -> true)
					.build();

			httpClientBuilder.setSSLContext(sslContext);
			HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					// ??????http???https??????
					.register("http", PlainConnectionSocketFactory.getSocketFactory())
					.register("https", sslConnectionSocketFactory).build();

			//??????Httpclient????????????????????????(??????)???????????????netty???okHttp????????????http??????
			PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			// ???????????????
			poolingHttpClientConnectionManager.setMaxTotal(httpClientPoolConfig.getMaxTotalConnect());
			// ??????????????????
			poolingHttpClientConnectionManager.setDefaultMaxPerRoute(httpClientPoolConfig.getMaxConnectPerRoute());
			// ???????????????????????????
			poolingHttpClientConnectionManager.setValidateAfterInactivity(httpClientPoolConfig.getValidateAfterInactivity());

			//???????????????
			httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
			// ????????????
			httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(httpClientPoolConfig.getRetryTimes(), true));

			//?????????????????????
			List<Header> headers = getDefaultHeaders();
			httpClientBuilder.setDefaultHeaders(headers);

			//???????????????????????????
			httpClientBuilder.setKeepAliveStrategy(connectionKeepAliveStrategy());
			return httpClientBuilder.build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			log.error("?????????HTTP???????????????", e);
		}
		return null;
	}


	/**
	 * ???????????????????????????
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
						log.error("?????????????????????????????????",ignore);
					}
				}
			}
			HttpHost target = (HttpHost) context.getAttribute(
					HttpClientContext.HTTP_TARGET_HOST);

			//????????????????????????,????????????????????????????????????,???????????????
			Optional<Map.Entry<String, Integer>> any = Optional.ofNullable(httpClientPoolConfig.getKeepAliveTargetHost()).orElseGet(HashMap::new)
					.entrySet().stream().filter(
							e -> e.getKey().equalsIgnoreCase(target.getHostName())).findAny();

			//???????????????????????????????????????
			return any.map(en -> en.getValue() * 1000L).orElse(httpClientPoolConfig.getKeepAliveTime() * 1000L);
		};
	}

	/**
	 * ???????????????
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
	 * ?????????????????????????????????utf-8
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
