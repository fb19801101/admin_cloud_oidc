package com.provider_oidc_gongcheng.domain.shared;


import com.provider_oidc_gongcheng.domain.RPHolder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class Application implements InitializingBean {

	/**
	 * 系统字符编码
	 */
	public static final String ENCODING = "UTF-8";

	/**
	 * Current  project home
	 */
	public static final String PROJECT_HOME = "https://github.com/monkeyk/MyOIDC";

	/**
	 * Current  version
	 */
	public static final String VERSION = "1.1.1";


	/**
	 * API注册信息
	 */
	private static RPHolder apiRPHolder = new RPHolder();


	/**
	 * application host
	 */
	private static String host;

	/**
	 * application host
	 */
	private static String[] split;


	/**
	 * default
	 */
	public Application() {
	}


	public static String host() {
		return host;
	}

	public static String host(Integer port) {
		if(split.length > 2) {
			return split[0]+":"+split[1]+":"+port+"/";
		}
		return host;
	}

	public static String host(String param) {
		int i = param.indexOf("/");
		return i == 0 ? host+param.substring(1) : host+param;
	}

	public static String ip() {
		if(split.length > 2) {
			int i = split[1].lastIndexOf("/");
			return i != -1 ? split[1].substring(i+1) : host;
		}
		return host;
	}

	public static Integer port() {
		if(split.length > 2) {
			int i = split[2].indexOf("/");
			return Integer.parseInt(i != -1 ? split[2].substring(0,i) : "-1");
		}
		return -1;
	}

	public void setHost(String host) {
		Application.host = host.endsWith("/") ? host : host + "/";
		split = host.split(":");
	}

	public static RPHolder getApiRPHolder() {
		return apiRPHolder;
	}

	public static void setApiRPHolder(RPHolder rpHolder) {
		apiRPHolder = rpHolder;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(host, "host is null");
	}
}