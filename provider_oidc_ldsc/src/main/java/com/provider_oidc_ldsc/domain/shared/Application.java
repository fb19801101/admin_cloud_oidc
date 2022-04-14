package com.provider_oidc_ldsc.domain.shared;


import com.provider_oidc_ldsc.domain.RPHolder;
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
	 * 本服务器 host
	 */
	private static String host;

	/**
	 * 本服务器 host split
	 */
	private static String[] split;


	/**
	 * API注册信息
	 */
	private static RPHolder apiRPHolder = new RPHolder();
	private static String apiName = "api_ldsc";


	/**
	 * SSO登录 openid-config-url
	 */
	private static String openidConfigUrl;

	/**
	 * SSO退出 openid-logout-url
	 */
	private static String openidLogoutUrl;

	/**
	 * API注册 reg-api-url
	 */
	private static String regApiUrl;

	/**
	 * 人力资源 hr-api-url
	 */
	private static String hrApiUrl;

	/**
	 * 门户认证 mh-certs-url
	 */
	private static String mhCertsUrl;

	/**
	 * 门户认证KID mh-certs-kid
	 */
	private static String mhCertsKid;

	/**
	 * API认证 reg-certs-url
	 */
	private static String regCertsUrl;

	/**
	 * API认证KID reg-certs-kid
	 */
	private static String regCertsKid;


	/**
	 * 登录用户的 ProviderId
	 */
	private static String loginProviderId;

	/**
	 * 登录用户的 UserId
	 */
	private static Integer loginUserId;

	/**
	 * 登录用户的 Auth
	 */
	private static String loginUserAuth;

	/**
	 * 登录用户的 SSO OIDC Type
	 */
	private static String loginUserType;


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

	public static String getApiName() {
		return apiName;
	}

	public static void setApiName(String apiName) {
		Application.apiName = apiName;
	}


	public void setOpenidConfigUrl(String openidConfigUrl) {
		this.openidConfigUrl = openidConfigUrl;
	}

	public static String getOpenidConfigUrl() {
		return openidConfigUrl;
	}

	public void setOpenidLogoutUrl(String openidLogoutUrl) {
		this.openidLogoutUrl = openidLogoutUrl;
	}

	public static String getOpenidLogoutUrl() {
		return openidLogoutUrl;
	}

	public void setRegApiUrl(String regApiUrl) {
		this.regApiUrl = regApiUrl;
	}

	public static String getRegApiUrl() {
		return regApiUrl;
	}

	public void setHrApiUrl(String hrApiUrl) {
		this.hrApiUrl = hrApiUrl;
	}

	public static String getHrApiUrl() {
		return hrApiUrl;
	}

	public void setMhCertsUrl(String mhCertsUrl) {
		this.mhCertsUrl = mhCertsUrl;
	}

	public static String getMhCertsUrl() {
		return mhCertsUrl;
	}

	public void setMhCertsKid(String mhCertsKid) {
		this.mhCertsKid = mhCertsKid;
	}

	public static String getMhCertsKid() {
		return mhCertsKid;
	}

	public void setRegCertsUrl(String regCertsUrl) {
		this.regCertsUrl = regCertsUrl;
	}

	public static String getRegCertsUrl() {
		return regCertsUrl;
	}

	public void setRegCertsKid(String regCertsKid) {
		this.regCertsKid = regCertsKid;
	}

	public static String getRegCertsKid() {
		return regCertsKid;
	}


	public void setLoginProviderId(String loginProviderId) {
		this.loginProviderId = loginProviderId;
	}

	public static String getLoginProviderId() {
		return loginProviderId;
	}

	public void setLoginUserId(Integer loginUserId) {
		this.loginUserId = loginUserId;
	}

	public static Integer getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserAuth(String loginUserAuth) {
		this.loginUserAuth = loginUserAuth;
	}

	public static String getLoginUserAuth() {
		return loginUserAuth;
	}

	public void setLoginUserType(String loginUserType) {
		this.loginUserType = loginUserType;
	}

	public static String getLoginUserType() {
		return loginUserType;
	}

	public static boolean hasAuth(String providerId, Integer userId) {
		return loginProviderId.equals(providerId) && loginUserId.equals(userId) && loginUserAuth.equals("has_auth");
	}


	@Override
	public void afterPropertiesSet() {
		Assert.notNull(host, "host is null");

		Assert.notNull(openidConfigUrl, "openid-config-url is null");
		Assert.notNull(openidLogoutUrl, "openid-logout-url is null");
		Assert.notNull(regApiUrl, "reg-api-url is null");
		Assert.notNull(hrApiUrl, "hr-api-url is null");
		Assert.notNull(mhCertsUrl, "mh-certs-url is null");
		Assert.notNull(mhCertsKid, "mh-certs-kid is null");
		Assert.notNull(regCertsUrl, "reg-certs-url is null");
		Assert.notNull(regCertsKid, "reg-certs-kid is null");
	}
}