package com.provider_oidc_ldsc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.provider_oidc_ldsc.domain.DateUtils;
import com.provider_oidc_ldsc.domain.HttpUtils;
import com.provider_oidc_ldsc.domain.TokenEntity;
import com.provider_oidc_ldsc.service.dto.AccessTokenDto;
import com.provider_oidc_ldsc.service.dto.CRCCOrganizationDto;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@SpringBootTest
class ProviderOidcClientApplicationTests {

    private String token = "eyJraWQiOiJiOGZkNzRjOC1hY2JjLTQ3NzgtYWZhOS03NTY0ZGFlZWE3M2YiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJjcmNjMTJfbGRzYyIsInNjb3BlIjpbImFwaV9ocjphbGwiLCJhcGlfaWM6YWxsIiwiYXBpX2dvbmdjaGVuZzphbGwiLCJhcGlfZ29uZ2NoZW5nYm06YWxsIiwiYXBpX2xhb3d1OmFsbCIsImFwaV9nYW5namluY2hhbmc6YWxsIiwiYXBpX2h1bm5pbmd0dTphbGwiLCJhcGlfZ29uZ2NoZW5nc2w6YWxsIiwiYXBpX2dvbmd4dTphbGwiLCJhcGlfd29ya3BsYW46YWxsIiwiYXBpX2R5bmFtaWNwbGFuOmFsbCIsImFwaV9nb25nY2hlbmd3YnM6YWxsIiwiYXBpX3NoZWJlaTphbGwiLCJhcGlfd3V6aTphbGwiLCJhcGlfcHJvamVjdDphbGwiXSwiZXhwIjoxNjQyOTAzNzg5LCJqdGkiOiIxYTYxMTgyNy0wMDhiLTQ0YmItOWJiMS0xMGY4ZGRkZTNiOTgiLCJjbGllbnRfaWQiOiJjcmNjMTJfbGRzYyJ9.h8WyB4HIRa00JSo5USNIX3AmuyN3VbbJ-wgDyidXc9eo4yh1St1Zu02zQ9jMBIpYuh7Jbo6LsMoWfvzYVd7WgpO0j9NwejB7t9USPGEmvCuPCU3pb9fngb65ggcdh0OV3ktWpZkArnbQQ5a3dF9TQSW2YO1KAjkzFJa4qziBwHBopL7zA8YQKovQrz8DFHl7OpMaPsKJabYIuKXdOyC9k6KoBKcN8Ya5Q-iztY5eBJp3xOHvCrhYNWaAvJJ_e375MXqsnzrkJJAwgsSxgnuqzGDf3Wz3k-hUcvU_MSEU2GgGcldrJ-7cjEz0RwznPoM2f0iu_77CHcBIWZ4HOm2YCg";

    @Test
    void contextLoads() throws Exception {
        String apiClientId = "crcc12_ldsc";
        String apiClientSecret = "Zl7uGHIn84AjXcd2SsIN3074SvejkMuISkT90eCk";

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
            token = tokenEntity.getAccessToken();
            System.out.println(token);
        }
    }

    @Test
    public void objs() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/orglist ";

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<JSONObject[]> organization = restTemplate.exchange(url, HttpMethod.GET, entity, JSONObject[].class);
            List<JSONObject> objs=Arrays.asList(Objects.requireNonNull(organization.getBody()));
            System.out.println(objs.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void obj() {
        System.out.println("begin time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization","Bearer "+token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(headers);

        String url = "https://hrapitest.crcc.cn/api/hr/tree/hr/986436/users";

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<CRCCOrganizationDto> organization = restTemplate.exchange(url, HttpMethod.GET, entity, CRCCOrganizationDto.class);
            CRCCOrganizationDto obj = organization.getBody();
            assert obj != null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("end time : " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
    }

}
