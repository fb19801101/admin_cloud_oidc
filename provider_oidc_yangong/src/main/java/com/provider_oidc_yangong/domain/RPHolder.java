package com.provider_oidc_yangong.domain;

import com.provider_oidc_yangong.domain.shared.BeanProvider;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwt.JwtClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
public class RPHolder implements Serializable {
    private static final long serialVersionUID = 4335285706375582063L;

    private static final Logger LOG = LoggerFactory.getLogger(RPHolder.class);

    /**
     * Fixed,  resource-id, from oidc-server Constants.java
     */
    private static final String RESOURCE_ID = "oidc-api";

    /**
     * 应用注册信息
     */
    private static final String CLIENT_NAME = "clientName";
    private static final String CLIENT_FULL_NAME = "clientFullName";
    private static final String CLIENT_ID = "clientId";
    private static final String CLIENT_SECRET = "clientSecret";
    private static final String CLIENT_REDIRECT_URIS = "clientRedirectUris";

    /**
     * 应用API注册信息
     */
    private static final String API_CLIENT_ID = "apiClientId";
    private static final String API_CLIENT_SECRET = "apiClientSecret";
    private static final String API_SCOPE = "apiScope";
    private static final String API_RESOURCE_ID = "apiResourceId";
    private static final String API_NAME = "apiName";
    private static final String API_SCOPE_CALL = "apiScopeCall";

    /**
     * 门户对接信息
     */
    private static final String WEB_URI_PC = "webUriPc";
    private static final String WEB_URI_APP = "webUriApp";
    private static final String WEB_API_URI = "webApiUri";
    private static final String WEB_API_TODO = "webApiTodo";
    private static final String WEB_API_INFO = "webApiInfo";

    /**
     * 应用注册中心信息
     */
    private static final String DISCOVERY_ENDPOINT = "discoveryEndpoint";
    private static final String CLIENT_PROVIDER_ID = "clientProviderId";
    private static final String CLIENT_USER_ID = "clientUserId";
    private static final String CLIENT_USER_AUTH = "clientUserAuth";
    private static final String CLIENT_USER_TYPE = "clientUserType";

    /**
     *  前端页面跳转地址
     */
    private static final String VUE_REDIRECT_URIS = "vueRedirectUris";


    @NotBlank(message = "Client Name 不能为空")
    private String clientName;

    @NotBlank(message = "Client Name 不能为空")
    private String clientFullName;


    @NotBlank(message = "Client Id 不能为空")
    private String clientId;

    @NotBlank(message = "Client Secret 不能为空")
    private String clientSecret;

    @NotBlank(message = "Client Redirect Uris 不能为空")
    private List<String> clientRedirectUris;


    @NotBlank(message = "Api Client Id 不能为空")
    private String apiClientId;

    @NotBlank(message = "Api Client Secret 不能为空")
    private String apiClientSecret;

    @NotBlank(message = "Api Scope 不能为空")
    private List<String> apiScope;

    @NotBlank(message = "Api Resource Id 不能为空")
    private String apiResourceId;

    @NotBlank(message = "Api Name 不能为空")
    private String apiName;

    @NotBlank(message = "Api Scope Call 不能为空")
    private List<String> apiScopeCall;


    @NotBlank(message = "Web Uri Pc 不能为空")
    private String webUriPc;

    @NotBlank(message = "Web Uri App 不能为空")
    private String webUriApp;

    @NotBlank(message = "Web Api Uri 不能为空")
    private String webApiUri;

    @NotBlank(message = "Web Api Todo 不能为空")
    private String webApiTodo;

    @NotBlank(message = "Web Api Info 不能为空")
    private String webApiInfo;


    @NotBlank(message = "Discovery Endpoint 不能为空")
    private String discoveryEndpoint;


    @NotBlank(message = "ClientProvider Id 不能为空")
    private String clientProviderId;

    @NotBlank(message = "ClientUser Id 不能为空")
    private String clientUserId;

    @NotBlank(message = "ClientUser Auth 不能为空")
    private String clientUserAuth;

    @NotBlank(message = "ClientUser Type 不能为空")
    private String clientUserType;


    @NotBlank(message = "VUE Redirect Uris 不能为空")
    private List<String> vueRedirectUris;



    /**
     * 临时变量，缓存
     */
    private DiscoveryEndpointInfo endpointInfo;

    public RPHolder() {
    }

    public RPHolder(Map<String, Object> jsonMap) {
        this.clientName = (String)jsonMap.get(CLIENT_NAME);
        this.clientFullName = (String)jsonMap.get(CLIENT_FULL_NAME);
        this.clientId = (String)jsonMap.get(CLIENT_ID);
        this.clientSecret = (String)jsonMap.get(CLIENT_SECRET);
        Object uris = jsonMap.get(CLIENT_REDIRECT_URIS);
        if (uris.getClass().equals(ArrayList.class)) {
            this.clientRedirectUris = (ArrayList<String>) uris;
        } else {
            this.clientRedirectUris = new ArrayList<>();
            this.clientRedirectUris.add((String)jsonMap.get(CLIENT_REDIRECT_URIS));
        }
        this.apiClientId = (String)jsonMap.get(API_CLIENT_ID);
        this.apiClientSecret = (String)jsonMap.get(API_CLIENT_SECRET);
        Object scope = jsonMap.get(API_SCOPE);
        if (scope.getClass().equals(ArrayList.class)) {
            this.apiScope = (ArrayList<String>) scope;
        } else {
            this.apiScope = new ArrayList<>();
            this.apiScope.add((String)jsonMap.get(API_SCOPE));
        }
        this.apiResourceId = (String)jsonMap.get(API_RESOURCE_ID);
        this.apiName = (String)jsonMap.get(API_NAME);
        Object scopeCall = jsonMap.get(API_SCOPE_CALL);
        if (scopeCall.getClass().equals(ArrayList.class)) {
            this.apiScopeCall = (ArrayList<String>) scopeCall;
        } else {
            this.apiScopeCall = new ArrayList<>();
            this.apiScopeCall.add((String)jsonMap.get(API_SCOPE_CALL));
        }
        this.webUriPc = (String)jsonMap.get(WEB_URI_PC);
        this.webUriApp = (String)jsonMap.get(WEB_URI_APP);
        this.webApiUri = (String)jsonMap.get(WEB_API_URI);
        this.webApiTodo = (String)jsonMap.get(WEB_API_TODO);
        this.webApiInfo = (String)jsonMap.get(WEB_API_INFO);
        this.discoveryEndpoint = (String)jsonMap.get(DISCOVERY_ENDPOINT);
        this.clientProviderId = (String)jsonMap.get(CLIENT_PROVIDER_ID);
        this.clientUserId = (String)jsonMap.get(CLIENT_USER_ID);
        this.clientUserAuth = (String)jsonMap.get(CLIENT_USER_AUTH);
        this.clientUserType = (String)jsonMap.get(CLIENT_USER_TYPE);
        uris = jsonMap.get(VUE_REDIRECT_URIS);
        if (uris.getClass().equals(ArrayList.class)) {
            this.vueRedirectUris = (ArrayList<String>) uris;
        } else {
            this.vueRedirectUris = new ArrayList<>();
            this.vueRedirectUris.add((String)jsonMap.get(VUE_REDIRECT_URIS));
        }
    }


    public String toJSON() {
        JwtClaims claims = new JwtClaims();
        claims.setStringClaim(CLIENT_NAME, clientName);
        claims.setStringClaim(CLIENT_FULL_NAME, clientFullName);
        claims.setStringClaim(CLIENT_ID, clientId);
        claims.setStringClaim(CLIENT_SECRET, clientSecret);
        claims.setStringListClaim(CLIENT_REDIRECT_URIS, clientRedirectUris);
        claims.setStringClaim(API_CLIENT_ID, apiClientId);
        claims.setStringClaim(API_CLIENT_SECRET, apiClientSecret);
        claims.setStringListClaim(API_SCOPE, apiScope);
        claims.setStringClaim(API_RESOURCE_ID, apiResourceId);
        claims.setStringClaim(API_NAME, apiName);
        claims.setStringListClaim(API_SCOPE_CALL, apiScopeCall);
        claims.setStringClaim(WEB_URI_PC, discoveryEndpoint);
        claims.setStringClaim(WEB_URI_APP, discoveryEndpoint);
        claims.setStringClaim(WEB_API_URI, discoveryEndpoint);
        claims.setStringClaim(WEB_API_TODO, discoveryEndpoint);
        claims.setStringClaim(WEB_API_INFO, discoveryEndpoint);
        claims.setStringClaim(DISCOVERY_ENDPOINT, discoveryEndpoint);
        claims.setStringClaim(CLIENT_PROVIDER_ID, clientProviderId);
        claims.setStringClaim(CLIENT_USER_ID, clientUserId);
        claims.setStringClaim(CLIENT_USER_AUTH, clientUserAuth);
        claims.setStringClaim(CLIENT_USER_TYPE, clientUserType);
        claims.setStringListClaim(VUE_REDIRECT_URIS, vueRedirectUris);
        return claims.toJson();
    }

    /**
     * 判断是否进行了配置
     *
     * @return true config
     */
    public boolean isConfigRP() {
        return StringUtils.isNoneBlank(clientId)
                && StringUtils.isNotBlank(clientSecret)
                && StringUtils.isNotBlank(discoveryEndpoint);
    }

    /**
     * 发送请求获取 信息
     *
     * @return DiscoveryEndpointInfo or null(error)
     */
    public DiscoveryEndpointInfo getDiscoveryEndpointInfo() {
        if (this.endpointInfo == null) {
            RestTemplate restTemplate = BeanProvider.getBean(RestTemplate.class);
            try {
                this.endpointInfo = restTemplate.getForObject(this.discoveryEndpoint, DiscoveryEndpointInfo.class);
            } catch (RestClientException e) {
                LOG.error("Send request to: {} error: {}", this.discoveryEndpoint, e);
            }
        }
        return this.endpointInfo;
    }


    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientRedirectUris(List<String> clientRedirectUris) {
        this.clientRedirectUris = clientRedirectUris;
    }

    public List<String> getClientRedirectUris() {
        return clientRedirectUris;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getApiClientId() {
        return apiClientId;
    }

    public void setApiClientId(String apiClientId) {
        this.apiClientId = apiClientId;
    }

    public String getApiClientSecret() {
        return apiClientSecret;
    }

    public void setApiClientSecret(String apiClientSecret) {
        this.apiClientSecret = apiClientSecret;
    }

    public List<String> getApiScope() {
        return apiScope;
    }

    public void setApiScope(List<String> apiScope) {
        this.apiScope = apiScope;
    }

    public String getApiResourceId() {
        return apiResourceId == null || apiResourceId.length() == 0 ? RESOURCE_ID:apiResourceId;
    }

    public void setApiResourceId(String apiResourceId) {
        this.apiResourceId = apiResourceId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public List<String> getApiScopeCall() {
        return apiScopeCall;
    }

    public void setApiScopeCall(List<String> apiScopeCall) {
        this.apiScopeCall = apiScopeCall;
    }

    public String getWebUriPc() {
        return webUriPc;
    }

    public void setWebUriPc(String webUriPc) {
        this.webUriPc = webUriPc;
    }

    public String getWebUriApp() {
        return webUriApp;
    }

    public void setWebUriApp(String webUriApp) {
        this.webUriApp = webUriApp;
    }

    public String getWebApiUri() {
        return webApiUri;
    }

    public void setWebApiUri(String webApiUri) {
        this.webApiUri = webApiUri;
    }

    public String getWebApiTodo() {
        return webApiTodo;
    }

    public void setWebApiTodo(String webApiTodo) {
        this.webApiTodo = webApiTodo;
    }

    public String getWebApiInfo() {
        return webApiInfo;
    }

    public void setWebApiInfo(String webApiInfo) {
        this.webApiInfo = webApiInfo;
    }

    public String getDiscoveryEndpoint() {
        return discoveryEndpoint;
    }

    public void setDiscoveryEndpoint(String discoveryEndpoint) {
        this.discoveryEndpoint = discoveryEndpoint;
    }

    public String getClientProviderId() {
        return clientProviderId;
    }

    public void setClientProviderId(String clientProviderId) {
        this.clientProviderId = clientProviderId;
    }

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    public String getClientUserAuth() {
        return clientUserAuth;
    }

    public void setClientUserAuth(String clientUserAuth) {
        this.clientUserAuth = clientUserAuth;
    }

    public String getClientUserType() {
        return clientUserType;
    }

    public void setClientUserType(String clientUserType) {
        this.clientUserType = clientUserType;
    }

    public List<String> getVueRedirectUris() {
        return vueRedirectUris;
    }

    public void setVueRedirectUris(List<String> vueRedirectUris) {
        this.vueRedirectUris = vueRedirectUris;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RPHolder{");
        sb.append("clientName='").append(clientName).append('\'');
        sb.append(", clientFullName='").append(clientFullName).append('\'');
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", clientSecret='").append(clientSecret).append('\'');
        sb.append(", clientRedirectUris=").append(clientRedirectUris);
        sb.append(", apiClientId='").append(apiClientId).append('\'');
        sb.append(", apiClientSecret='").append(apiClientSecret).append('\'');
        sb.append(", apiScope=").append(apiScope);
        sb.append(", apiResourceId='").append(apiResourceId).append('\'');
        sb.append(", apiName='").append(apiName).append('\'');
        sb.append(", apiScopeCall=").append(apiScopeCall);
        sb.append(", webUriPc='").append(webUriPc).append('\'');
        sb.append(", webUriApp='").append(webUriApp).append('\'');
        sb.append(", webApiUri='").append(webApiUri).append('\'');
        sb.append(", webApiTodo='").append(webApiTodo).append('\'');
        sb.append(", webApiInfo='").append(webApiInfo).append('\'');
        sb.append(", discoveryEndpoint='").append(discoveryEndpoint).append('\'');
        sb.append(", clientProviderId='").append(clientProviderId).append('\'');
        sb.append(", clientUserId='").append(clientUserId).append('\'');
        sb.append(", clientUserAuth='").append(clientUserAuth).append('\'');
        sb.append(", clientUserType='").append(clientUserType).append('\'');
        sb.append(", vueRedirectUris=").append(vueRedirectUris);
        sb.append(", endpointInfo=").append(endpointInfo);
        sb.append('}');
        return sb.toString();
    }
}