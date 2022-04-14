package com.provider_oidc_hunningtu.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.provider_oidc_hunningtu.service.dto.AccessTokenDto;
import com.provider_oidc_hunningtu.service.dto.CRCCOrganizationDto;
import com.provider_oidc_hunningtu.service.dto.CRCCUserInfoDto;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import javax.annotation.PreDestroy;
import java.net.URI;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 项目启动时加载token
 * @Author QiYuan
 * @Date 2021/3/10
 */
@Component
public class TokenHolder {
    public static TokenEntity tokenEntity = new TokenEntity();
//    @PostConstruct
    public static void init(RPHolder rpHolder) throws Exception {
        tokenEntity=getToken(rpHolder);
    }
    @PreDestroy
    public void destroy(){}

    //    @Scheduled(cron = "0 0 0/2 * * ?")
    //    public void testOne() throws Exception {
    //        //每2小时执行一次缓存
    //        init();
    //    }
    public static TokenEntity getToken(RPHolder rpHolder) throws Exception {
        if(rpHolder !=null && rpHolder.isConfigRP()) {
            String apiClientId = rpHolder.getApiClientId();
            String apiClientSecret = rpHolder.getApiClientSecret();

            String auth = apiClientId + ":" + apiClientSecret;
            byte[] bytes = auth.getBytes();
            //Base64 加密
            String encoded = Base64.getEncoder().encodeToString(bytes);
            //System.out.println("Base 64 加密后： Basic " + encoded);

            Map<String, String> queryMap = new HashMap<>();
            Map<String, String> headers = new HashMap<>();
            queryMap.put("grant_type", "client_credentials");
            //headers.put("Authorization", "Basic Q1JDQzEyLUxEU0M6T2hPS3dVYWhQOXQ5cmJDalZuMzhrTXQ4N2RITWswM2NGWFZFNDhVUw==");
            headers.put("Authorization", "Basic " + encoded);
            HttpResponse response = HttpUtils.doGet("https://regtest.crcc.cn", "/oauth/token", "get", headers, queryMap);
            if (response.getStatusLine().getStatusCode() == 200) {
                String json = EntityUtils.toString(response.getEntity());
                TokenEntity tokenEntity = JSON.parseObject(json, TokenEntity.class);
                return tokenEntity;
            }
            return null;
        }

        return null;
    }


    public static String doGet(String path) throws Exception {
        String authorization = TokenHolder.tokenEntity.getAccessToken();
        Map<String,String> headers=new HashMap<>();
        if (!authorization.equals("")&& authorization != null) {
            headers.put("Authorization", "Bearer " + authorization);
        }
        HttpResponse response = HttpUtils.doGet("https://hrapitest.crcc.cn/api/hr", path, "get", headers, null);
        if (response.getStatusLine().getStatusCode()==200){
            return EntityUtils.toString(response.getEntity());
//            if (json!=null||!json.equals("")){
//                byte[] bs = json.getBytes(StandardCharsets.ISO_8859_1);
//                String utf8Str = new String(bs, StandardCharsets.UTF_8);
//                return utf8Str;
//            }
        }
        return "";
    }


    public static AccessTokenDto loadAccessToken(RPHolder rpHolder) {
        if(rpHolder !=null && rpHolder.isConfigRP()) {
            String apiClientId = rpHolder.getApiClientId();
            String apiClientSecret = rpHolder.getApiClientSecret();

            String auth = apiClientId + ":" + apiClientSecret;
            byte[] bytes = auth.getBytes();
            //Base64 加密
            String encoded = Base64.getEncoder().encodeToString(bytes);
            //System.out.println("Base 64 加密后： Basic " + encoded);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.add("Authorization", "Basic " + encoded);
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

            MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
            param.add("grant_type", "client_credentials");
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://regtest.crcc.cn/oauth/token").queryParams(param);
            URI url = builder.build().encode().toUri();

            RestTemplate restTemplate = new RestTemplate();

            try {
                ResponseEntity<AccessTokenDto> accessToken = restTemplate.exchange(url, HttpMethod.GET, entity, AccessTokenDto.class);
                TokenEntity tokenEntity = new TokenEntity();
                AccessTokenDto token = accessToken.getBody();
                tokenEntity.setAccessToken(token.getAccessToken());
                return token;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("RestTemplate Exception: loadAccessToken --> " + e.getMessage());
                return null;
            }
        }

        return null;
    }


    public static CRCCUserInfoDto getUserApi(AccessTokenDto accessTokenDto, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/"+path;

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<CRCCUserInfoDto> userInfo = restTemplate.exchange(url, HttpMethod.GET, entity, CRCCUserInfoDto.class);
            return userInfo.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("RestTemplate Exception: getUserApi --> " + e.getMessage());
            return null;
        }
    }

    public static List<CRCCUserInfoDto> getUserListApi(AccessTokenDto accessTokenDto, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/"+path;

        RestTemplate restTemplate = new RestTemplate();

        try {
            ParameterizedTypeReference<List<CRCCUserInfoDto>> responseType = new ParameterizedTypeReference<List<CRCCUserInfoDto>>() {};
            ResponseEntity<List<CRCCUserInfoDto>> userInfo = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
            return userInfo.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("RestTemplate Exception: getUserListApi --> " + e.getMessage());
            return null;
        }
    }

    public static CRCCOrganizationDto getOrgApi(AccessTokenDto accessTokenDto, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/"+path;

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<CRCCOrganizationDto> organization = restTemplate.exchange(url, HttpMethod.GET, entity, CRCCOrganizationDto.class);
            return organization.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("RestTemplate Exception: getOrgApi --> " + e.getMessage());
            return null;
        }
    }

    public static List<CRCCOrganizationDto> getOrgListApi(AccessTokenDto accessTokenDto, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/"+path;

        RestTemplate restTemplate = new RestTemplate();

        try {
            ParameterizedTypeReference<List<CRCCOrganizationDto>> responseType = new ParameterizedTypeReference<List<CRCCOrganizationDto>>() {};
            ResponseEntity<List<CRCCOrganizationDto>> organization = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
            return organization.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("RestTemplate Exception: getOrgListApi --> " + e.getMessage());
            return null;
        }
    }

    public static JSONObject getObjApi(AccessTokenDto accessTokenDto, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/";
        if(path != null && path.length() > 0) {
            url = "https://hrapitest.crcc.cn/api/hr/"+path;
        }

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<JSONObject> organization = restTemplate.exchange(url, HttpMethod.GET, entity, JSONObject.class);
            return organization.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("RestTemplate Exception: getObjApi --> " + e.getMessage());
            return null;
        }
    }

    public static List<JSONObject> getObjListApi(AccessTokenDto accessTokenDto, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/";
        if(path != null && path.length() > 0) {
            url = "https://hrapitest.crcc.cn/api/hr/"+path;
        }

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<JSONObject[]> organization = restTemplate.exchange(url, HttpMethod.GET, entity, JSONObject[].class);
            return Arrays.asList(organization.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("RestTemplate Exception: getObjListApi --> " + e.getMessage());
            return null;
        }
    }


    public static CRCCUserInfoDto getUserAsyncApi(AccessTokenDto accessTokenDto, String path, String provider) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/"+path;

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        try {
            final ListenableFuture<ResponseEntity<CRCCUserInfoDto>> response = asyncRestTemplate.exchange(url, HttpMethod.GET, entity, CRCCUserInfoDto.class);
            final CRCCUserInfoDto[] userInfoArray = new CRCCUserInfoDto[1];
            response.addCallback(new ListenableFutureCallback<ResponseEntity<CRCCUserInfoDto>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("ListenableFutureCallback Failure Exception: getUserAsyncApi --> " + ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<CRCCUserInfoDto> responseEntity) {
                    CRCCUserInfoDto user =responseEntity.getBody();
                    if(user != null) {
                        user.setProvider(provider);
                        userInfoArray[0] = user;
//                        System.out.println("AsyncRestTemplate Success: getUserAsyncApi --> user: "+user.getName()+" time: " + DateUtils.currentTimeMillis());
                    }
                }
            });

            return userInfoArray[0];
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AsyncRestTemplate Exception: getUserAsyncApi --> " + e.getMessage());
            return null;
        }
    }

    public static List<CRCCUserInfoDto> getUserListAsyncApi(AccessTokenDto accessTokenDto, String path, String provider) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/"+path;

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        try {
            ParameterizedTypeReference<List<CRCCUserInfoDto>> responseType = new ParameterizedTypeReference<List<CRCCUserInfoDto>>() {};
            final ListenableFuture<ResponseEntity<List<CRCCUserInfoDto>>> response = asyncRestTemplate.exchange(url, HttpMethod.GET, entity, responseType);
            final List<CRCCUserInfoDto> userInfoArray = new ArrayList<>();
            response.addCallback(new ListenableFutureCallback<ResponseEntity<List<CRCCUserInfoDto>>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("ListenableFutureCallback Failure Exception: getUserListAsyncApi --> " + ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<List<CRCCUserInfoDto>> responseEntity) {
                    List<CRCCUserInfoDto> list = responseEntity.getBody();
                    if(list != null) {
                        for(CRCCUserInfoDto x: list) {
                            x.setProvider(provider);
                            userInfoArray.add(x);
                        }
//                        System.out.println("AsyncRestTemplate Success: getUserListAsyncApi --> size: "+userInfoArray.size()+" time: " + DateUtils.currentTimeMillis());
                    }
                }
            });

            return userInfoArray;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AsyncRestTemplate Exception: getUserListAsyncApi --> " + e.getMessage());
            return null;
        }
    }

    public static CRCCOrganizationDto getOrgAsyncApi(AccessTokenDto accessTokenDto, String path, String provider) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/"+path;

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        try {
            final ListenableFuture<ResponseEntity<CRCCOrganizationDto>> response = asyncRestTemplate.exchange(url, HttpMethod.GET, entity, CRCCOrganizationDto.class);
            final CRCCOrganizationDto[] organizationArray = new CRCCOrganizationDto[1];
            response.addCallback(new ListenableFutureCallback<ResponseEntity<CRCCOrganizationDto>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("ListenableFutureCallback Failure Exception: getOrgAsyncApi --> " + ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<CRCCOrganizationDto> responseEntity) {
                    CRCCOrganizationDto org = responseEntity.getBody();
                    if(org != null) {
                        org.setProvider(provider);
                        organizationArray[0] = org;
//                        System.out.println("AsyncRestTemplate Success: getOrgAsyncApi --> org: "+org.getName()+" time: " + DateUtils.currentTimeMillis());
                    }
                }
            });

            return organizationArray[0];
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AsyncRestTemplate Exception: getOrgAsyncApi --> " + e.getMessage());
            return null;
        }
    }

    public static List<CRCCOrganizationDto> getOrgListAsyncApi(AccessTokenDto accessTokenDto, String path, String provider) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/"+path;

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        try {
            ParameterizedTypeReference<List<CRCCOrganizationDto>> responseType = new ParameterizedTypeReference<List<CRCCOrganizationDto>>() {};
            final ListenableFuture<ResponseEntity<List<CRCCOrganizationDto>>> response = asyncRestTemplate.exchange(url, HttpMethod.GET, entity, responseType);
            final List<CRCCOrganizationDto> organizationArray = new ArrayList<>();
            response.addCallback(new ListenableFutureCallback<ResponseEntity<List<CRCCOrganizationDto>>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("ListenableFutureCallback Failure Exception: getOrgListAsyncApi --> " + ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<List<CRCCOrganizationDto>> responseEntity) {
                    List<CRCCOrganizationDto> list = responseEntity.getBody();
                    if(list != null) {
                        for(CRCCOrganizationDto x: list) {
                            x.setProvider(provider);
                            organizationArray.add(x);
                        }
//                        System.out.println("AsyncRestTemplate Success: getOrgListAsyncApi --> size: "+organizationArray.size()+" time: " + DateUtils.currentTimeMillis());
                    }
                }
            });

            return organizationArray;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AsyncRestTemplate Exception: getOrgListAsyncApi --> " + e.getMessage());
            return null;
        }
    }

    public static JSONObject getObjAsyncApi(AccessTokenDto accessTokenDto, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/";
        if(path != null && path.length() > 0) {
            url = "https://hrapitest.crcc.cn/api/hr/"+path;
        }

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        try {
            final ListenableFuture<ResponseEntity<JSONObject>> response = asyncRestTemplate.exchange(url, HttpMethod.GET, entity, JSONObject.class);
            final JSONObject[] jsonArray = new JSONObject[1];
            response.addCallback(new ListenableFutureCallback<ResponseEntity<JSONObject>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("ListenableFutureCallback Failure Exception: getObjAsyncApi --> " + ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<JSONObject> responseEntity) {
                    if(responseEntity.getBody() != null) {
                        jsonArray[0] = responseEntity.getBody();
//                        System.out.println("AsyncRestTemplate Success: getObjAsyncApi --> time: " + DateUtils.currentTimeMillis());
                    }
                }
            });

            return jsonArray[0];
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AsyncRestTemplate Exception: getObjAsyncApi --> " + e.getMessage());
            return null;
        }
    }

    public static List<JSONObject> getObjListAsyncApi(AccessTokenDto accessTokenDto, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/";
        if(path != null && path.length() > 0) {
            url = "https://hrapitest.crcc.cn/api/hr/"+path;
        }

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        try {
            ParameterizedTypeReference<List<JSONObject>> responseType = new ParameterizedTypeReference<List<JSONObject>>() {};
            final ListenableFuture<ResponseEntity<List<JSONObject>>> response = asyncRestTemplate.exchange(url, HttpMethod.GET, entity, responseType);
            final List<JSONObject> jsonArray = new ArrayList<>();
            response.addCallback(new ListenableFutureCallback<ResponseEntity<List<JSONObject>>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("ListenableFutureCallback Failure Exception: getObjListAsyncApi --> " + ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<List<JSONObject>> responseEntity) {
                    if(responseEntity.getBody() != null) {
                        jsonArray.addAll(responseEntity.getBody());
//                        System.out.println("AsyncRestTemplate Success: getObjListAsyncApi --> size: "+jsonArray.size()+" time: " + DateUtils.currentTimeMillis());
                    }
                }
            });

            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AsyncRestTemplate Exception: getObjListAsyncApi --> " + e.getMessage());
            return null;
        }
    }


    public static void getUserAsyncApi(AccessTokenDto accessTokenDto, String path, String provider, CRCCUserInfoDto user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/"+path;

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        try {
            final ListenableFuture<ResponseEntity<CRCCUserInfoDto>> response = asyncRestTemplate.exchange(url, HttpMethod.GET, entity, CRCCUserInfoDto.class);

            response.addCallback(new ListenableFutureCallback<ResponseEntity<CRCCUserInfoDto>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("ListenableFutureCallback Failure Exception: getUserAsyncApi --> " + ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<CRCCUserInfoDto> responseEntity) {
                    if(user != null) {
                        user.set(responseEntity.getBody());
                        user.setProvider(provider);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AsyncRestTemplate Exception: getUserAsyncApi --> " + e.getMessage());
        }
    }

    public static void getUserListAsyncApi(AccessTokenDto accessTokenDto, String path, String provider, CRCCOrganizationDto org) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/"+path;

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        try {
            ParameterizedTypeReference<List<CRCCUserInfoDto>> responseType = new ParameterizedTypeReference<List<CRCCUserInfoDto>>() {};
            final ListenableFuture<ResponseEntity<List<CRCCUserInfoDto>>> response = asyncRestTemplate.exchange(url, HttpMethod.GET, entity, responseType);

            response.addCallback(new ListenableFutureCallback<ResponseEntity<List<CRCCUserInfoDto>>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("ListenableFutureCallback Failure Exception: getUserListAsyncApi --> " + ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<List<CRCCUserInfoDto>> responseEntity) {
                    List<CRCCUserInfoDto> list = responseEntity.getBody();
                    if(list != null && org != null) {
                        for(CRCCUserInfoDto x: list) {
                            x.setProvider(provider);
                        }

                        org.setUsers(list);
//                        System.out.println("AsyncRestTemplate Success: getUserListAsyncApi --> size: "+list.size()+" time: " + DateUtils.currentTimeMillis());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AsyncRestTemplate Exception: getUserListAsyncApi --> " + e.getMessage());
        }
    }

    public static void getUserAsyncApi(AccessTokenDto accessTokenDto, String path, String provider, List<CRCCUserInfoDto> list, Integer index) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/"+path;

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        try {
            final ListenableFuture<ResponseEntity<CRCCUserInfoDto>> response = asyncRestTemplate.exchange(url, HttpMethod.GET, entity, CRCCUserInfoDto.class);

            response.addCallback(new ListenableFutureCallback<ResponseEntity<CRCCUserInfoDto>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("ListenableFutureCallback Failure Exception: getUserAsyncApi --> " + ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<CRCCUserInfoDto> responseEntity) {
                    CRCCUserInfoDto user = responseEntity.getBody();
                    if(list != null && list.size() > index && user != null) {
                        user.setProvider(provider);
                        list.set(index, user);
//                        System.out.println("AsyncRestTemplate Success: getObjAsyncApi --> size: "+list.size()+" time: " + DateUtils.currentTimeMillis());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AsyncRestTemplate Exception: getUserAsyncApi --> " + e.getMessage());
        }
    }

    public static void getUserListAsyncApi(AccessTokenDto accessTokenDto, String path, String provider, List<CRCCUserInfoDto> list) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/"+path;

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        try {
            ParameterizedTypeReference<List<CRCCUserInfoDto>> responseType = new ParameterizedTypeReference<List<CRCCUserInfoDto>>() {};
            final ListenableFuture<ResponseEntity<List<CRCCUserInfoDto>>> response = asyncRestTemplate.exchange(url, HttpMethod.GET, entity, responseType);

            response.addCallback(new ListenableFutureCallback<ResponseEntity<List<CRCCUserInfoDto>>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("ListenableFutureCallback Failure Exception: getUserListAsyncApi --> " + ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<List<CRCCUserInfoDto>> responseEntity) {
                    List<CRCCUserInfoDto> _list = responseEntity.getBody();
                    if(list != null && _list != null) {
                        list.clear();
                        for(CRCCUserInfoDto x: _list) {
                            x.setProvider(provider);
                            list.add(x);
                        }
//                        System.out.println("AsyncRestTemplate Success: getUserListAsyncApi --> size: "+list.size()+" time: " + DateUtils.currentTimeMillis());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AsyncRestTemplate Exception: getUserListAsyncApi --> " + e.getMessage());
        }
    }

    public static void getOrgAsyncApi(AccessTokenDto accessTokenDto, String path, String provider, CRCCOrganizationDto org) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/"+path;

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        try {
            final ListenableFuture<ResponseEntity<CRCCOrganizationDto>> response = asyncRestTemplate.exchange(url, HttpMethod.GET, entity, CRCCOrganizationDto.class);

            response.addCallback(new ListenableFutureCallback<ResponseEntity<CRCCOrganizationDto>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("ListenableFutureCallback Failure Exception: getOrgAsyncApi --> " + ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<CRCCOrganizationDto> responseEntity) {

                    if(org != null) {
                        org.set(responseEntity.getBody());
                        org.setProvider(provider);
//                        System.out.println("AsyncRestTemplate Success: getOrgAsyncApi --> org: "+org.getName()+" time: " + DateUtils.currentTimeMillis());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AsyncRestTemplate Exception: getOrgAsyncApi --> " + e.getMessage());
        }
    }

    public static void getOrgAsyncApi(AccessTokenDto accessTokenDto, String path, String provider, List<CRCCOrganizationDto> list, Integer index) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/"+path;

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        try {
            final ListenableFuture<ResponseEntity<CRCCOrganizationDto>> response = asyncRestTemplate.exchange(url, HttpMethod.GET, entity, CRCCOrganizationDto.class);

            response.addCallback(new ListenableFutureCallback<ResponseEntity<CRCCOrganizationDto>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("ListenableFutureCallback Failure Exception: getOrgAsyncApi --> " + ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<CRCCOrganizationDto> responseEntity) {
                    CRCCOrganizationDto org = responseEntity.getBody();
                    if(list != null && list.size() > index && org != null) {
                        org.setProvider(provider);
                        list.set(index, org);
//                        System.out.println("AsyncRestTemplate Success: getObjAsyncApi --> size: "+list.size()+" time: " + DateUtils.currentTimeMillis());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AsyncRestTemplate Exception: getOrgAsyncApi --> " + e.getMessage());
        }
    }

    public static void getOrgListAsyncApi(AccessTokenDto accessTokenDto, String path, String provider, List<CRCCOrganizationDto> list) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/"+path;

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        try {
            ParameterizedTypeReference<List<CRCCOrganizationDto>> responseType = new ParameterizedTypeReference<List<CRCCOrganizationDto>>() {};
            final ListenableFuture<ResponseEntity<List<CRCCOrganizationDto>>> response = asyncRestTemplate.exchange(url, HttpMethod.GET, entity, responseType);

            response.addCallback(new ListenableFutureCallback<ResponseEntity<List<CRCCOrganizationDto>>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("ListenableFutureCallback Failure Exception: getOrgListAsyncApi --> " + ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<List<CRCCOrganizationDto>> responseEntity) {
                    List<CRCCOrganizationDto> _list = responseEntity.getBody();
                    if(list != null && _list != null) {
                        list.clear();
                        for(CRCCOrganizationDto x: _list) {
                            x.setProvider(provider);
                            list.add(x);
                        }
//                        System.out.println("AsyncRestTemplate Success: getOrgListAsyncApi --> size: "+list.size()+" time: " + DateUtils.currentTimeMillis());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AsyncRestTemplate Exception: getOrgListAsyncApi --> " + e.getMessage());
        }
    }

    public static void getObjAsyncApi(AccessTokenDto accessTokenDto, String path, List<JSONObject> list, Integer index) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/";
        if(path != null && path.length() > 0) {
            url = "https://hrapitest.crcc.cn/api/hr/"+path;
        }

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        try {
            final ListenableFuture<ResponseEntity<JSONObject>> response = asyncRestTemplate.exchange(url, HttpMethod.GET, entity, JSONObject.class);

            response.addCallback(new ListenableFutureCallback<ResponseEntity<JSONObject>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("ListenableFutureCallback Failure Exception: getObjAsyncApi --> " + ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<JSONObject> responseEntity) {
                    if(list != null && list.size() > index && responseEntity.getBody() != null) {
                        list.set(index, responseEntity.getBody());
//                        System.out.println("AsyncRestTemplate Success: getObjAsyncApi --> size: "+list.size()+" time: " + DateUtils.currentTimeMillis());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AsyncRestTemplate Exception: getObjAsyncApi --> " + e.getMessage());
        }
    }

    public static void getObjListAsyncApi(AccessTokenDto accessTokenDto, String path, List<JSONObject> list) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/";
        if(path != null && path.length() > 0) {
            url = "https://hrapitest.crcc.cn/api/hr/"+path;
        }

        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        try {
            ParameterizedTypeReference<List<JSONObject>> responseType = new ParameterizedTypeReference<List<JSONObject>>() {};
            final ListenableFuture<ResponseEntity<List<JSONObject>>> response = asyncRestTemplate.exchange(url, HttpMethod.GET, entity, responseType);

            response.addCallback(new ListenableFutureCallback<ResponseEntity<List<JSONObject>>>() {
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("ListenableFutureCallback Exception: getObjListAsyncApi --> " + ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<List<JSONObject>> responseEntity) {
                    List<JSONObject> _list = responseEntity.getBody();
                    if(list != null && _list != null) {
                        list.addAll(_list);
//                        System.out.println("AsyncRestTemplate Success: getObjListAsyncApi --> size: "+list.size()+" time: " + DateUtils.currentTimeMillis());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("AsyncRestTemplate Exception: getObjListAsyncApi --> " + e.getMessage());
        }
    }


    public void guava() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        // 基于jdk线程池，创建支持异步回调的线程池
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);

        long start = System.currentTimeMillis();
        // 具体的异步访问任务
        com.google.common.util.concurrent.ListenableFuture<HttpEntity<String>> ans =
                listeningExecutorService.submit(new Callable<HttpEntity<String>>() {
                    @Override
                    public HttpEntity<String> call() throws Exception {
                        RestTemplate restTemplate = new RestTemplate();
                        return restTemplate.getForEntity("http://127.0.0.1:8080/atimeout?name=一灰灰&age=19", String.class);
                    }
                });

        // 完成之后，在指定的线程池（第三个参数）中回调
        Futures.addCallback(ans, new com.google.common.util.concurrent.FutureCallback<HttpEntity<String>>() {
            @Override
            public void onSuccess(@Nullable HttpEntity<String> stringHttpEntity) {
                System.out.println("guava call back res");
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("guava call back failed");
            }
        }, Executors.newFixedThreadPool(1));

        System.out.println("do something other in guava!");
        listeningExecutorService.shutdown();
    }

    public void async() {
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        long start = System.currentTimeMillis();
        ListenableFuture<ResponseEntity<String>> response = asyncRestTemplate.getForEntity("http://127.0.0.1:8080/atimeout?name=一灰灰&age=20", String.class);

        response.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("1. Async failure get !");
            }

            @Override
            public void onSuccess(ResponseEntity<String> stringResponseEntity) {
                String ans = stringResponseEntity.getBody();
                System.out.println("1. Async success get!");
            }
        });

        response = asyncRestTemplate.getForEntity("http://127.0.0.1:8080/4xx?name=一灰灰&age=20", String.class);
        response.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("2. Async failure get!");
            }

            @Override
            public void onSuccess(ResponseEntity<String> result) {
                System.out.println("2. Async success get!");
            }
        });

        System.out.println("do something else!!!");
    }


    public static RPHolder getRPHolderApi(AccessTokenDto accessTokenDto, String uri, String path, String scope) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = String.format("%s/%s/%s",uri,scope,path);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<RPHolder> rpHolder = restTemplate.exchange(url, HttpMethod.GET, entity, RPHolder.class);
            return rpHolder.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("RestTemplate Exception: getRPHolderApi --> " + e.getMessage());
            return null;
        }
    }

    public static List<RPHolder> getRPHolderListApi(AccessTokenDto accessTokenDto, String uri, String path, String scope) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = String.format("%s/%s/%s",uri,scope,path);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ParameterizedTypeReference<List<RPHolder>> responseType = new ParameterizedTypeReference<List<RPHolder>>() {};
            ResponseEntity<List<RPHolder>> rpHolders = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
            return rpHolders.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("RestTemplate Exception: getRPHolderListApi --> " + e.getMessage());
            return null;
        }
    }

    public static String getStringApi(AccessTokenDto accessTokenDto, String uri, String path, String scope) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String token = accessTokenDto.getAccessToken();
        headers.add("Authorization","Bearer "+token);
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = String.format("%s/%s/%s",uri,scope,path);


        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> str = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return str.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("RestTemplate Exception: getStringApi --> " + e.getMessage());
            return null;
        }
    }

}
