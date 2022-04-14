package com.provider_oidc_wuzibm.service.impl;

import com.provider_oidc_wuzibm.service.business.OIDCTokenVerifier;
import com.provider_oidc_wuzibm.domain.RPHolder;
import com.provider_oidc_wuzibm.domain.RPHolderRepository;
import com.provider_oidc_wuzibm.service.OIDCClientService;
import com.provider_oidc_wuzibm.service.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Service
public class OIDCClientServiceImpl implements OIDCClientService {

    private static final Logger LOG = LoggerFactory.getLogger(OIDCClientServiceImpl.class);

    @Autowired
    private RPHolderRepository rpHolderRepository;


    @Autowired
    private RestTemplate restTemplate;


    @Override
    public RPHolder loadRPHolder() {
        return rpHolderRepository.findRPHolder();
    }

    @Override
    public boolean saveRPHolder(RPHolder rpHolder) {
        boolean saveOK = rpHolderRepository.saveRPHolder(rpHolder);
        return saveOK && rpHolder.getDiscoveryEndpointInfo() != null;
    }

    @Override
    public AuthAccessTokenDto createAuthAccessTokenDto(AuthCallbackDto callbackDto) {
        RPHolder rpHolder = loadRPHolder();
        return new AuthAccessTokenDto()
                .setAccessTokenUri(rpHolder.getDiscoveryEndpointInfo().getToken_endpoint())
                .setCode(callbackDto.getCode());
    }

    @Override
    public AccessTokenDto retrieveAccessTokenDto(AuthAccessTokenDto tokenDto) {
        final String fullUri = tokenDto.getAccessTokenUri();
        LOG.debug("Get access_token URL: {}", fullUri);

        return loadAccessTokenDto(fullUri, tokenDto.getAuthCodeParams());
    }

    @Override
    public AccessTokenDto retrieveAccessTokenDto_(AuthAccessTokenDto tokenDto) {
        final String fullUri = tokenDto.getAccessTokenUri();
        LOG.debug("Get access_token URL: {}", fullUri);

        return loadAccessTokenDto_(fullUri, tokenDto.getAuthCodeParams());
    }

    @Override
    public UserInfoDto loadUserInfoDto(String accessToken) {
        LOG.debug("Load OIDC User_Info by access_token = {}", accessToken);

        if (StringUtils.isBlank(accessToken)) {
            return new UserInfoDto("Illegal 'access_token'", "'access_token' is empty");
        } else {
            RPHolder rpHolder = loadRPHolder();
            try {
                String userinfoEndpoint = rpHolder.getDiscoveryEndpointInfo().getUserinfo_endpoint();
                //access_token 建议使用header传递
                return restTemplate.getForObject(userinfoEndpoint + "?access_token=" + accessToken, UserInfoDto.class);
            } catch (RestClientException e) {
                LOG.error("Rest error", e);
            }
            return null;
        }
    }

    @Override
    public UserInfoDto loadUserInfoDto_(String accessToken) {
        LOG.debug("Load OIDC Login_Info by access_token = {}", accessToken);

        if (StringUtils.isBlank(accessToken)) {
            return new UserInfoDto("access_token_error","access_token' is empty");
        } else {
            RPHolder rpHolder = loadRPHolder();
            try {
                String userinfoEndpoint = rpHolder.getDiscoveryEndpointInfo().getUserinfo_endpoint();

                // 构建请求头
                HttpHeaders requestHeaders = new HttpHeaders();
                // 指定响应返回json格式
                requestHeaders.add("accept", "application/json");
                // AccessToken放在请求头中
                requestHeaders.setBearerAuth(accessToken);
                // 构建请求实体
                HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
                RestTemplate restTemplate = new RestTemplate();
                // get请求方式 此处必须提供CRCCUserInfoDto的空参构造器 ，
                ResponseEntity<UserInfoDto> response = restTemplate.exchange(userinfoEndpoint, HttpMethod.GET, requestEntity, UserInfoDto.class);

                return new UserInfoDto(response.getBody().getSub());
            } catch (ResourceAccessException e){

                LOG.error("请求超时", e);

            } catch (RestClientException e) {
                LOG.error("Rest error", e);
            }
            return null;
        }
    }

    @Override
    public Map<String, Object> verifyToken(String token) {
        RPHolder rpHolder = loadRPHolder();
        OIDCTokenVerifier verifier = new OIDCTokenVerifier(rpHolder, token);
        return verifier.verify();
    }

    @Override
    public AccessTokenDto retrieveCredentialsAccessTokenDto(AuthAccessTokenDto authAccessTokenDto) {
        final String uri = authAccessTokenDto.getAccessTokenUri();
        LOG.debug("Get [{}] access_token URL: {}", authAccessTokenDto.getGrantType(), uri);

        return loadAccessTokenDto(uri, authAccessTokenDto.getCredentialsParams());
    }

    @Override
    public AccessTokenDto retrievePasswordAccessTokenDto(AuthAccessTokenDto authAccessTokenDto) {
        final String fullUri = authAccessTokenDto.getAccessTokenUri();
        LOG.debug("Get [password] access_token URL: {}", fullUri);

        return loadAccessTokenDto(fullUri, authAccessTokenDto.getAccessTokenParams());
    }

    @Override
    public AccessTokenDto refreshAccessTokenDto(RefreshAccessTokenDto refreshAccessTokenDto) {
        final String fullUri = refreshAccessTokenDto.getRefreshAccessTokenUri();
        LOG.debug("Get refresh_access_token URL: {}", fullUri);

        return loadAccessTokenDto(fullUri, refreshAccessTokenDto.getRefreshTokenParams());
    }

    private AccessTokenDto loadAccessTokenDto(String fullUri, Map<String, String> params) {
        try {
            //此处的 post请求参数用在 url上拼接不是好方式
            StringBuilder url = new StringBuilder(fullUri + "?");
            for (String key : params.keySet()) {
                url.append(key).append("=").append(params.get(key)).append("&");
            }
            return restTemplate.postForObject(url.toString(), null, AccessTokenDto.class);
        } catch (RestClientException e) {
            LOG.error("Send request to: {} error", fullUri, e);
            return new AccessTokenDto("request_error", e.getMessage());
        }
    }

    /**
     * 使用code换取 access-token
     *
     * @param fullUri
     * @param params
     * @return
     */
    private AccessTokenDto loadAccessTokenDto_(String fullUri, Map<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        //设置Http的Header
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String client_id = params.get("client_id");
        String client_secret = params.get("client_secret");
        //请求头添加 基本认证
        headers.setBasicAuth(client_id, client_secret);
//		参数移除  client_id client_secret
        params.remove("client_id");
        params.remove("client_secret");
        // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递 post传参
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
        paramMap.add("grant_type", params.get("grant_type"));
        paramMap.add("redirect_uri", params.get("redirect_uri"));
        paramMap.add("code", params.get("code"));

        HttpEntity entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(fullUri).queryParams(paramMap);
        try {
            //此处为get请求
            ResponseEntity<AccessTokenDto> exchange = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, AccessTokenDto.class);

            return exchange.getBody();
        } catch (RestClientException e) {
            LOG.error("Send request to: {} error", fullUri, e);
            LOG.error("完整请求地址：", builder.build().encode().toUri());

            return new AccessTokenDto("request_error", e.getMessage());
        }
    }

}
