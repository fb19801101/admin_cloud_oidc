package com.provider_oidc_ldsc.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.provider_oidc_ldsc.domain.DateUtils;
import com.provider_oidc_ldsc.domain.JwtUtils;
import com.provider_oidc_ldsc.domain.RPHolder;
import com.provider_oidc_ldsc.domain.TokenHolder;
import com.provider_oidc_ldsc.domain.auth.AuthRole;
import com.provider_oidc_ldsc.domain.auth.AuthRoleDetails;
import com.provider_oidc_ldsc.domain.auth.AuthRoleDetailsRepository;
import com.provider_oidc_ldsc.domain.shared.Application;
import com.provider_oidc_ldsc.entity.*;
import com.provider_oidc_ldsc.plan.DynamicPlan;
import com.provider_oidc_ldsc.plan.NetPlanItem;
import com.provider_oidc_ldsc.service.OIDCClientService;
import com.provider_oidc_ldsc.service.dto.*;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.jose4j.jwk.RsaJsonWebKey;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

/**
 * Handle 'authorization_code'  type actions
 *
 * From spring-oauth-client(https://gitee.com/mkk/spring-oauth-client)
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@RestController
@RequestMapping("/dev-api")
public class VueRetController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OIDCClientService clientService;

    @Autowired
    private AuthRoleDetailsRepository authRoleDetailsRepository;

    @Autowired
    private AuthEntityRepository authEntityRepository;

    @Autowired
    private LogEntityRepository logEntityRepository;

    @Autowired
    private PostChangeLogEntityRepository postChangeLogEntityRepository;

    @Autowired
    private OrgLogEntityRepository orgLogEntityRepository;

    @Autowired
    private DynamicPlan dynamicPlan;


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////   private static Value  ///////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static LocalDateTime invalidTokenDateTime;

    public static AccessTokenDto accessTokenDto;

    private static CRCCOrganizationDto crccOrganizationDto;

    private static List<CRCCUserInfoDto> crccUserInfoDtoList;

    private static ScheduledExecutorService timerOrganization;

//    private static String filterRedirectUri = "ldsc.cr121.com";
    private static String filterRedirectUri = "ames.cr121.com";


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////   public static final Value  ///////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private interface CRCCConstants {
        /**
         * crcc 中国铁建虚拟节点Provider
         */
        String NODE_PROVIDER = "crcc";

        /**
         * crcc 中国铁建虚拟节点Id
         */
        Integer NODE_ID = 2147483647;

        /**
         * crcc 中国铁建虚拟节点Code
         */
        String NODE_CODE = "00001";

        /**
         * crcc 中国铁建虚拟节点名称
         */
        String NODE_NAME = "中国铁建";

        /**
         * crcc 中国铁建虚拟节点Type
         */
        Integer NODE_TYPE = 0;

        /**
         * 开发环境 hr 甘忠忠 userProvider
         */
        String ADMIN_PROVIDER = "hr";

        /**
         * 开发环境 hr 甘忠忠 userID
         */
        Integer ADMIN_ID = 364035;

        /**
         * 开发环境 hr 甘忠忠 userName
         */
        String ADMIN_NAME = "甘忠忠";

        /**
         * 开发环境 hr 甘忠忠 AuthEntity
         */
        AuthEntity ADMIN_AUTH = new AuthEntity(0, "hr", 364035, "hr", 364035,
                0, 364034, "甘忠忠", 364034, "部员", "0000100001000597700199002", "总部/中铁十二局（勿动）/信息化管理部/部员", 0,
                "crcc", 2147483647, "00001", 0, "中国铁建", 0,
                "crcc", 2147483647, "00001", 0, "中国铁建", 0,
                1,2,1,1,1,1);

//        /**
//         * 生产环境 crcc12 方波 userProvider
//         */
//        String ADMIN_PROVIDER = "crcc12";
//
//        /**
//         * 生产环境 crcc12 方波 userID
//         */
//        Integer ADMIN_ID = 893297;
//
//        /**
//         * 生产环境 crcc12 方波 userName
//         */
//        String ADMIN_NAME = "方波";
//
//        /**
//         * 生产环境 crcc12 方波 AuthEntity
//         */
//        AuthEntity ADMIN_AUTH = new AuthEntity(0, "crcc12", 893297, "crcc12", 893297,
//                0, 191661, "方波", 191661, "经理", "000010001201001010007702799001", "中铁十二局/一公司/信息化管理部/经理", 0,
//                "crcc12", 1, "0000100012", 1, "中铁十二局", 0,
//                "crcc", 2147483647, "00001", 0, "中国铁建", 0,
//                1,2,1,1,1,1);
    }

    public VueRetController() {}

    /**
     * 验证 AccessToKen
     * @return
     */
    public boolean checkAccessToken() {
        try {
            String regCertsUrl = Application.getRegCertsUrl();
            String regCertsKid = Application.getRegCertsKid();
            RsaJsonWebKey key = JwtUtils.generalKeyFromUri(regCertsUrl, regCertsKid);

            if (key != null && request != null) {
                AccessTokenDto accessToken = JSON.parseObject(request.getHeader("Authorization"), AccessTokenDto.class);
                if(accessToken != null) {
                    Claims claims = JwtUtils.parserJwt(key.getRsaPublicKey(), accessToken.getAccessToken());
                    return claims != null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("JWT Verifier AccessToken Exception: checkAccessToken --> " + e.getMessage());
            return false;
        }

        return false;
    }

    /**
     * 获取请求头信息
     * @return
     */
    private Map<String, String> getHeadersInfo() {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////   Org接口   ///////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 应用API注册信息
     *
     * @param apiName
     * @return
     */
    @GetMapping("api_info")
    public ResultData<RPHolder> apiInfo(String apiName) {
        if (accessTokenDto == null || apiName == null || apiName.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        RPHolder rpHolder = apiRPHolder(apiName);
        if (!rpHolder.isConfigRP()) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), rpHolder, 1);
    }

    /**
     * 应用API列表
     *
     * @return
     */
    @GetMapping("api_list")
    public ResultData<List<RPHolder>> apiList() {
        List<RPHolder> apiList = clientService.loadApiRPHolders();
        if(apiList != null && !apiList.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), apiList, apiList.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 应用API注册
     *
     * @param apiName
     * @return
     */
    @GetMapping("api_check")
    public ResultData<AccessTokenDto> apiCheck(String apiName) {
        if (accessTokenDto == null || apiName == null || apiName.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        RPHolder rpHolder = apiRPHolder(apiName);
        if (!rpHolder.isConfigRP()) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        AccessTokenDto token = TokenHolder.loadAccessToken(rpHolder);

        if(token == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), token, 1);
    }

    /**
     * 应用API登录
     *
     * @param apiName
     * @return
     */
    @GetMapping("api_login")
    public ResultData<String> apiLogin(String apiName) {
        if (accessTokenDto == null || apiName == null || apiName.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), "AccessToken Failed", 1);
        }

        RPHolder rpHolder = apiRPHolder(apiName);
        if (!rpHolder.isConfigRP()) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), apiName+" call login failed", 1);
        }

        List<String> uris = rpHolder.getClientRedirectUris();
        for(String x: uris) {
            if(x.contains(filterRedirectUri)) {
                String uri = x.substring(0,x.indexOf("auth_callback"));

                AccessTokenDto token = TokenHolder.loadAccessToken(Application.getApiRPHolder());

                if(token == null) {
                    return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), "AccessToken Failed", 1);
                }

                String ret = TokenHolder.getStringApi(token, uri,"login","all-api");

                if(ret == null) {
                    return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), apiName+" call login failed", 1);
                }

                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), ret, 1);
            }
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), apiName+" call login failed", 1);
    }

    /**
     * 应用API调用
     *
     * @param apiName
     * @return
     */
    @GetMapping("api_call")
    public ResultData<String> apiCall(String apiName) {
        if (accessTokenDto == null || apiName == null || apiName.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), "AccessToken Failed", 1);
        }

        RPHolder rpHolder = apiRPHolder(apiName);
        if (!rpHolder.isConfigRP()) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), apiName+" call echo failed", 1);
        }

        List<String> uris = rpHolder.getClientRedirectUris();
        for(String x: uris) {
            if(x.contains(filterRedirectUri)) {
                String uri = x.substring(0,x.indexOf("auth_callback")-1);

                AccessTokenDto token = TokenHolder.loadAccessToken(Application.getApiRPHolder());

                if(token == null) {
                    return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), "AccessToken Failed", 1);
                }

                String ret = TokenHolder.getStringApi(token, uri,"echo","all-api");

                if(ret == null) {
                    return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), apiName+" call echo failed", 1);
                }

                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), ret, 1);
            }
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), apiName+" call echo failed", 1);
    }

    /**
     * 应用回调地址过滤
     *
     * @param filter
     * @return
     */
    @GetMapping("uri_filter")
    public ResultData<String> uriFilter(String filter) {
        if (filter == null || filter.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), "localhost", 1);
        }

        filterRedirectUri = filter;

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), filterRedirectUri, 1);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////   Portal接口   ///////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @GetMapping("add_todo")
    public ResultData<PortalData> addTodo(String routeName, Integer todoNumber) {
        if (accessTokenDto == null || routeName == null || routeName.length() == 0 || todoNumber == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        TodoEntity todo = new TodoEntity("C00000", "操作成功，应用存在两条待办事项");
        todo.addDataItem(routeName, todoNumber);

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), new PortalData(todo), 1);
    }

    @GetMapping("set_todo_number")
    public ResultData<TodoEntity> setTodoNumber(Integer todoNumber) {
        if (accessTokenDto == null || todoNumber == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        TodoEntity todo = PortalData.getToto();
        if(todo != null) {
            int number = Math.min(todo.getTodoNumber() - 1, todoNumber);
            todo.setTodoNumber(Math.max(number, 0));
            todo.setTotalNum(Math.max(number, 0));
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), todo, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    @GetMapping("get_todo")
    public ResultData<TodoEntity> getTodo() {
        if (accessTokenDto == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        TodoEntity todo = PortalData.getToto();
        if(todo != null) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), todo, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    @GetMapping("get_todo_number")
    public ResultData<Integer> getTodoNumber() {
        if (accessTokenDto == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        TodoEntity todo = PortalData.getToto();
        if(todo != null) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), todo.getTodoNumber(), 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    @GetMapping("clear_todo")
    public ResultData<PortalData> clearTodo() {
        if (accessTokenDto == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        TodoEntity todo = new TodoEntity("操作成功，应用没有任何待办事项");
        PortalData.nullTodo();

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), new PortalData(todo), 1);
    }

    @GetMapping("add_presses")
    public ResultData<PortalData> addPresses(String code, String headline, String routeName) {
        if (accessTokenDto == null || code == null || code.length() == 0 || headline == null || headline.length() == 0 || routeName == null || routeName.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        PressesEntity presses = new PressesEntity("C00000", "操作成功，应用有数字铁建聚合信息");
        presses.addDataItem(code, headline, routeName);

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), new PortalData(presses), 1);
    }

    @GetMapping("add_presses_item")
    public ResultData<PressesEntity> addPressesItem(String code, String headline, String routeName) {
        if (accessTokenDto == null || code == null || code.length() == 0 || headline == null || headline.length() == 0 || routeName == null || routeName.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        PressesEntity presses = PortalData.getPresses();
        if (presses != null) {
            presses.addDataItem(code, headline, routeName);
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), presses, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    @GetMapping("set_presses_item_unread")
    public ResultData<PressesEntity> setPressesItemUnread(String code, Boolean unread) {
        if (accessTokenDto == null || unread == null || code == null || code.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        PressesEntity presses = PortalData.getPresses();
        if(presses != null) {
            presses.setPressesDataItemUnread(code, unread);
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), presses, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    @GetMapping("get_presses")
    public ResultData<PressesEntity> getPresses() {
        if (accessTokenDto == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        PressesEntity presses = PortalData.getPresses();
        if(presses != null) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), presses, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    @GetMapping("get_presses_item")
    public ResultData<JSONObject> getPressesItem(String code) {
        if (accessTokenDto == null || code == null || code.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        PressesEntity presses = PortalData.getPresses();
        if(presses != null) {
            JSONObject json = presses.getDataItem(code);
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), json, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    @GetMapping("get_presses_items")
    public ResultData<JSONArray> getPressesItems(Boolean unread) {
        if (accessTokenDto == null || unread == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        PressesEntity presses = PortalData.getPresses();
        if(presses != null) {
            JSONArray jsons = presses.findDataUnreadItems(unread);
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), jsons, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    @GetMapping("get_presses_item_unread")
    public ResultData<Boolean> getPressesItemUnread(String code) {
        if (accessTokenDto == null || code == null || code.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        PressesEntity presses = PortalData.getPresses();
        if(presses != null) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), presses.getPressesDataItemUnread(code), 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    @GetMapping("del_presses_item")
    public ResultData<String> delPressesItem(String code) {
        if (accessTokenDto == null || code == null || code.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        PressesEntity presses = PortalData.getPresses();
        if(presses != null) {
            presses.delDataItem(code);
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), "消息信息删除完成", 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    @GetMapping("del_presses_items")
    public ResultData<String> delPressesItems(Boolean unread) {
        if (accessTokenDto == null || unread == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        PressesEntity presses = PortalData.getPresses();
        if(presses != null) {
            presses.delDataUnreadItems(unread);
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), "消息信息删除完成", 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    @GetMapping("clear_presses")
    public ResultData<PortalData> clearPresses() {
        if (accessTokenDto == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        PressesEntity presses = new PressesEntity("操作成功，应用没有任何推送消息");
        PortalData.nullPresses();

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), new PortalData(presses), 1);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////   Org接口   ///////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 获取当前用户信息
     *
     * @param providerId
     * @param userId
     * @return
     */
    @GetMapping(value = "current_user_info")
    public ResultData<CRCCUserInfoDto> currentUserInfo(String providerId, Integer userId) {
        CRCCUserInfoDto userInfo = apiGetUser(providerId, userId, false, false);

        if (userInfo != null) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), userInfo, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取当前用户权限
     *
     * @param providerId
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "current_user_auth")
    public ResultData<AuthEntity> currentUserAuth(String providerId, Integer userId) {
        AuthEntity authEntity = authLoginEntity(providerId, userId);

        if (authEntity != null) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), authEntity, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 当前系统铁建节点和管理员ID
     *
     * @throws Exception
     */
    @GetMapping(value = "current_crcc_node_admin")
    public ResultData<JSONObject> currentCRCCNodeAndAdmin()  {
        JSONObject json = new JSONObject();
        json.put("crccNodeProvider", CRCCConstants.NODE_PROVIDER);
        json.put("crccNodeId", CRCCConstants.NODE_ID);
        json.put("crccNodeName", CRCCConstants.NODE_NAME);
        json.put("crccNodeCode", CRCCConstants.NODE_CODE);
        json.put("crccNodeType", CRCCConstants.NODE_TYPE);
        json.put("crccAdminProvider", CRCCConstants.ADMIN_PROVIDER);
        json.put("crccAdminId", CRCCConstants.ADMIN_ID);
        json.put("crccAdminName", CRCCConstants.ADMIN_NAME);

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), json, 1);

    }

    /**
     * 当前用户权限根节点
     *
     * @param providerId
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("current_auth_root_node")
    public ResultData<List<CRCCOrganizationDto>> currentAuthRootNode(String providerId, Integer userId) {
        AuthEntity authEntity = authLoginEntity(providerId, userId);
        CRCCOrganizationDto orgAuthRoot;
        if(authEntity != null) {
            orgAuthRoot = apiGetOrganization(authEntity.getNodeProvider(), authEntity.getNodeId(), authEntity.getNodeType());
            if(orgAuthRoot != null) {
                List<CRCCOrganizationDto> orgList = new ArrayList<>();
                orgList.add(orgAuthRoot);
                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), orgList, 1);
            }
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取任一组织机构直接下级机构集合，集合中过滤type内容
     *
     * @param providerId
     * @param orgId
     * @param orgType
     * @param filterType
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_organization_children_filter")
    public ResultData<List<CRCCOrganizationDto>> getOrganizationChildren(String providerId, Integer orgId, Integer orgType, Integer filterType) {
        if (providerId == null || providerId.length() == 0 || orgId == null || orgType == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        List<CRCCOrganizationDto> orgList = apiGetOrganizationChildren(providerId, orgId, orgType);

        if(orgList != null && !orgList.isEmpty()) {
            orgList.removeIf(x -> x.getType().equals(filterType));
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), orgList, orgList.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取任一组织机构直接下级机构集合
     *
     * @param providerId
     * @param orgId
     * @param orgType
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_organization_children")
    public ResultData<List<CRCCOrganizationDto>> getOrganizationChildren(String providerId, Integer orgId, Integer orgType) {
        if (providerId == null || providerId.length() == 0 || orgId == null || orgType == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        List<CRCCOrganizationDto> orgList = apiGetOrganizationChildren(providerId, orgId, orgType);

        if(orgList != null && !orgList.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), orgList, orgList.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取部门下所有用户集合
     *
     * @param providerId
     * @param deptId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_department_users")
    public ResultData<List<CRCCUserInfoDto>> getDepartmentUsers(String providerId, Integer deptId) {
        if (providerId == null || providerId.length() == 0 || deptId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        List<CRCCUserInfoDto> listUsers = new ArrayList<>();
        List<CRCCOrganizationDto> listOrganization = apiGetDepartmentChildren(providerId, deptId);
        if(listOrganization != null) {
            for (CRCCOrganizationDto x : listOrganization) {
                if (x.getType() == 3) {
                    List<CRCCUserInfoDto> list = apiGetPositionUsers(providerId, x.getId());
                    if(list != null) {
                        listUsers.addAll(list);
                    }
                } else if (x.getType() == 2) {
                    List<CRCCOrganizationDto> listPosition = apiGetDepartmentChildren(providerId, x.getId());
                    if(listPosition != null) {
                        for (CRCCOrganizationDto y : listPosition) {
                            List<CRCCUserInfoDto> list = apiGetPositionUsers(providerId, y.getId());
                            if(list != null) {
                                listUsers.addAll(list);
                            }
                        }
                    }
                }
            }
        }

        if (!listUsers.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), listUsers, listUsers.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取岗位下所有用户集合
     *
     * @param providerId
     * @param positionId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_position_users")
    public ResultData<List<CRCCUserInfoDto>> getPositionUsers_(String providerId, Integer positionId) {
        if (providerId == null || providerId.length() == 0 || positionId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        List<CRCCUserInfoDto> listUsers = apiGetPositionUsers(providerId, positionId);

        if (listUsers != null && !listUsers.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), listUsers, listUsers.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取任一组织路径
     *
     * @param providerId
     * @param orgId
     * @param level
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_organization_path")
    public ResultData<String> getOrganizationPath_(String providerId, Integer orgId, Integer level, Boolean virtual) {
        if (providerId == null || providerId.length() == 0 || orgId == null || level == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        String path = apiGetOrganizationPathStr(providerId, orgId, level, virtual);

        if (path != null && !path.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), path, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////   static接口   ////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 初始化用户列表及组织机构树
     *
     * @param optProvider
     * @param optId
     * @param providerId
     * @param orgId
     * @param orgType
     * @return
     * @throws Exception
     */
    @GetMapping(value = "init_organization_and_users")
    public ResultData<Boolean> initOrganizationAndUsers(String optProvider, Integer optId, String providerId, Integer orgId, Integer orgType) {
        if (providerId == null || providerId.length() == 0 || orgId == null || orgType == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), false, 1);
        }

        AuthEntity authEntity = authLoginEntity(optProvider, optId);
        syncInitOrganizationAndUsers(providerId, orgId, orgType, authEntity);

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), true, 1);
    }

    /**
     * 获取用户的组织机构
     *
     * @param providerId
     * @param orgId
     * @param orgType
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_organization_and_users")
    public ResultData<CRCCOrganizationDto> getOrganizationAndUsers(String providerId, Integer orgId, Integer orgType) {
        if (providerId == null || providerId.length() == 0 || orgId == null || orgType == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        ExecutorService es = Executors.newCachedThreadPool();
        es.submit(() -> {
            try {
                crccOrganizationDto = syncTreeOrganizationAndUsers(providerId, orgId, orgType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        es.shutdown();

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), crccOrganizationDto, 1);
    }
    public ResultData<CRCCOrganizationDto> getAsyncOrganizationAndUsers(String providerId, Integer orgId, Integer orgType) throws Exception {
        if (providerId == null || providerId.length() == 0 || orgId == null || orgType == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        asyncTreeOrganizationAndUsers(providerId, orgId, orgType);

        if(crccOrganizationDto != null) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), crccOrganizationDto, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 对组织人员树进行排序和分级
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "init_tree_level_and_sort")
    public ResultData<CRCCOrganizationDto> initTreeLevelAndSort() {
        if(crccOrganizationDto != null) {
            initOrganizationAndUsersTree(crccOrganizationDto);

            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), crccOrganizationDto, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取用户的组织机构，末节点到部门
     *
     * @param providerId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_organization_with_department")
    public ResultData<CRCCOrganizationDto> getOrganizationWithDepartment(String providerId) throws Exception {
        if (providerId == null || providerId.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        if(crccOrganizationDto != null) {
            CRCCOrganizationDto org = crccOrganizationDto.clone();
            CRCCOrganizationDto.filterOrganizationByType(org, providerId, 3);

            if(org != null) {
                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), org, 1);
            }
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取用户的组织机构，末节点到岗位
     *
     * @param providerId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_organization_with_position")
    public ResultData<CRCCOrganizationDto> getOrganizationWithPosition(String providerId) throws Exception {
        if (providerId == null || providerId.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        if(crccOrganizationDto != null) {
            CRCCOrganizationDto org = crccOrganizationDto.clone();
            CRCCOrganizationDto.filterOrganizationByType(org, providerId, 4);

            if(org != null) {
                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), org, 1);
            }
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取用户的组织机构，末节点到人员
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_organization_with_users")
    public ResultData<CRCCOrganizationDto> getOrganizationWithUsers() throws Exception {
        if(crccOrganizationDto != null) {
            CRCCOrganizationDto org = crccOrganizationDto.clone();
            CRCCOrganizationDto.plusOrganizationFromUsers(org);

            if(org != null) {
                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), org, 1);
            }
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取机构下用户信息，通过用户id
     *
     * @param providerId
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_organization_by_user")
    public ResultData<CRCCOrganizationDto> getOrganizationByUserId(String providerId, Integer userId) {
        if (providerId == null || providerId.length() == 0 || userId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        if(crccOrganizationDto != null) {
            CRCCOrganizationDto org = CRCCOrganizationDto.findOrganizationByUserId(crccOrganizationDto, providerId, userId);

            if(org != null) {
                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), org, 1);
            }
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取机构下用户集合，通过党委、部门、岗位类型
     *
     * @param providerId
     * @param type
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_organization_by_type")
    public ResultData<List<CRCCOrganizationDto>> getOrganizationByType(String providerId, Integer type) {
        if (providerId == null || providerId.length() == 0 || type == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        if(crccOrganizationDto != null) {
            List<CRCCOrganizationDto> list = CRCCOrganizationDto.findOrganizationListByType(crccOrganizationDto, providerId, type);

            if(!list.isEmpty()) {
                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
            }
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取机构下用户集合，通过党委、部门、岗位id
     *
     * @param optProvider
     * @param optId
     * @param providerId
     * @param orgId
     * @param offset
     * @param limit
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_organization_users_with_offset_and_limit")
    public ResultData<List<CRCCUserInfoDto>> getOrganizationUsersWithOffsetAndLimit(String optProvider, Integer optId, String providerId, Integer orgId, Integer offset, Integer limit) {
        if (providerId == null || providerId.length() == 0 || orgId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        AuthEntity authEntity = authLoginEntity(optProvider, optId);
        if(crccOrganizationDto != null && authEntity != null) {
            List<CRCCUserInfoDto> list;
            if(orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
                if(authEntity.getNodeId().compareTo(CRCCConstants.NODE_ID) == 0) {
                    list = CRCCOrganizationDto.findOrganizationUsers(crccOrganizationDto);
                } else {
                    CRCCOrganizationDto org = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, authEntity.getNodeProvider(), authEntity.getNodeId());
                    list = CRCCOrganizationDto.findOrganizationUsers(org, offset, limit);
                }
            } else {
                CRCCOrganizationDto org = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, providerId, orgId);
                list = CRCCOrganizationDto.findOrganizationUsers(org, offset, limit);
            }

            if(!list.isEmpty()) {
                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
            }
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取机构下用户集合，通过党委、部门、岗位id
     *
     * @param providerId
     * @param orgId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_search_users")
    public ResultData<List<CRCCUserInfoDto>> getSearchUsers(String providerId, Integer orgId, Integer orgType, String filter) throws InterruptedException {

        List<CRCCUserInfoDto> listUser = apiSearchUserInfo(providerId, orgId, orgType, "%"+filter+"%");

        if(listUser != null && !listUser.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), listUser, listUser.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取机构下用户集合，通过党委、部门、岗位id
     *
     * @param optProvider
     * @param optId
     * @param providerId
     * @param orgId
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_user_list")
    public ResultData<Boolean> getUserList(String optProvider, Integer optId, String providerId, Integer orgId) {
        if (providerId == null || providerId.length() == 0 || orgId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), false, 1);
        }

        AuthEntity authEntity = authLoginEntity(optProvider, optId);
        crccUserInfoDtoList = syncInitUserInfoList(providerId, orgId, authEntity);

        if(!crccUserInfoDtoList.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), true, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), false, 1);
    }

    /**
     * 获取机构下用户集合总数，通过党委、部门、岗位id
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_users_count")
    public ResultData<Integer> getUsersCount() {
        if(crccUserInfoDtoList != null) {
            int count = crccUserInfoDtoList.size();

            if(count > 0) {
                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), count, 1);
            }
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取机构下用户集合，通过党委、部门、岗位id
     *
     * @param offset
     * @param limit
     * @return
     * @throws Exception
     */
    @GetMapping(value = "get_users_with_offset_and_limit")
    public ResultData<List<CRCCUserInfoDto>> getUsersWithOffsetAndLimit(Integer offset, Integer limit) {
        if(crccUserInfoDtoList != null) {
            List<CRCCUserInfoDto> list = CRCCUserInfoDto.findUserInfoList(crccUserInfoDtoList, offset, limit);

            if(list != null && !list.isEmpty()) {
                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
            }
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取人员实时集合，通过人员id列表
     *
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(value = "get_users_info")
    public ResultData<List<CRCCUserInfoDto>> getUsersInfo(@RequestBody JSONObject params) {
        if (params == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        List<CRCCUserInfoDto> listUsers = new ArrayList<>();
        if(params.containsKey("params")) {
            JSONArray jsons = params.getJSONArray("params");
            if(!jsons.isEmpty()) {
                for (int i = 0; i < jsons.size(); i++) {
                    String provider = jsons.getJSONObject(i).getString("provider");
                    Integer id = jsons.getJSONObject(i).getInteger("id");
                    CRCCUserInfoDto user = apiGetUser(provider, id, true, true);
                    if (user != null) {
                        listUsers.add(user);
                    }
                }
            }
        }

        if(!listUsers.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), listUsers, listUsers.size());
        }


        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////   auth接口   //////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 添加用户权限
     *
     * @param orgId
     * @param organizationDto
     * @param providerId
     * @param userId
     * @param authRole
     * @return
     * @throws Exception
     */
    @PostMapping(value = "insert_organization_auth")
    public ResultData<AuthRoleDetails> insertOrganizationAuth(Integer orgId, CRCCOrganizationDto organizationDto, String providerId, Integer userId, AuthRole authRole) {
        if (providerId == null || providerId.length() == 0 || userId == null || authRole == null || orgId == null || organizationDto == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        CRCCOrganizationDto authOrganizationDto = CRCCOrganizationDto.findOrganizationById(organizationDto, providerId, orgId);
        AuthRoleDetails authRoleDetails = new AuthRoleDetails();
        if(authOrganizationDto != null) {
            authRoleDetails.setProviderId(providerId);
            authRoleDetails.setUserId(userId);

            authRoleDetails.setAuthNode(authOrganizationDto.getId());
            authRoleDetails.setAuthCode(authOrganizationDto.getCode());

            authRoleDetails.setAuthPath(apiGetOrganizationPathStr(providerId, authOrganizationDto.getId(), 1, true));
            authRoleDetails.setAuthType(authOrganizationDto.getType() == 3);
            authRoleDetails.setAuthRole(authRole);
            authRoleDetails.setAuthFlag(false);
            authRoleDetails.setCreateTime(LocalDateTime.now());

            authRoleDetailsRepository.insertAuthRoleDetails(authRoleDetails);
        }

        AuthRoleDetails _authRoleDetails = authRoleDetailsRepository.getCurrentAuthRoleDetailsByUserId(userId);

        if(_authRoleDetails != null) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), _authRoleDetails, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 修改用户权限
     *
     * @param orgId
     * @param organizationDto
     * @param providerId
     * @param userId
     * @param authRole
     * @param authFlag
     * @return
     * @throws Exception
     */
    @PostMapping(value = "update_organization_auth")
    public ResultData<AuthRoleDetails> updateOrganizationAuth(Integer orgId, CRCCOrganizationDto organizationDto, String providerId, Integer userId, AuthRole authRole, Boolean authFlag) {
        if (providerId == null || providerId.length() == 0 || userId == null || authRole == null || orgId == null || organizationDto == null || authFlag == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        CRCCOrganizationDto authOrganizationDto = CRCCOrganizationDto.findOrganizationById(organizationDto, providerId, orgId);
        AuthRoleDetails authRoleDetails = authRoleDetailsRepository.getCurrentAuthRoleDetailsByUserId(userId);

        if(authOrganizationDto != null) {
            authRoleDetails.setProviderId(providerId);

            authRoleDetails.setAuthNode(authOrganizationDto.getId());
            authRoleDetails.setAuthCode(authOrganizationDto.getCode());

            authRoleDetails.setAuthPath(apiGetOrganizationPathStr(providerId, authOrganizationDto.getId(), 1, true));
            authRoleDetails.setAuthType(authOrganizationDto.getType() == 3);
            authRoleDetails.setAuthRole(authRole);
            authRoleDetails.setAuthFlag(authFlag);
            authRoleDetails.setCreateTime(LocalDateTime.now());

            authRoleDetailsRepository.updateAuthRoleDetails(authRoleDetails);
        }

        if(authRoleDetails != null) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), authRoleDetails, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 查询授权列表
     *
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("query_auth_by_id")
    public ResultData<AuthEntity> queryAuthById(Integer id) {
        if (id == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        AuthEntity authEntity = authEntityRepository.getAuthEntityById(id);

        if(authEntity != null) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), authEntity, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 查询授权列表
     *
     * @param nodeId
     * @return
     * @throws Exception
     */
    @GetMapping("query_auth_by_node")
    public ResultData<List<AuthEntity>> queryAuthByNode(String nodeProvider, Integer nodeId) {
        if (nodeProvider == null || nodeProvider.length() == 0 || nodeId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        List<AuthEntity>list = authEntityRepository.getAuthEntityByNodeId(nodeProvider, nodeId);

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 查询授权列表
     *
     * @param nodeCode
     * @return
     * @throws Exception
     */
    @GetMapping("query_auth_by_code")
    public ResultData<List<AuthEntity>> queryAuthByCode(String nodeProvider, String nodeCode) {
        if (nodeProvider == null || nodeProvider.length() == 0 || nodeCode == null || nodeCode.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        List<AuthEntity>list;
        if(nodeProvider.compareTo(CRCCConstants.NODE_PROVIDER) == 0 && nodeCode.compareTo(CRCCConstants.NODE_CODE) == 0) {
            list = authEntityRepository.getAllAuthEntity();
        } else {
            list = authEntityRepository.getAuthEntityByNodeCode(nodeProvider, nodeCode);
        }

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 查询授权列表
     *
     * @param providerId
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("query_auth_by_user_id")
    public ResultData<CRCCOrganizationDto> queryAuthByUserId(String providerId, Integer userId) {
        if (providerId == null || providerId.length() == 0 || userId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        AuthEntity authEntity = authLoginEntity(providerId, userId);

        if(authEntity != null) {
            CRCCOrganizationDto org;
            if(authEntity.getNodeId().compareTo(CRCCConstants.NODE_ID) == 0) {
                org = apiGetCompanyRoot();
            } else {
                org = apiGetCompanyParent(providerId, authEntity.getNodeId());
            }

            if(org != null) {
                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), org, 1);
            }
        }
        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 查询授权列表
     *
     * @param providerId
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("query_auth_list_by_user_id")
    public ResultData<List<CRCCOrganizationDto>> queryAuthListByUserId(String providerId, Integer userId) {
        if (providerId == null || providerId.length() == 0 || userId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        AuthEntity authEntity = authLoginEntity(providerId, userId);

        if(authEntity != null) {
            CRCCOrganizationDto org = apiGetOrganization(authEntity.getNodeProvider(), authEntity.getNodeId(), authEntity.getNodeType());

            if(org != null) {
                List<CRCCOrganizationDto> orgList = new ArrayList<>();
                orgList.add(org);
                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), orgList, 1);
            }
        }
        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 查询授权列表根节点
     *
     * @param providerId
     * @param userId
     * @return
     * @throws Exception
     */
    @GetMapping("query_auth_node_root")
    public ResultData<List<CRCCOrganizationDto>> queryAuthNodeRoot(String providerId, Integer userId) {
        if (providerId == null || providerId.length() == 0 || userId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        AuthEntity authEntity = authLoginEntity(providerId, userId);

        if(authEntity != null) {
            CRCCOrganizationDto org = apiGetCompanyParent(authEntity.getNodeProvider(), authEntity.getNodeId());
            if(org != null) {
                List<CRCCOrganizationDto> orgList = new ArrayList<>();
                orgList.add(org);
                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), orgList, 1);
            }
        }
        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取菜单权限
     * @param providerId
     * @param userId
     * @param objProvider
     * @param objId
     * @return
     */
    @GetMapping("query_menu_auth")
    public ResultData<List<MenuAuth>> queryMenuAuth(String providerId, Integer userId, String objProvider, Integer objId) {
        if (providerId == null || providerId.length() == 0 || userId == null || objProvider == null || objProvider.length() == 0 || objId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        AuthEntity authEntity = authLoginEntity(objProvider, objId);
        if(authEntity == null){
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        AuthEntity operatorAuth = authLoginEntity(providerId, userId);
        if(operatorAuth == null){
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        List<MenuAuth> list = new ArrayList<>();

        MenuAuth menuAuth = new MenuAuth(operatorAuth.getAuthOrg());
        list.add(new MenuAuth("组织人员浏览", authEntity.getAuthOrg(), menuAuth.get_manage(), menuAuth.get_scan()));
        menuAuth = new MenuAuth(operatorAuth.getAuthOpAuth());
        list.add(new MenuAuth("授权管理", authEntity.getAuthOpAuth(), menuAuth.get_manage(), menuAuth.get_scan()));
        menuAuth = new MenuAuth(operatorAuth.getAuthQueryAuth());
        list.add(new MenuAuth("授权查询", authEntity.getAuthQueryAuth(), menuAuth.get_manage(), menuAuth.get_scan()));
        menuAuth = new MenuAuth(operatorAuth.getAuthLog());
        list.add(new MenuAuth("系统日志", authEntity.getAuthLog(), menuAuth.get_manage(), menuAuth.get_scan()));
        menuAuth = new MenuAuth(operatorAuth.getAuthPostChange());
        list.add(new MenuAuth("岗位变化查询", authEntity.getAuthPostChange(), menuAuth.get_manage(), menuAuth.get_scan()));
        menuAuth = new MenuAuth(operatorAuth.getAuthPostDelete());
        list.add(new MenuAuth("组织删除查询", authEntity.getAuthPostDelete(), menuAuth.get_manage(), menuAuth.get_scan()));

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, 1);
    }

    /**
     * 添加人员授权
     *
     * @param providerId
     * @param operatorId
     * @param objProvider
     * @param objId
     * @param objType
     * @param nodeProvider
     * @param nodeId
     * @return
     * @throws Exception
     */
    @GetMapping("add_auth_")
    public ResultData<AuthEntity> addAuth(String providerId, Integer operatorId, String objProvider, Integer objId, Integer objType, String nodeProvider, Integer nodeId) {
        if (providerId == null || providerId.length() == 0 || operatorId == null || objId == null || objType == null || nodeId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        AuthEntity authEntity = authLoginEntity(objProvider, objId);

        if(crccOrganizationDto != null) {
            CRCCOrganizationDto organizationDto_opt = CRCCOrganizationDto.findOrganizationByUserId(crccOrganizationDto, providerId, operatorId);
            CRCCOrganizationDto organizationDto_obj = objType == 0 ? CRCCOrganizationDto.findOrganizationByUserId(crccOrganizationDto, objProvider, objId)
                    : CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, objProvider, objId);
            CRCCOrganizationDto organizationDto_node = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, nodeProvider, nodeId);
            CRCCOrganizationDto organizationDto_node_parent = CRCCOrganizationDto.findOrganizationParentById(crccOrganizationDto, nodeProvider, nodeId);

            if (organizationDto_opt != null && organizationDto_obj != null && organizationDto_node != null) {
                String path_opt = CRCCOrganizationDto.findOrganizationPathStrById(crccOrganizationDto, providerId, organizationDto_opt.getId());
                String path_obj = CRCCOrganizationDto.findOrganizationPathStrById(crccOrganizationDto, objProvider, organizationDto_obj.getId());
                String path_node = CRCCOrganizationDto.findOrganizationPathStrById(crccOrganizationDto, nodeProvider, organizationDto_node.getId());
                String path_node_parent = CRCCConstants.NODE_NAME;
                if(organizationDto_node_parent != null) {
                    path_node_parent = CRCCOrganizationDto.findOrganizationPathStrById(crccOrganizationDto, nodeProvider, organizationDto_node_parent.getId());
                }

                CRCCUserInfoDto userInfoDto_opt = CRCCOrganizationDto.findOrganizationUserByUserId(crccOrganizationDto, providerId, operatorId);
                CRCCUserInfoDto userInfoDto_obj = CRCCOrganizationDto.findOrganizationUserByUserId(crccOrganizationDto, objProvider, objId);

                LogEntity logEntity = new LogEntity();

                if (authEntity == null) {
                    authEntity = new AuthEntity();
                    authEntity.setProviderId(providerId);
                    authEntity.setOperatorId(operatorId);

                    authEntity.setObjProvider(objProvider);
                    authEntity.setObjId(objId);
                    authEntity.setObjType(objType);
                    authEntity.setObjNode(organizationDto_obj.getId());
                    authEntity.setObjName(objType == 0 && userInfoDto_obj != null ? userInfoDto_obj.getName() : organizationDto_obj.getName());
                    authEntity.setObjPostId(organizationDto_obj.getId());
                    authEntity.setObjPostName(organizationDto_obj.getName());
                    authEntity.setObjPostCode(organizationDto_obj.getCode());
                    authEntity.setObjPath(path_obj);

                    authEntity.setObjIndex(-1);
                    if(userInfoDto_obj != null) {
                        Integer orgIndex = CRCCOrganizationDto.findOrganizationIndexByUserId(crccOrganizationDto, userInfoDto_obj.getProvider(), userInfoDto_obj.getId());
                        if (orgIndex != null && objType == 0) {
                            authEntity.setObjIndex(orgIndex);
                        }
                    }

                    CRCCOrganizationDto orgObj = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_obj.getProvider(), organizationDto_obj.getId());
                    if(orgObj != null && objType == 1) {
                        authEntity.setObjIndex(orgObj.getIndex());
                    }

                    authEntity.setNodeProvider(nodeProvider);
                    authEntity.setNodeId(nodeId);
                    authEntity.setNodeCode(organizationDto_node.getCode());
                    authEntity.setNodeType(organizationDto_node.getType());
                    authEntity.setNodePath(path_node);

                    logEntity.setPostProvider(organizationDto_obj.getProvider());
                    logEntity.setPostId(organizationDto_obj.getId());
                    logEntity.setPostCode(organizationDto_obj.getCode());
                    logEntity.setPostPath(path_obj);

                    CRCCOrganizationDto orgNode = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());
                    if(orgNode == null) {
                        apiInsertOrganization(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());

                        orgNode = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());
                    }
                    if(orgNode != null) {
                        authEntity.setNodeIndex(orgNode.getIndex());
                    } else {
                        authEntity.setNodeIndex(-1);
                    }

                    if(organizationDto_node_parent != null) {
                        authEntity.setNodeParentProvider(organizationDto_node_parent.getProvider());
                        authEntity.setNodeParentId(organizationDto_node_parent.getId());
                        authEntity.setNodeParentCode(organizationDto_node_parent.getCode());
                        authEntity.setNodeParentType(organizationDto_node_parent.getType());
                        authEntity.setNodeParentPath(path_node_parent);

                        CRCCOrganizationDto orgNodeParent = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node_parent.getProvider(), organizationDto_node_parent.getId());
                        if(orgNodeParent != null) {
                            authEntity.setNodeParentIndex(orgNodeParent.getIndex());
                        }
                    } else {
                        authEntity.setNodeParentProvider(CRCCConstants.NODE_PROVIDER);
                        authEntity.setNodeParentId(CRCCConstants.NODE_ID);
                        authEntity.setNodeParentCode(CRCCConstants.NODE_CODE);
                        authEntity.setNodeParentType(CRCCConstants.NODE_TYPE);
                        authEntity.setNodeParentPath(CRCCConstants.NODE_NAME);

                        CRCCOrganizationDto orgNodeParent = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, CRCCConstants.NODE_PROVIDER, CRCCConstants.NODE_ID);
                        if(orgNodeParent != null) {
                            authEntity.setNodeParentIndex(orgNodeParent.getIndex());
                        }
                    }

                    authEntity.setAuthRole(AuthRole.VIEW);
                    authEntity.setAuthFlag(false);
                    authEntity.setAuthOrg(1);
                    authEntity.setAuthOpAuth(0);
                    authEntity.setAuthQueryAuth(0);
                    authEntity.setAuthLog(0);
                    authEntity.setAuthPostChange(0);
                    authEntity.setAuthPostDelete(0);

                    authEntity.setCreateTime(LocalDateTime.now());
                    //添加一条权限
                    authEntityRepository.insertAuthEntity(authEntity);
                } else {
                    authEntity.setNodeProvider(nodeProvider);
                    authEntity.setNodeId(nodeId);
                    authEntity.setNodeCode(organizationDto_node.getCode());
                    authEntity.setNodeType(organizationDto_node.getType());
                    authEntity.setNodePath(path_node);

                    logEntity.setPostProvider(organizationDto_obj.getProvider());
                    logEntity.setPostId(organizationDto_obj.getId());
                    logEntity.setPostCode(organizationDto_obj.getCode());
                    logEntity.setPostPath(path_obj);

                    CRCCOrganizationDto orgNode = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());
                    if(orgNode == null) {
                        apiInsertOrganization(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());

                        orgNode = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());
                    }
                    if(orgNode != null) {
                        authEntity.setNodeIndex(orgNode.getIndex());
                    } else {
                        authEntity.setNodeIndex(-1);
                    }

                    if(organizationDto_node_parent != null) {
                        authEntity.setNodeParentProvider(organizationDto_node_parent.getProvider());
                        authEntity.setNodeParentId(organizationDto_node_parent.getId());
                        authEntity.setNodeParentCode(organizationDto_node_parent.getCode());
                        authEntity.setNodeParentType(organizationDto_node_parent.getType());
                        authEntity.setNodeParentPath(path_node_parent);

                        CRCCOrganizationDto orgNodeParent = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node_parent.getProvider(), organizationDto_node_parent.getId());
                        if(orgNodeParent != null) {
                            authEntity.setNodeParentIndex(orgNodeParent.getIndex());
                        }
                    } else {
                        authEntity.setNodeParentProvider(CRCCConstants.NODE_PROVIDER);
                        authEntity.setNodeParentId(CRCCConstants.NODE_ID);
                        authEntity.setNodeParentCode(CRCCConstants.NODE_CODE);
                        authEntity.setNodeParentType(CRCCConstants.NODE_TYPE);
                        authEntity.setNodeParentPath(CRCCConstants.NODE_NAME);

                        CRCCOrganizationDto orgNodeParent = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, CRCCConstants.NODE_PROVIDER, CRCCConstants.NODE_ID);
                        if(orgNodeParent != null) {
                            authEntity.setNodeParentIndex(orgNodeParent.getIndex());
                        }
                    }

                    //添加一条权限
                    authEntityRepository.updateAuthEntity(authEntity);
                }

                MenuAuth menuAuth = new MenuAuth("组织人员浏览", authEntity.getAuthOrg());
                StringBuilder content = new StringBuilder(objType == 0 ? "增加用户授权对象：" : "增加岗位授权对象")
                        .append(userInfoDto_obj != null ? userInfoDto_obj.getName() : organizationDto_obj.getName())
                        .append("，对象所属组织：")
                        .append(path_obj)
                        .append("，被授权组织：")
                        .append(organizationDto_node.getName())
                        .append("，被授权功能：")
                        .append(menuAuth.log());

                logEntity.setContent(content.toString());

                logEntity.setUserName(userInfoDto_opt != null ? userInfoDto_opt.getName() : organizationDto_opt.getName());
                logEntity.setUserPath(path_opt);
                logEntity.setCreateTime(LocalDateTime.now());

                //添加一条日志
                logEntityRepository.insertLogEntity(logEntity);
            }
        }

        if(authEntity != null) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), authEntity, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 添加人员授权
     *
     * @param providerId
     * @param operatorId
     * @param objProvider
     * @param objId
     * @param objType
     * @param nodeProvider
     * @param nodeId
     * @return
     * @throws Exception
     */
    @GetMapping("add_auth")
    public ResultData<AuthEntity> addAuth(String providerId, Integer operatorId, String objProvider, Integer objId, Integer objType, String nodeProvider, Integer nodeId, Integer nodeType) {
        if (providerId == null || providerId.length() == 0 || operatorId == null || objProvider == null || objProvider.length() == 0 || objId == null || objType == null || nodeProvider == null || nodeProvider.length() == 0 || nodeId == null || nodeType == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        AuthEntity authEntity = authLoginEntity(objProvider, objId);

        CRCCOrganizationDto organizationDto_opt = apiGetUserMainPosition(providerId, operatorId);
        CRCCOrganizationDto organizationDto_obj = objType == 0 ? apiGetUserMainPosition(objProvider, objId) : apiGetPosition(objProvider, objId);
        CRCCOrganizationDto organizationDto_node = apiGetOrganization(nodeProvider, nodeId, nodeType);
        CRCCOrganizationDto organizationDto_node_parent = apiGetOrganizationParent(nodeProvider, nodeId, nodeType);

        if (organizationDto_opt != null && organizationDto_obj != null && organizationDto_node != null) {
            String path_opt = apiGetOrganizationPathStr(providerId, organizationDto_opt.getId(), 1, true);
            String path_obj = apiGetOrganizationPathStr(objProvider, organizationDto_obj.getId(),1, true);
            String path_node = apiGetOrganizationPathStr(nodeProvider, organizationDto_node.getId(),1, true);
            String path_node_parent = CRCCConstants.NODE_NAME;
            if(organizationDto_node_parent != null) {
                path_node_parent = apiGetOrganizationPathStr(nodeProvider, organizationDto_node_parent.getId(),1, true);
            }

            CRCCUserInfoDto userInfoDto_opt = apiGetUser(providerId, operatorId, false, false);
            CRCCUserInfoDto userInfoDto_obj = objType == 0 ? apiGetUser(objProvider, objId, false, false) : null;

            LogEntity logEntity = new LogEntity();

            if (authEntity == null) {
                authEntity = new AuthEntity();
                authEntity.setProviderId(providerId);
                authEntity.setOperatorId(operatorId);

                authEntity.setObjProvider(objProvider);
                authEntity.setObjId(objId);
                authEntity.setObjType(objType);
                authEntity.setObjNode(organizationDto_obj.getId());
                authEntity.setObjName(objType == 0 && userInfoDto_obj != null ? userInfoDto_obj.getName() : organizationDto_obj.getName());
                authEntity.setObjPostId(organizationDto_obj.getId());
                authEntity.setObjPostName(organizationDto_obj.getName());
                authEntity.setObjPostCode(organizationDto_obj.getCode());
                authEntity.setObjPath(path_obj);

                authEntity.setObjIndex(-1);
                if(userInfoDto_obj != null) {
                    Integer orgIndex = CRCCOrganizationDto.findOrganizationIndexByUserId(crccOrganizationDto, userInfoDto_obj.getProvider(), userInfoDto_obj.getId());
                    if (orgIndex != null && objType == 0) {
                        authEntity.setObjIndex(orgIndex);
                    }
                }

                CRCCOrganizationDto orgObj = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_obj.getProvider(), organizationDto_obj.getId());
                if(orgObj != null && objType == 1) {
                    authEntity.setObjIndex(orgObj.getIndex());
                }

                authEntity.setNodeProvider(nodeProvider);
                authEntity.setNodeId(nodeId);
                authEntity.setNodeCode(organizationDto_node.getCode());
                authEntity.setNodeType(organizationDto_node.getType());
                authEntity.setNodePath(path_node);

                logEntity.setPostProvider(organizationDto_obj.getProvider());
                logEntity.setPostId(organizationDto_obj.getId());
                logEntity.setPostCode(organizationDto_obj.getCode());
                logEntity.setPostPath(path_obj);

                CRCCOrganizationDto orgNode = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());
                if(orgNode == null) {
                    apiInsertOrganization(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());

                    orgNode = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());
                }
                if(orgNode != null) {
                    authEntity.setNodeIndex(orgNode.getIndex());
                } else {
                    authEntity.setNodeIndex(-1);
                }

                if(organizationDto_node_parent != null) {
                    authEntity.setNodeParentProvider(organizationDto_node_parent.getProvider());
                    authEntity.setNodeParentId(organizationDto_node_parent.getId());
                    authEntity.setNodeParentCode(organizationDto_node_parent.getCode());
                    authEntity.setNodeParentType(organizationDto_node_parent.getType());
                    authEntity.setNodeParentPath(path_node_parent);

                    CRCCOrganizationDto orgNodeParent = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node_parent.getProvider(), organizationDto_node_parent.getId());
                    if(orgNodeParent != null) {
                        authEntity.setNodeParentIndex(orgNodeParent.getIndex());
                    }
                } else {
                    authEntity.setNodeParentProvider(CRCCConstants.NODE_PROVIDER);
                    authEntity.setNodeParentId(CRCCConstants.NODE_ID);
                    authEntity.setNodeParentCode(CRCCConstants.NODE_CODE);
                    authEntity.setNodeParentType(CRCCConstants.NODE_TYPE);
                    authEntity.setNodeParentPath(CRCCConstants.NODE_NAME);

                    CRCCOrganizationDto orgNodeParent = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, CRCCConstants.NODE_PROVIDER, CRCCConstants.NODE_ID);
                    if(orgNodeParent != null) {
                        authEntity.setNodeParentIndex(orgNodeParent.getIndex());
                    }
                }

                authEntity.setAuthRole(AuthRole.VIEW);
                authEntity.setAuthFlag(false);
                authEntity.setAuthOrg(1);
                authEntity.setAuthOpAuth(0);
                authEntity.setAuthQueryAuth(0);
                authEntity.setAuthLog(0);
                authEntity.setAuthPostChange(0);
                authEntity.setAuthPostDelete(0);

                authEntity.setCreateTime(LocalDateTime.now());
                //添加一条权限
                authEntityRepository.insertAuthEntity(authEntity);
            } else {
                authEntity.setNodeProvider(nodeProvider);
                authEntity.setNodeId(nodeId);
                authEntity.setNodeCode(organizationDto_node.getCode());
                authEntity.setNodeType(organizationDto_node.getType());
                authEntity.setNodePath(path_node);

                logEntity.setPostProvider(organizationDto_obj.getProvider());
                logEntity.setPostId(organizationDto_obj.getId());
                logEntity.setPostCode(organizationDto_obj.getCode());
                logEntity.setPostPath(path_obj);

                CRCCOrganizationDto orgNode = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());
                if(orgNode == null) {
                    apiInsertOrganization(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());

                    orgNode = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());
                }
                if(orgNode != null) {
                    authEntity.setNodeIndex(orgNode.getIndex());
                } else {
                    authEntity.setNodeIndex(-1);
                }

                if(organizationDto_node_parent != null) {
                    authEntity.setNodeParentProvider(organizationDto_node_parent.getProvider());
                    authEntity.setNodeParentId(organizationDto_node_parent.getId());
                    authEntity.setNodeParentCode(organizationDto_node_parent.getCode());
                    authEntity.setNodeParentType(organizationDto_node_parent.getType());
                    authEntity.setNodeParentPath(path_node_parent);

                    CRCCOrganizationDto orgNodeParent = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node_parent.getProvider(), organizationDto_node_parent.getId());
                    if(orgNodeParent != null) {
                        authEntity.setNodeParentIndex(orgNodeParent.getIndex());
                    }
                } else {
                    authEntity.setNodeParentProvider(CRCCConstants.NODE_PROVIDER);
                    authEntity.setNodeParentId(CRCCConstants.NODE_ID);
                    authEntity.setNodeParentCode(CRCCConstants.NODE_CODE);
                    authEntity.setNodeParentType(CRCCConstants.NODE_TYPE);
                    authEntity.setNodeParentPath(CRCCConstants.NODE_NAME);

                    CRCCOrganizationDto orgNodeParent = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, CRCCConstants.NODE_PROVIDER, CRCCConstants.NODE_ID);
                    if(orgNodeParent != null) {
                        authEntity.setNodeParentIndex(orgNodeParent.getIndex());
                    }
                }

                //添加一条权限
                authEntityRepository.updateAuthEntity(authEntity);
            }

            MenuAuth menuAuth = new MenuAuth("组织人员浏览", authEntity.getAuthOrg());
            StringBuilder content = new StringBuilder(objType == 0 ? "增加用户授权对象：" : "增加岗位授权对象")
                    .append(userInfoDto_obj == null ? organizationDto_obj.getName() : userInfoDto_obj.getName())
                    .append("，对象所属组织：")
                    .append(path_obj)
                    .append("，被授权组织：")
                    .append(organizationDto_node.getName())
                    .append("，被授权功能：")
                    .append(menuAuth.log());

            logEntity.setContent(content.toString());

            logEntity.setUserName( userInfoDto_opt != null ? userInfoDto_opt.getName() : organizationDto_opt.getName());
            logEntity.setUserPath(path_opt);
            logEntity.setCreateTime(LocalDateTime.now());

            //添加一条日志
            logEntityRepository.insertLogEntity(logEntity);
        }

        if(authEntity != null) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), authEntity, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 添加人员授权
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping("add_auth_list")
    public ResultData<List<AuthEntity>> addAuthList(@RequestBody JSONObject params) throws InterruptedException {
        if (params == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        List<AuthEntity> listAuthEntityInsert = new ArrayList<>();
        List<AuthEntity> listAuthEntityUpdate = new ArrayList<>();
        List<LogEntity> listLogEntity = new ArrayList<>();
        List<JSONObject> listParams = new ArrayList<>();
        if(params.containsKey("params")) {
            JSONArray jsons = params.getJSONArray("params");
            if(!jsons.isEmpty()) {
                listParams = jsons.toJavaList(JSONObject.class);
            }
        }

        if(!listParams.isEmpty()) {
            ExecutorService es = Executors.newCachedThreadPool();
            CountDownLatch latch = new CountDownLatch(listParams.size());

            for (JSONObject x : listParams) {
                es.submit(() -> {
                    try {
                        String providerId = x.getString("providerId");
                        Integer operatorId = x.getInteger("operatorId");
                        String objProvider = x.getString("objProvider");
                        Integer objId = x.getInteger("objId");
                        Integer objType = x.getInteger("objType");
                        String nodeProvider = x.getString("nodeProvider");
                        Integer nodeId = x.getInteger("nodeId");
                        Integer nodeType = x.getInteger("nodeType");

                        AuthEntity authEntity = authLoginEntity(objProvider, objId);

                        CRCCOrganizationDto organizationDto_opt = apiGetUserMainPosition(providerId, operatorId);
                        CRCCOrganizationDto organizationDto_obj = objType == 0 ? apiGetUserMainPosition(objProvider, objId) : apiGetPosition(objProvider, objId);
                        CRCCOrganizationDto organizationDto_node = apiGetOrganization(nodeProvider, nodeId, nodeType);
                        CRCCOrganizationDto organizationDto_node_parent = apiGetOrganizationParent(nodeProvider, nodeId, nodeType);

                        if (organizationDto_opt != null && organizationDto_obj != null && organizationDto_node != null) {
                            String path_opt = apiGetOrganizationPathStr(providerId, organizationDto_opt.getId(), 1, true);
                            String path_obj = apiGetOrganizationPathStr(objProvider, organizationDto_obj.getId(), 1, true);
                            String path_node = apiGetOrganizationPathStr(nodeProvider, organizationDto_node.getId(), 1, true);
                            String path_node_parent = CRCCConstants.NODE_NAME;
                            if(organizationDto_node_parent != null) {
                                path_node_parent = apiGetOrganizationPathStr(nodeProvider, organizationDto_node_parent.getId(),1, true);
                            }

                            CRCCUserInfoDto userInfoDto_opt = apiGetUser(providerId, operatorId, false, false);
                            CRCCUserInfoDto userInfoDto_obj = objType == 0 ? apiGetUser(objProvider, objId, false, false) : null;

                            LogEntity logEntity = new LogEntity();
                            if (authEntity == null) {
                                authEntity = new AuthEntity();
                                authEntity.setProviderId(providerId);
                                authEntity.setOperatorId(operatorId);

                                authEntity.setObjProvider(objProvider);
                                authEntity.setObjId(objId);
                                authEntity.setObjType(objType);
                                authEntity.setObjNode(organizationDto_obj.getId());
                                authEntity.setObjName(objType == 0 && userInfoDto_obj != null ? userInfoDto_obj.getName() : organizationDto_obj.getName());
                                authEntity.setObjPostId(organizationDto_obj.getId());
                                authEntity.setObjPostName(organizationDto_obj.getName());
                                authEntity.setObjPostCode(organizationDto_obj.getCode());
                                authEntity.setObjPath(path_obj);

                                authEntity.setObjIndex(-1);
                                if(userInfoDto_obj != null) {
                                    Integer orgIndex = CRCCOrganizationDto.findOrganizationIndexByUserId(crccOrganizationDto, userInfoDto_obj.getProvider(), userInfoDto_obj.getId());
                                    if (orgIndex != null && objType == 0) {
                                        authEntity.setObjIndex(orgIndex);
                                    }
                                }

                                CRCCOrganizationDto orgObj = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_obj.getProvider(), organizationDto_obj.getId());
                                if(orgObj != null && objType == 1) {
                                    authEntity.setObjIndex(orgObj.getIndex());
                                }

                                authEntity.setNodeProvider(nodeProvider);
                                authEntity.setNodeId(nodeId);
                                authEntity.setNodeCode(organizationDto_node.getCode());
                                authEntity.setNodeType(organizationDto_node.getType());
                                authEntity.setNodePath(path_node);

                                logEntity.setPostProvider(organizationDto_obj.getProvider());
                                logEntity.setPostId(organizationDto_obj.getId());
                                logEntity.setPostCode(organizationDto_obj.getCode());
                                logEntity.setPostPath(path_obj);

                                CRCCOrganizationDto orgNode = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());
                                if(orgNode == null) {
                                    apiInsertOrganization(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());

                                    orgNode = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());
                                }
                                if(orgNode != null) {
                                    authEntity.setNodeIndex(orgNode.getIndex());
                                } else {
                                    authEntity.setNodeIndex(-1);
                                }

                                if(organizationDto_node_parent != null) {
                                    authEntity.setNodeParentProvider(organizationDto_node_parent.getProvider());
                                    authEntity.setNodeParentId(organizationDto_node_parent.getId());
                                    authEntity.setNodeParentCode(organizationDto_node_parent.getCode());
                                    authEntity.setNodeParentType(organizationDto_node_parent.getType());
                                    authEntity.setNodeParentPath(path_node_parent);

                                    CRCCOrganizationDto orgNodeParent = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node_parent.getProvider(), organizationDto_node_parent.getId());
                                    if(orgNodeParent != null) {
                                        authEntity.setNodeParentIndex(orgNodeParent.getIndex());
                                    }
                                } else {
                                    authEntity.setNodeParentProvider(CRCCConstants.NODE_PROVIDER);
                                    authEntity.setNodeParentId(CRCCConstants.NODE_ID);
                                    authEntity.setNodeParentCode(CRCCConstants.NODE_CODE);
                                    authEntity.setNodeParentType(CRCCConstants.NODE_TYPE);
                                    authEntity.setNodeParentPath(CRCCConstants.NODE_NAME);

                                    CRCCOrganizationDto orgNodeParent = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, CRCCConstants.NODE_PROVIDER, CRCCConstants.NODE_ID);
                                    if(orgNodeParent != null) {
                                        authEntity.setNodeParentIndex(orgNodeParent.getIndex());
                                    }
                                }

                                authEntity.setAuthRole(AuthRole.VIEW);
                                authEntity.setAuthFlag(false);
                                authEntity.setAuthOrg(1);
                                authEntity.setAuthOpAuth(0);
                                authEntity.setAuthQueryAuth(0);
                                authEntity.setAuthLog(0);
                                authEntity.setAuthPostChange(0);
                                authEntity.setAuthPostDelete(0);

                                authEntity.setCreateTime(LocalDateTime.now());

                                listAuthEntityInsert.add(authEntity);
                            } else {
                                authEntity.setObjPostCode(organizationDto_obj.getCode());

                                authEntity.setNodeProvider(nodeProvider);
                                authEntity.setNodeId(nodeId);
                                authEntity.setNodeCode(organizationDto_node.getCode());
                                authEntity.setNodeType(organizationDto_node.getType());
                                authEntity.setNodePath(path_node);

                                logEntity.setPostProvider(organizationDto_obj.getProvider());
                                logEntity.setPostId(organizationDto_obj.getId());
                                logEntity.setPostCode(organizationDto_obj.getCode());
                                logEntity.setPostPath(path_obj);

                                CRCCOrganizationDto orgNode = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());
                                if(orgNode == null) {
                                    apiInsertOrganization(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());

                                    orgNode = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node.getProvider(), organizationDto_node.getId());
                                }
                                if(orgNode != null) {
                                    authEntity.setNodeIndex(orgNode.getIndex());
                                } else {
                                    authEntity.setNodeIndex(-1);
                                }

                                if(organizationDto_node_parent != null) {
                                    authEntity.setNodeParentProvider(organizationDto_node_parent.getProvider());
                                    authEntity.setNodeParentId(organizationDto_node_parent.getId());
                                    authEntity.setNodeParentCode(organizationDto_node_parent.getCode());
                                    authEntity.setNodeParentType(organizationDto_node_parent.getType());
                                    authEntity.setNodeParentPath(path_node_parent);

                                    CRCCOrganizationDto orgNodeParent = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, organizationDto_node_parent.getProvider(), organizationDto_node_parent.getId());
                                    if(orgNodeParent != null) {
                                        authEntity.setNodeParentIndex(orgNodeParent.getIndex());
                                    }
                                } else {
                                    authEntity.setNodeParentProvider(CRCCConstants.NODE_PROVIDER);
                                    authEntity.setNodeParentId(CRCCConstants.NODE_ID);
                                    authEntity.setNodeParentCode(CRCCConstants.NODE_CODE);
                                    authEntity.setNodeParentType(CRCCConstants.NODE_TYPE);
                                    authEntity.setNodeParentPath(CRCCConstants.NODE_NAME);

                                    CRCCOrganizationDto orgNodeParent = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, CRCCConstants.NODE_PROVIDER, CRCCConstants.NODE_ID);
                                    if(orgNodeParent != null) {
                                        authEntity.setNodeParentIndex(orgNodeParent.getIndex());
                                    }
                                }

                                listAuthEntityUpdate.add(authEntity);
                            }

                            MenuAuth menuAuth = new MenuAuth("组织人员浏览", authEntity.getAuthOrg());
                            StringBuilder content = new StringBuilder(objType == 0 ? "增加用户授权对象：" : "增加岗位授权对象：")
                                    .append(userInfoDto_obj == null ? organizationDto_obj.getName() : userInfoDto_obj.getName())
                                    .append("，对象所属组织：")
                                    .append(path_obj)
                                    .append("，被授权组织：")
                                    .append(path_node)
                                    .append("，被授权功能：")
                                    .append(menuAuth.log());

                            logEntity.setContent(content.toString());

                            logEntity.setUserName(userInfoDto_opt != null ? userInfoDto_opt.getName() : organizationDto_opt.getName());
                            logEntity.setUserPath(path_opt);
                            logEntity.setCreateTime(LocalDateTime.now());

                            listLogEntity.add(logEntity);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();
            es.shutdown();

            List<AuthEntity> listAuthEntity = new ArrayList<>();
            listAuthEntity.addAll(listAuthEntityInsert);
            listAuthEntity.addAll(listAuthEntityUpdate);
            if(!listAuthEntity.isEmpty()) {
                //添加一条权限
                authEntityRepository.insertAuthEntityList(listAuthEntityInsert);
                //更新一条权限
                authEntityRepository.updateAuthEntityList(listAuthEntityUpdate);
                //添加一条日志
                logEntityRepository.insertLogEntityList(listLogEntity);

                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), listAuthEntity, listAuthEntity.size());
            }
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 删除授权
     *
     * @param providerId
     * @param operatorId
     * @param objProvider
     * @param objId
     * @return
     * @throws Exception
     */
    @GetMapping("delete_auth")
    public ResultData<AuthEntity> deleteAuth(String providerId, Integer operatorId, String objProvider, Integer objId) {
        if (providerId == null || providerId.length() == 0 || operatorId == null || objProvider == null || objProvider.length() == 0 || objId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        AuthEntity authEntity = authLoginEntity(objProvider, objId);

        if (authEntity == null || operatorId.compareTo(authEntity.getObjId()) == 0 || authEntity.getObjId().compareTo(CRCCConstants.ADMIN_ID) == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), authEntity, 0);
        }

        authEntityRepository.deleteAuthEntityByObjId(objProvider, objId);

        StringBuilder content = new StringBuilder(authEntity.getObjType() == 0 ? "删除用户授权对象：" : "删除岗位授权对象：")
                .append(authEntity.getObjName())
                .append("，对象所属组织：")
                .append(authEntity.getObjPath())
                .append("，被授权组织：")
                .append(authEntity.getNodePath());

        LogEntity logEntity = new LogEntity();
        logEntity.setPostProvider(authEntity.getObjProvider());
        logEntity.setPostId(authEntity.getObjPostId());
        logEntity.setPostCode(authEntity.getObjPostCode());
        logEntity.setPostPath(authEntity.getObjPath());
        logEntity.setContent(content.toString());

        CRCCUserInfoDto userInfoDto_opt = apiGetUser(providerId, operatorId, false, false);
        CRCCOrganizationDto organizationDto_opt = apiGetUserMainPosition(providerId, operatorId);
        if(organizationDto_opt != null) {
            String path_opt = apiGetOrganizationPathStr(providerId, organizationDto_opt.getId(), 1, true);
            logEntity.setUserPath(path_opt);
        }

        if(userInfoDto_opt != null) {
            logEntity.setUserName(userInfoDto_opt.getName());
        }
        logEntity.setCreateTime(LocalDateTime.now());

        //添加一条日志
        logEntityRepository.insertLogEntity(logEntity);

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null, 0);
    }

    /**
     * 删除授权列表
     *
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping("delete_auth_list")
    public ResultData<List<AuthEntity>> deleteAuthList(@RequestBody JSONObject params) throws InterruptedException {
        if (params == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        List<AuthEntity> listAuthEntity = new ArrayList<>();
        List<LogEntity> listLogEntity = new ArrayList<>();
        List<JSONObject> listParams = new ArrayList<>();
        if(params.containsKey("params")) {
            JSONArray jsons = params.getJSONArray("params");
            if(!jsons.isEmpty()) {
                listParams = jsons.toJavaList(JSONObject.class);
            }
        }

        if(!listParams.isEmpty()) {
            ExecutorService es = Executors.newCachedThreadPool();
            CountDownLatch latch = new CountDownLatch(listParams.size());

            for (JSONObject x : listParams) {
                es.submit(() -> {
                    try {
                        String providerId = x.getString("providerId");
                        Integer operatorId = x.getInteger("operatorId");
                        String objProvider = x.getString("objProvider");
                        Integer objId = x.getInteger("objId");

                        AuthEntity curAuthEntity = authLoginEntity(providerId, operatorId);
                        AuthEntity authEntity = authEntityRepository.getCurrentAuthEntityByObjId(objProvider, objId);

                        if (curAuthEntity != null && authEntity != null && !curAuthEntity.compareTo(authEntity) &&
                                authEntity.getObjId().compareTo(CRCCConstants.ADMIN_ID) != 0) {

                            listAuthEntity.add(authEntity);

                            StringBuilder content = new StringBuilder(authEntity.getObjType() == 0 ? "删除用户授权对象：" : "删除岗位授权对象：")
                                    .append(authEntity.getObjName())
                                    .append("，对象所属组织：")
                                    .append(authEntity.getObjPath())
                                    .append("，被授权组织：")
                                    .append(authEntity.getNodePath());

                            LogEntity logEntity = new LogEntity();
                            logEntity.setPostProvider(authEntity.getObjProvider());
                            logEntity.setPostId(authEntity.getObjPostId());
                            logEntity.setPostCode(authEntity.getObjPostCode());
                            logEntity.setPostPath(authEntity.getObjPath());
                            logEntity.setContent(content.toString());

                            CRCCUserInfoDto userInfoDto_opt = apiGetUser(providerId, operatorId, false, false);
                            CRCCOrganizationDto organizationDto_opt = apiGetUserMainPosition(providerId, operatorId);
                            if (organizationDto_opt != null) {
                                String path_opt = apiGetOrganizationPathStr(providerId, organizationDto_opt.getId(), 1, true);
                                logEntity.setUserPath(path_opt);
                            }

                            if (userInfoDto_opt != null) {
                                logEntity.setUserName(userInfoDto_opt.getName());
                            }
                            logEntity.setCreateTime(LocalDateTime.now());

                            listLogEntity.add(logEntity);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();
            es.shutdown();

            if(!listAuthEntity.isEmpty() && !listLogEntity.isEmpty()) {
                //删除多条权限
                authEntityRepository.deleteAuthEntityList(listAuthEntity);
                //添加多条日志
                logEntityRepository.insertLogEntityList(listLogEntity);

                return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), listAuthEntity, listAuthEntity.size());
            }
        }

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), listAuthEntity, listAuthEntity.size());
    }

    /**
     * 查询菜单权限
     *
     * @param objProvider
     * @param objId
     * @return
     * @throws Exception
     */
    @GetMapping("query_auth")
    public ResultData<AuthEntity> queryAuth(String objProvider, Integer objId) {
        if (objProvider == null || objProvider.length() == 0 || objId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        AuthEntity authEntity = authLoginEntity(objProvider, objId);

        if(authEntity != null && authEntity.getNodeId() != null){
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), authEntity, 1);
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 修改菜单权限
     *
     * @param optProvider
     * @param optId
     * @param objProvider
     * @param objId
     * @param authOrg
     * @param authOpAuth
     * @param authQueryAuth
     * @param authLog
     * @param authPostChange
     * @param authPostDelete
     * @return
     */
    @GetMapping("update_auth")
    public ResultData<AuthEntity> updateAuth(String optProvider, Integer optId, String objProvider, Integer objId, Integer authOrg, Integer authOpAuth, Integer authQueryAuth, Integer authLog, Integer authPostChange, Integer authPostDelete) {
        if (optProvider == null || optProvider.length() == 0 || optId == null || objProvider == null || objProvider.length() == 0 || objId == null || authOrg == null || authOpAuth == null || authQueryAuth == null || authLog == null || authPostChange == null || authPostDelete == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }


        AuthEntity authEntity = authLoginEntity(objProvider, objId);

        if (authEntity == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        authEntity.setAuthOrg(authOrg);
        authEntity.setAuthOpAuth(authOpAuth);
        authEntity.setAuthQueryAuth(authQueryAuth);
        authEntity.setAuthLog(authLog);
        authEntity.setAuthPostChange(authPostChange);
        authEntity.setAuthPostDelete(authPostDelete);
        authEntity.setCreateTime(LocalDateTime.now());

        //添加一条权限
        authEntityRepository.updateAuthEntity(authEntity);

        // 更新权限日志
        LogEntity logEntity = new LogEntity();

        logEntity.setPostProvider(authEntity.getObjProvider());
        logEntity.setPostId(authEntity.getObjPostId());
        logEntity.setPostCode(authEntity.getObjPostCode());
        logEntity.setPostPath(authEntity.getObjPath());

        MenuAuth[] list = new MenuAuth[6];
        list[0] = new MenuAuth("组织人员浏览", authEntity.getAuthOrg());
        list[1] = new MenuAuth("授权管理", authEntity.getAuthOpAuth());
        list[2] = new MenuAuth("授权查询", authEntity.getAuthQueryAuth());
        list[3] = new MenuAuth("系统日志", authEntity.getAuthLog());
        list[4] = new MenuAuth("岗位变化查询", authEntity.getAuthPostChange());
        list[5] = new MenuAuth("组织删除查询", authEntity.getAuthPostDelete());

        StringBuilder content = new StringBuilder(authEntity.getObjType() == 0 ? "更新用户授权对象：" : "更新岗位授权对象：")
                .append(authEntity.getObjName())
                .append("，对象所属组织：")
                .append(authEntity.getObjPath())
                .append("，被授权组织：")
                .append(authEntity.getNodePath())
                .append("，被授权功能：")
                .append(list[0].log())
                .append(list[1].log())
                .append(list[2].log())
                .append(list[3].log())
                .append(list[4].log())
                .append(list[5].log());

        logEntity.setContent(content.toString());

        CRCCOrganizationDto organizationDto_opt = apiGetUserMainPosition(optProvider, optId);
        if(organizationDto_opt != null) {
            String path_opt = apiGetOrganizationPathStr(optProvider, organizationDto_opt.getId(), 1, true);
            logEntity.setUserPath(path_opt);
        }

        CRCCUserInfoDto userInfoDto_opt = apiGetUser(optProvider, optId, false, false);
        if(userInfoDto_opt != null) {
            logEntity.setUserName(userInfoDto_opt.getName());
        }

        logEntity.setCreateTime(LocalDateTime.now());
        logEntityRepository.insertLogEntity(logEntity);

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), authEntity, 1);
    }

    /**
     * 登录人员鉴权组织删除和授权岗位变化
     *
     * @param optProvider
     * @param optId
     * @param await
     * @return
     * @throws Exception
     */
    @GetMapping("auth_login")
    public ResultData<Boolean> authLogin(String optProvider, Integer optId, Boolean await) throws Exception {
        Boolean is = asyncAuthLogin(optProvider, optId, authEntityRepository, postChangeLogEntityRepository, orgLogEntityRepository, await);
        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), is, 1);
    }

    /**
     * 登录人员鉴权岗位变化
     *
     * @param optProvider
     * @param optId
     * @param await
     * @return
     * @throws Exception
     */
    @GetMapping("auth_post_change")
    public ResultData<Boolean> authPostChange(String optProvider, Integer optId, Boolean await) throws Exception {
        Boolean is = asyncAuthLoginPostChange(optProvider, optId, authEntityRepository, postChangeLogEntityRepository, orgLogEntityRepository, await);
        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), is, 1);
    }

    /**
     * 登录人员鉴权组织删除
     *
     * @param optProvider
     * @param optId
     * @param await
     * @return
     * @throws Exception
     */
    @GetMapping("auth_org_delete")
    public ResultData<Boolean> authOrgDelete(String optProvider, Integer optId, Boolean await) throws Exception {
        Boolean is = asyncAuthLoginOrgDelete(optProvider, optId, authEntityRepository, postChangeLogEntityRepository, orgLogEntityRepository, await);
        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), is, 1);
    }

    /**
     * 登录人员鉴权组织更新
     *
     * @param optProvider
     * @param optId
     * @param await
     * @return
     * @throws Exception
     */
    @GetMapping("auth_org_update")
    public ResultData<Boolean> authOrgUpdate(String optProvider, Integer optId, Boolean await) throws Exception {
        Boolean is = asyncAuthLoginOrgUpdate(optProvider, optId, authEntityRepository, await);
        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), is, 1);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////   Log接口   ///////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 获取所有岗位变化日志列表
     *
     * @return
     */
    @GetMapping("get_all_post_log")
    public ResultData<List<PostChangeLogEntity>> getAllPostLog(){
        List<PostChangeLogEntity> list = postChangeLogEntityRepository.getAllPostChangeLogEntity();

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 模糊查询岗位变化日志列表
     * @param str
     * @return
     */
    @GetMapping("query_post_log")
    public ResultData<List<PostChangeLogEntity>> queryPostLog(String str){
        if (str == null || str.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        List<PostChangeLogEntity> list = postChangeLogEntityRepository.queryPostChangeLogEntity(str);

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 通过机构节点id查询岗位变化日志列表
     * @param nodeProvider
     * @param nodeId
     * @return
     */
    @GetMapping("query_post_by_node_id")
    public ResultData<List<PostChangeLogEntity>> queryPostByNodeId(String nodeProvider, Integer nodeId){
        if (nodeProvider == null || nodeProvider.length() == 0 || nodeId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        List<PostChangeLogEntity> list = postChangeLogEntityRepository.queryPostChangeLogEntityByNodeId(nodeProvider, nodeId);

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 通过机构节点code查询岗位变化日志列表
     * @param nodeProvider
     * @param nodeCode
     * @return
     */
    @GetMapping("query_post_by_node_code")
    public ResultData<List<PostChangeLogEntity>> queryPostByNodeCode(String nodeProvider, String nodeCode){
        if (nodeProvider == null || nodeProvider.length() == 0 || nodeCode == null || nodeCode.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        List<PostChangeLogEntity>list;
        if(nodeProvider.compareTo(CRCCConstants.NODE_PROVIDER) == 0 && nodeCode.compareTo(CRCCConstants.NODE_CODE) == 0) {
            list = postChangeLogEntityRepository.getAllPostChangeLogEntity();
        } else {
            list = postChangeLogEntityRepository.queryPostChangeLogEntityByNodeCode(nodeProvider, nodeCode);
        }

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取所有组织删除日志列表
     * @return
     */
    @GetMapping("get_all_org_log")
    public ResultData<List<OrgLogEntity>> getAllOrgLog(){
        List<OrgLogEntity> list = orgLogEntityRepository.getAllOrgLogEntity();

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 模糊查询组织删除日志列表
     * @param str
     * @return
     */
    @GetMapping("query_org_Log")
    public ResultData<List<OrgLogEntity>> queryOrgLog(String str){
        if (str == null || str.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        List<OrgLogEntity> list = orgLogEntityRepository.queryOrgLogEntity(str);

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 通过机构节点id查询组织删除列表
     * @param nodeProvider
     * @param nodeId
     * @return
     */
    @GetMapping("query_org_by_node_id")
    public ResultData<List<OrgLogEntity>> queryOrgByNodeId(String nodeProvider, Integer nodeId){
        if (nodeProvider == null || nodeProvider.length() == 0 || nodeId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        List<OrgLogEntity> list = orgLogEntityRepository.queryOrgLogEntityByNodeId(nodeProvider, nodeId);

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 通过机构节点code查询组织删除列表
     * @param nodeProvider
     * @param nodeCode
     * @return
     */
    @GetMapping("query_org_by_node_code")
    public ResultData<List<OrgLogEntity>> queryOrgByNodeCode(String nodeProvider, String nodeCode){
        if (nodeProvider == null || nodeProvider.length() == 0 || nodeCode == null || nodeCode.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        List<OrgLogEntity>list;
        if(nodeProvider.compareTo(CRCCConstants.NODE_PROVIDER) == 0 && nodeCode.compareTo(CRCCConstants.NODE_CODE) == 0) {
            list = orgLogEntityRepository.getAllOrgLogEntity();
        } else {
            list = orgLogEntityRepository.queryOrgLogEntityByNodeCode(nodeProvider, nodeCode);
        }

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取所欲系统日志列表
     * @return
     */
    @GetMapping("get_all_log")
    public ResultData<List<LogEntity>> getAllLog(){
        List<LogEntity> list = logEntityRepository.getAllLogEntity();

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 模糊查询系统日志列表
     * @param str
     * @return
     */
    @GetMapping("query_Log")
    public ResultData<List<LogEntity>> queryLog(String str){
        if (str == null || str.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        List<LogEntity> list = logEntityRepository.queryLogEntity(str);

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 通过机构节点id查询日志列表
     * @param nodeProvider
     * @param nodeId
     * @return
     */
    @GetMapping("query_log_by_node_id")
    public ResultData<List<LogEntity>> queryLogByNodeId(String nodeProvider, Integer nodeId){
        if (nodeProvider == null || nodeProvider.length() == 0 || nodeId == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        List<LogEntity> list = logEntityRepository.queryLogEntityByNodeId(nodeProvider, nodeId);

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 通过机构节点code查询日志列表
     * @param nodeProvider
     * @param nodeCode
     * @return
     */
    @GetMapping("query_log_by_node_code")
    public ResultData<List<LogEntity>> queryLogByNodeCode(String nodeProvider, String nodeCode){
        if (nodeProvider == null || nodeProvider.length() == 0 || nodeCode == null || nodeCode.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
        }

        List<LogEntity>list;
        if(nodeProvider.compareTo(CRCCConstants.NODE_PROVIDER) == 0 && nodeCode.compareTo(CRCCConstants.NODE_CODE) == 0) {
            list = logEntityRepository.getAllLogEntity();
        } else {
            list = logEntityRepository.queryLogEntityByNodeCode(nodeProvider, nodeCode);
        }

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////   Token   ////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 初始化网络计划参数
     *
     * @param params
     * @return
     */
    @PostMapping("plan_params")
    public ResultData<Boolean> planParams(@RequestBody JSONObject params) {
        if (params == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), false, 0);
        }

        NetPlanItem[] items = new NetPlanItem[283];
        items[0] = new NetPlanItem(1,0,1,"升杭大桥-动态计划","[]",128.0);
        items[1] = new NetPlanItem(2,1,2,"地基及基础","[]",47.0);
        items[2] = new NetPlanItem(3,2,3,"钻孔桩","[]",44.0);
        items[3] = new NetPlanItem(4,3,4,"0#台","[]",20.0);
        items[4] = new NetPlanItem(5,4,5,"0-1#桩","[131]",1.0);
        items[5] = new NetPlanItem(6,4,5,"0-2#桩","[5]",1.0);
        items[6] = new NetPlanItem(7,4,5,"0-3#桩","[6]",1.0);
        items[7] = new NetPlanItem(8,4,5,"0-4#桩","[7]",1.0);
        items[8] = new NetPlanItem(9,4,5,"0-5#桩","[8]",1.0);
        items[9] = new NetPlanItem(10,4,5,"0-6#桩","[9]",1.0);
        items[10] = new NetPlanItem(11,4,5,"0-7#桩","[10]",1.0);
        items[11] = new NetPlanItem(12,4,5,"0-8#桩","[11]",1.0);
        items[12] = new NetPlanItem(13,4,5,"0-9#桩","[41]",1.0);
        items[13] = new NetPlanItem(14,4,5,"0-10#桩","[13]",1.0);
        items[14] = new NetPlanItem(15,4,5,"0-11#桩","[14]",1.0);
        items[15] = new NetPlanItem(16,4,5,"0-12#桩","[15]",1.0);
        items[16] = new NetPlanItem(17,4,5,"0-13#桩","[16]",1.0);
        items[17] = new NetPlanItem(18,4,5,"0-14#桩","[17]",1.0);
        items[18] = new NetPlanItem(19,4,5,"0-15#桩","[18]",1.0);
        items[19] = new NetPlanItem(20,4,5,"0-16#桩","[19]",1.0);
        items[20] = new NetPlanItem(21,4,5,"0-17#桩","[54]",1.0);
        items[21] = new NetPlanItem(22,4,5,"0-18#桩","[21]",1.0);
        items[22] = new NetPlanItem(23,4,5,"0-19#桩","[22]",1.0);
        items[23] = new NetPlanItem(24,4,5,"0-20#桩","[23]",1.0);
        items[24] = new NetPlanItem(25,4,5,"0-21#桩","[24]",1.0);
        items[25] = new NetPlanItem(26,4,5,"0-22#桩","[25]",1.0);
        items[26] = new NetPlanItem(27,4,5,"0-23#桩","[26]",1.0);
        items[27] = new NetPlanItem(28,4,5,"0-24#桩","[27]",1.0);
        items[28] = new NetPlanItem(29,3,4,"1#墩","[81]",12.0);
        items[29] = new NetPlanItem(30,29,5,"1-1#桩","[93]",1.0);
        items[30] = new NetPlanItem(31,29,5,"1-2#桩","[30]",1.0);
        items[31] = new NetPlanItem(32,29,5,"1-3#桩","[31]",1.0);
        items[32] = new NetPlanItem(33,29,5,"1-4#桩","[32]",1.0);
        items[33] = new NetPlanItem(34,29,5,"1-5#桩","[33]",1.0);
        items[34] = new NetPlanItem(35,29,5,"1-6#桩","[34]",1.0);
        items[35] = new NetPlanItem(36,29,5,"1-7#桩","[35]",1.0);
        items[36] = new NetPlanItem(37,29,5,"1-8#桩","[36]",1.0);
        items[37] = new NetPlanItem(38,29,5,"1-9#桩","[37]",1.0);
        items[38] = new NetPlanItem(39,29,5,"1-10#桩","[38]",1.0);
        items[39] = new NetPlanItem(40,29,5,"1-11#桩","[39]",1.0);
        items[40] = new NetPlanItem(41,29,5,"1-12#桩","[40]",1.0);
        items[41] = new NetPlanItem(42,3,4,"2#墩","[55]",12.0);
        items[42] = new NetPlanItem(43,42,5,"2-1#桩","[67]",1.0);
        items[43] = new NetPlanItem(44,42,5,"2-2#桩","[43]",1.0);
        items[44] = new NetPlanItem(45,42,5,"2-3#桩","[44]",1.0);
        items[45] = new NetPlanItem(46,42,5,"2-4#桩","[45]",1.0);
        items[46] = new NetPlanItem(47,42,5,"2-5#桩","[46]",1.0);
        items[47] = new NetPlanItem(48,42,5,"2-6#桩","[47]",1.0);
        items[48] = new NetPlanItem(49,42,5,"2-7#桩","[48]",1.0);
        items[49] = new NetPlanItem(50,42,5,"2-8#桩","[49]",1.0);
        items[50] = new NetPlanItem(51,42,5,"2-9#桩","[50]",1.0);
        items[51] = new NetPlanItem(52,42,5,"2-10#桩","[51]",1.0);
        items[52] = new NetPlanItem(53,42,5,"2-11#桩","[52]",1.0);
        items[53] = new NetPlanItem(54,42,5,"2-12#桩","[53]",1.0);
        items[54] = new NetPlanItem(55,3,4,"3#墩","[68]",12.0);
        items[55] = new NetPlanItem(56,55,5,"3-1#桩","[80]",1.0);
        items[56] = new NetPlanItem(57,55,5,"3-2#桩","[56]",1.0);
        items[57] = new NetPlanItem(58,55,5,"3-3#桩","[57]",1.0);
        items[58] = new NetPlanItem(59,55,5,"3-4#桩","[58]",1.0);
        items[59] = new NetPlanItem(60,55,5,"3-5#桩","[59]",1.0);
        items[60] = new NetPlanItem(61,55,5,"3-6#桩","[60]",1.0);
        items[61] = new NetPlanItem(62,55,5,"3-7#桩","[61]",1.0);
        items[62] = new NetPlanItem(63,55,5,"3-8#桩","[62]",1.0);
        items[63] = new NetPlanItem(64,55,5,"3-9#桩","[63]",1.0);
        items[64] = new NetPlanItem(65,55,5,"3-10#桩","[64]",1.0);
        items[65] = new NetPlanItem(66,55,5,"3-11#桩","[65]",1.0);
        items[66] = new NetPlanItem(67,55,5,"3-12#桩","[66]",1.0);
        items[67] = new NetPlanItem(68,3,4,"4#墩","[]",12.0);
        items[68] = new NetPlanItem(69,68,5,"4-1#桩","[]",1.0);
        items[69] = new NetPlanItem(70,68,5,"4-2#桩","[69]",1.0);
        items[70] = new NetPlanItem(71,68,5,"4-3#桩","[70]",1.0);
        items[71] = new NetPlanItem(72,68,5,"4-4#桩","[71]",1.0);
        items[72] = new NetPlanItem(73,68,5,"4-5#桩","[72]",1.0);
        items[73] = new NetPlanItem(74,68,5,"4-6#桩","[73]",1.0);
        items[74] = new NetPlanItem(75,68,5,"4-7#桩","[74]",1.0);
        items[75] = new NetPlanItem(76,68,5,"4-8#桩","[75]",1.0);
        items[76] = new NetPlanItem(77,68,5,"4-9#桩","[76]",1.0);
        items[77] = new NetPlanItem(78,68,5,"4-10#桩","[77]",1.0);
        items[78] = new NetPlanItem(79,68,5,"4-11#桩","[78]",1.0);
        items[79] = new NetPlanItem(80,68,5,"4-12#桩","[79]",1.0);
        items[80] = new NetPlanItem(81,3,4,"5#墩","[94]",12.0);
        items[81] = new NetPlanItem(82,81,5,"5-1#桩","[106]",1.0);
        items[82] = new NetPlanItem(83,81,5,"5-2#桩","[82]",1.0);
        items[83] = new NetPlanItem(84,81,5,"5-3#桩","[83]",1.0);
        items[84] = new NetPlanItem(85,81,5,"5-4#桩","[84]",1.0);
        items[85] = new NetPlanItem(86,81,5,"5-5#桩","[85]",1.0);
        items[86] = new NetPlanItem(87,81,5,"5-6#桩","[86]",1.0);
        items[87] = new NetPlanItem(88,81,5,"5-7#桩","[87]",1.0);
        items[88] = new NetPlanItem(89,81,5,"5-8#桩","[88]",1.0);
        items[89] = new NetPlanItem(90,81,5,"5-9#桩","[89]",1.0);
        items[90] = new NetPlanItem(91,81,5,"5-10#桩","[90]",1.0);
        items[91] = new NetPlanItem(92,81,5,"5-11#桩","[91]",1.0);
        items[92] = new NetPlanItem(93,81,5,"5-12#桩","[92]",1.0);
        items[93] = new NetPlanItem(94,3,4,"6#墩","[]",12.0);
        items[94] = new NetPlanItem(95,94,5,"6-1#桩","[]",1.0);
        items[95] = new NetPlanItem(96,94,5,"6-2#桩","[95]",1.0);
        items[96] = new NetPlanItem(97,94,5,"6-3#桩","[96]",1.0);
        items[97] = new NetPlanItem(98,94,5,"6-4#桩","[97]",1.0);
        items[98] = new NetPlanItem(99,94,5,"6-5#桩","[98]",1.0);
        items[99] = new NetPlanItem(100,94,5,"6-6#桩","[99]",1.0);
        items[100] = new NetPlanItem(101,94,5,"6-7#桩","[100]",1.0);
        items[101] = new NetPlanItem(102,94,5,"6-8#桩","[101]",1.0);
        items[102] = new NetPlanItem(103,94,5,"6-9#桩","[102]",1.0);
        items[103] = new NetPlanItem(104,94,5,"6-10#桩","[103]",1.0);
        items[104] = new NetPlanItem(105,94,5,"6-11#桩","[104]",1.0);
        items[105] = new NetPlanItem(106,94,5,"6-12#桩","[105]",1.0);
        items[106] = new NetPlanItem(107,3,4,"7#台","[]",24.0);
        items[107] = new NetPlanItem(108,107,5,"7-1#桩","[]",1.0);
        items[108] = new NetPlanItem(109,107,5,"7-2#桩","[108]",1.0);
        items[109] = new NetPlanItem(110,107,5,"7-3#桩","[109]",1.0);
        items[110] = new NetPlanItem(111,107,5,"7-4#桩","[110]",1.0);
        items[111] = new NetPlanItem(112,107,5,"7-5#桩","[111]",1.0);
        items[112] = new NetPlanItem(113,107,5,"7-6#桩","[112]",1.0);
        items[113] = new NetPlanItem(114,107,5,"7-7#桩","[113]",1.0);
        items[114] = new NetPlanItem(115,107,5,"7-8#桩","[114]",1.0);
        items[115] = new NetPlanItem(116,107,5,"7-9#桩","[115]",1.0);
        items[116] = new NetPlanItem(117,107,5,"7-10#桩","[116]",1.0);
        items[117] = new NetPlanItem(118,107,5,"7-11#桩","[117]",1.0);
        items[118] = new NetPlanItem(119,107,5,"7-12#桩","[118]",1.0);
        items[119] = new NetPlanItem(120,107,5,"7-13#桩","[119]",1.0);
        items[120] = new NetPlanItem(121,107,5,"7-14#桩","[120]",1.0);
        items[121] = new NetPlanItem(122,107,5,"7-15#桩","[121]",1.0);
        items[122] = new NetPlanItem(123,107,5,"7-16#桩","[122]",1.0);
        items[123] = new NetPlanItem(124,107,5,"7-17#桩","[123]",1.0);
        items[124] = new NetPlanItem(125,107,5,"7-18#桩","[124]",1.0);
        items[125] = new NetPlanItem(126,107,5,"7-19#桩","[125]",1.0);
        items[126] = new NetPlanItem(127,107,5,"7-20#桩","[126]",1.0);
        items[127] = new NetPlanItem(128,107,5,"7-21#桩","[127]",1.0);
        items[128] = new NetPlanItem(129,107,5,"7-22#桩","[128]",1.0);
        items[129] = new NetPlanItem(130,107,5,"7-23#桩","[129]",1.0);
        items[130] = new NetPlanItem(131,107,5,"7-24#桩","[130]",1.0);
        items[131] = new NetPlanItem(132,2,3,"承台","[]",35.0);
        items[132] = new NetPlanItem(133,132,4,"0#台","[4]",3.0);
        items[133] = new NetPlanItem(134,133,5,"0#台承台","[12;20;28]",3.0);
        items[134] = new NetPlanItem(135,132,4,"1#墩","[29]",3.0);
        items[135] = new NetPlanItem(136,135,5,"1#墩承台","[41]",3.0);
        items[136] = new NetPlanItem(137,132,4,"2#墩","[42]",3.0);
        items[137] = new NetPlanItem(138,137,5,"2#墩承台","[54]",3.0);
        items[138] = new NetPlanItem(139,132,4,"3#墩","[55]",3.0);
        items[139] = new NetPlanItem(140,139,5,"3#墩承台","[67]",3.0);
        items[140] = new NetPlanItem(141,132,4,"4#墩","[68]",3.0);
        items[141] = new NetPlanItem(142,141,5,"4#墩承台","[80]",3.0);
        items[142] = new NetPlanItem(143,132,4,"5#墩","[81]",3.0);
        items[143] = new NetPlanItem(144,143,5,"5#墩承台","[93]",3.0);
        items[144] = new NetPlanItem(145,132,4,"6#墩","[94]",3.0);
        items[145] = new NetPlanItem(146,145,5,"6#墩承台","[106]",3.0);
        items[146] = new NetPlanItem(147,132,4,"7#台","[107]",3.0);
        items[147] = new NetPlanItem(148,147,5,"7#台承台","[131]",3.0);
        items[148] = new NetPlanItem(149,1,2,"墩台","[]",47.0);
        items[149] = new NetPlanItem(150,149,3,"墩身","[]",30.0);
        items[150] = new NetPlanItem(151,150,4,"1#墩","[135]",6.0);
        items[151] = new NetPlanItem(152,151,5,"1#墩墩身","[136]",4.0);
        items[152] = new NetPlanItem(153,151,5,"1#墩顶帽","[152]",2.0);
        items[153] = new NetPlanItem(154,150,4,"2#墩","[137]",6.0);
        items[154] = new NetPlanItem(155,154,5,"2#墩墩身","[138]",4.0);
        items[155] = new NetPlanItem(156,154,5,"2#墩顶帽","[155]",2.0);
        items[156] = new NetPlanItem(157,150,4,"3#墩","[139]",6.0);
        items[157] = new NetPlanItem(158,157,5,"3#墩墩身","[140]",4.0);
        items[158] = new NetPlanItem(159,157,5,"3#墩顶帽","[158]",2.0);
        items[159] = new NetPlanItem(160,150,4,"4#墩","[141]",6.0);
        items[160] = new NetPlanItem(161,160,5,"4#墩墩身","[142]",4.0);
        items[161] = new NetPlanItem(162,160,5,"4#墩顶帽","[161]",2.0);
        items[162] = new NetPlanItem(163,150,4,"5#墩","[143]",6.0);
        items[163] = new NetPlanItem(164,163,5,"5#墩墩身","[144]",4.0);
        items[164] = new NetPlanItem(165,163,5,"5#墩顶帽","[164]",2.0);
        items[165] = new NetPlanItem(166,150,4,"6#墩","[145]",6.0);
        items[166] = new NetPlanItem(167,166,5,"6#墩墩身","[146]",4.0);
        items[167] = new NetPlanItem(168,166,5,"6#墩顶帽","[167]",2.0);
        items[168] = new NetPlanItem(169,149,3,"台身","[]",27.0);
        items[169] = new NetPlanItem(170,169,4,"0#台","[133]",7.0);
        items[170] = new NetPlanItem(171,170,5,"0#台台身","[134]",4.0);
        items[171] = new NetPlanItem(172,170,5,"0#台顶帽","[171]",2.0);
        items[172] = new NetPlanItem(173,170,5,"0#台台顶","[172]",1.0);
        items[173] = new NetPlanItem(174,169,4,"7#台","[147]",7.0);
        items[174] = new NetPlanItem(175,174,5,"7#台台身","[148]",4.0);
        items[175] = new NetPlanItem(176,174,5,"7#台顶帽","[175]",2.0);
        items[176] = new NetPlanItem(177,174,5,"7#台台顶","[176]",1.0);
        items[177] = new NetPlanItem(178,149,3,"支承垫石","[]",8.0);
        items[178] = new NetPlanItem(179,178,4,"0#台","[170]",1.0);
        items[179] = new NetPlanItem(180,179,5,"0#台支承垫石","[173]",1.0);
        items[180] = new NetPlanItem(181,178,4,"1#墩","[179;151]",1.0);
        items[181] = new NetPlanItem(182,181,5,"1#墩支承垫石","[180;153]",1.0);
        items[182] = new NetPlanItem(183,178,4,"2#墩","[181;154]",1.0);
        items[183] = new NetPlanItem(184,183,5,"2#墩支承垫石","[182;156]",1.0);
        items[184] = new NetPlanItem(185,178,4,"3#墩","[183;157]",1.0);
        items[185] = new NetPlanItem(186,185,5,"3#墩支承垫石","[184;159]",1.0);
        items[186] = new NetPlanItem(187,178,4,"4#墩","[185;160]",1.0);
        items[187] = new NetPlanItem(188,187,5,"4#墩支承垫石","[186;162]",1.0);
        items[188] = new NetPlanItem(189,178,4,"5#墩","[187;163]",1.0);
        items[189] = new NetPlanItem(190,189,5,"5#墩支承垫石","[188;165]",1.0);
        items[190] = new NetPlanItem(191,178,4,"6#墩","[189;166]",1.0);
        items[191] = new NetPlanItem(192,191,5,"6#墩支承垫石","[190;168]",1.0);
        items[192] = new NetPlanItem(193,178,4,"7#台","[191;174]",1.0);
        items[193] = new NetPlanItem(194,193,5,"7#台支承垫石","[192;177]",1.0);
        items[194] = new NetPlanItem(195,149,3,"锥体","[]",24.0);
        items[195] = new NetPlanItem(196,195,4,"0#台","[170]",4.0);
        items[196] = new NetPlanItem(197,196,5,"0#台锥体","[173]",4.0);
        items[197] = new NetPlanItem(198,195,4,"7#台","[174]",4.0);
        items[198] = new NetPlanItem(199,198,5,"7#台锥体","[177]",4.0);
        items[199] = new NetPlanItem(200,149,3,"排水设施","[]",24.0);
        items[200] = new NetPlanItem(201,200,4,"0#台","[196]",4.0);
        items[201] = new NetPlanItem(202,201,5,"0#台排水设施","[197]",4.0);
        items[202] = new NetPlanItem(203,200,4,"7#台","[198]",4.0);
        items[203] = new NetPlanItem(204,203,5,"7#台排水设施","[199]",4.0);
        items[204] = new NetPlanItem(205,1,2,"结合梁","[]",31.0);
        items[205] = new NetPlanItem(206,205,3,"钢梁","[]",24.0);
        items[206] = new NetPlanItem(207,206,4,"第1跨","[193;179;181]",5.0);
        items[207] = new NetPlanItem(208,207,5,"第1跨钢梁","[180;182;194]",5.0);
        items[208] = new NetPlanItem(209,206,4,"第2跨","[207;183]",5.0);
        items[209] = new NetPlanItem(210,209,5,"第2跨钢梁","[208;184]",5.0);
        items[210] = new NetPlanItem(211,206,4,"第3跨","[209;185]",3.0);
        items[211] = new NetPlanItem(212,211,5,"第3跨钢梁","[210;186]",3.0);
        items[212] = new NetPlanItem(213,206,4,"第4跨","[211;187]",3.0);
        items[213] = new NetPlanItem(214,213,5,"第4跨钢梁","[212;188]",3.0);
        items[214] = new NetPlanItem(215,206,4,"第5跨","[213;189]",2.0);
        items[215] = new NetPlanItem(216,215,5,"第5跨钢梁","[214;190]",2.0);
        items[216] = new NetPlanItem(217,206,4,"第6跨","[215;191]",2.0);
        items[217] = new NetPlanItem(218,217,5,"第6跨钢梁","[216;192]",2.0);
        items[218] = new NetPlanItem(219,206,4,"第7跨","[217]",4.0);
        items[219] = new NetPlanItem(220,219,5,"第7跨钢梁","[218]",4.0);
        items[220] = new NetPlanItem(221,205,3,"桥面板","[]",7.0);
        items[221] = new NetPlanItem(222,221,4,"第1跨","[219]",1.0);
        items[222] = new NetPlanItem(223,222,5,"第1跨桥面板","[220]",1.0);
        items[223] = new NetPlanItem(224,221,4,"第2跨","[222]",1.0);
        items[224] = new NetPlanItem(225,224,5,"第2跨桥面板","[223]",1.0);
        items[225] = new NetPlanItem(226,221,4,"第3跨","[224]",1.0);
        items[226] = new NetPlanItem(227,226,5,"第3跨桥面板","[225]",1.0);
        items[227] = new NetPlanItem(228,221,4,"第4跨","[226]",1.0);
        items[228] = new NetPlanItem(229,228,5,"第4跨桥面板","[227]",1.0);
        items[229] = new NetPlanItem(230,221,4,"第5跨","[228]",1.0);
        items[230] = new NetPlanItem(231,230,5,"第5跨桥面板","[229]",1.0);
        items[231] = new NetPlanItem(232,221,4,"第6跨","[230]",1.0);
        items[232] = new NetPlanItem(233,232,5,"第6跨桥面板","[231]",1.0);
        items[233] = new NetPlanItem(234,221,4,"第7跨","[232]",1.0);
        items[234] = new NetPlanItem(235,234,5,"第7跨桥面板","[233]",1.0);
        items[235] = new NetPlanItem(236,1,2,"桥梁附属设施","[]",74.0);
        items[236] = new NetPlanItem(237,236,3,"防护墙","[]",21.0);
        items[237] = new NetPlanItem(238,237,4,"第1跨","[234]",3.0);
        items[238] = new NetPlanItem(239,238,5,"第1跨防护墙","[235]",3.0);
        items[239] = new NetPlanItem(240,237,4,"第2跨","[238]",3.0);
        items[240] = new NetPlanItem(241,240,5,"第2跨防护墙","[239]",3.0);
        items[241] = new NetPlanItem(242,237,4,"第3跨","[240]",3.0);
        items[242] = new NetPlanItem(243,242,5,"第3跨防护墙","[241]",3.0);
        items[243] = new NetPlanItem(244,237,4,"第4跨","[242]",3.0);
        items[244] = new NetPlanItem(245,244,5,"第4跨防护墙","[243]",3.0);
        items[245] = new NetPlanItem(246,237,4,"第5跨","[244]",3.0);
        items[246] = new NetPlanItem(247,246,5,"第5跨防护墙","[245]",3.0);
        items[247] = new NetPlanItem(248,237,4,"第6跨","[246]",3.0);
        items[248] = new NetPlanItem(249,248,5,"第6跨防护墙","[247]",3.0);
        items[249] = new NetPlanItem(250,237,4,"第7跨","[248]",3.0);
        items[250] = new NetPlanItem(251,250,5,"第7跨防护墙","[249]",3.0);
        items[251] = new NetPlanItem(252,236,3,"栏杆","[]",14.0);
        items[252] = new NetPlanItem(253,252,4,"第1跨","[250]",2.0);
        items[253] = new NetPlanItem(254,253,5,"第1跨栏杆","[251]",2.0);
        items[254] = new NetPlanItem(255,252,4,"第2跨","[253]",2.0);
        items[255] = new NetPlanItem(256,255,5,"第2跨栏杆","[254]",2.0);
        items[256] = new NetPlanItem(257,252,4,"第3跨","[255]",2.0);
        items[257] = new NetPlanItem(258,257,5,"第3跨栏杆","[256]",2.0);
        items[258] = new NetPlanItem(259,252,4,"第4跨","[257]",2.0);
        items[259] = new NetPlanItem(260,259,5,"第4跨栏杆","[258]",2.0);
        items[260] = new NetPlanItem(261,252,4,"第5跨","[259]",2.0);
        items[261] = new NetPlanItem(262,261,5,"第5跨栏杆","[260]",2.0);
        items[262] = new NetPlanItem(263,252,4,"第6跨","[261]",2.0);
        items[263] = new NetPlanItem(264,263,5,"第6跨栏杆","[262]",2.0);
        items[264] = new NetPlanItem(265,252,4,"第7跨","[263]",2.0);
        items[265] = new NetPlanItem(266,265,5,"第7跨栏杆","[264]",2.0);
        items[266] = new NetPlanItem(267,236,3,"吊围栏","[]",32.0);
        items[267] = new NetPlanItem(268,267,4,"0#台","[170]",4.0);
        items[268] = new NetPlanItem(269,268,5,"0#台吊围栏","[173]",4.0);
        items[269] = new NetPlanItem(270,267,4,"1#墩","[151;268]",4.0);
        items[270] = new NetPlanItem(271,270,5,"1#墩吊围栏","[153;269]",4.0);
        items[271] = new NetPlanItem(272,267,4,"2#墩","[154;270]",4.0);
        items[272] = new NetPlanItem(273,272,5,"2#墩吊围栏","[156;271]",4.0);
        items[273] = new NetPlanItem(274,267,4,"3#墩","[157;272]",4.0);
        items[274] = new NetPlanItem(275,274,5,"3#墩吊围栏","[159;273]",4.0);
        items[275] = new NetPlanItem(276,267,4,"4#墩","[160;274]",4.0);
        items[276] = new NetPlanItem(277,276,5,"4#墩吊围栏","[162;275]",4.0);
        items[277] = new NetPlanItem(278,267,4,"5#墩","[163;276]",4.0);
        items[278] = new NetPlanItem(279,278,5,"5#墩吊围栏","[165;277]",4.0);
        items[279] = new NetPlanItem(280,267,4,"6#墩","[166;278]",4.0);
        items[280] = new NetPlanItem(281,280,5,"6#墩吊围栏","[168;279]",4.0);
        items[281] = new NetPlanItem(282,267,4,"7#台","[174;280]",4.0);
        items[282] = new NetPlanItem(283,282,5,"7#台吊围栏","[177;281]",4.0);

        if(params.containsKey("params")) {
            JSONObject json = params.getJSONObject("params");
            if(!json.isEmpty()) {
                double value = json.getDoubleValue("value");

                String date = json.getString("date");
                String format = json.getString("format");

                int year = json.getInteger("year");
                int month = json.getInteger("month");
                int day = json.getInteger("day");
                int hour = json.getInteger("hour");
                int minute = json.getInteger("minute");
                int second = json.getInteger("second");

                LocalDateTime localDateTime = DateUtils.parseLocalDateTime(date, format);
                double _value = DateUtils.convertLocalDateTimeToExcelDateTime(localDateTime);

                double excelDateTime = DateUtils.convertLocalDateTimeToExcelDateTime(LocalDateTime.of(year,month,day,hour,minute,second));
                dynamicPlan.initItems(items, 283, excelDateTime);
            }
        }

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), true, 1);
    }

    /**
     * 计算静态网络计划
     * @return
     */
    @GetMapping("plan_static")
    public ResultData<Boolean> planStatic(){
        dynamicPlan.calcPlanItems();

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), true, 1);
    }

    /**
     * 计算动态网络计划
     *
     * @param params
     * @return
     */
    @PostMapping("plan_estimate")
    public ResultData<Boolean> planEstimate(@RequestBody JSONObject params){
        if (params == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), false, 0);
        }

        if(params.containsKey("params")) {
            JSONArray jsons = params.getJSONArray("params");
            if(!jsons.isEmpty()) {
                List<JSONObject> listParams = jsons.toJavaList(JSONObject.class);

                if(!listParams.isEmpty()) {
                    int max_level = dynamicPlan.getMax_level();
                    for (JSONObject x : listParams) {
                        if(x.containsKey("id") && x.containsKey("type") && x.containsKey("level")) {
                            int id = x.getInteger("id");
                            int type = x.getInteger("type");
                            int level = x.getInteger("level");

                            if (level == max_level && type == 3 && x.containsKey("value")) {
                                double value = x.getDoubleValue("value");
                                dynamicPlan.calcEstimateItems(id, type, value);
                            } else if (level == max_level && type != 3 && x.containsKey("date") && x.containsKey("format")) {
                                String date = x.getString("date");
                                String format = x.getString("format");

                                LocalDateTime localDateTime = DateUtils.parseLocalDateTime(date, format);
                                double excelDateTime = DateUtils.convertLocalDateTimeToExcelDateTime(localDateTime);

                                dynamicPlan.calcEstimateItems(id, type, excelDateTime);
                            } else if (level == max_level && type != 3 && x.containsKey("year") && x.containsKey("month") && x.containsKey("day") && x.containsKey("hour") && x.containsKey("minute") && x.containsKey("second")) {
                                int year = x.getInteger("year");
                                int month = x.getInteger("month");
                                int day = x.getInteger("day");
                                int hour = x.getInteger("hour");
                                int minute = x.getInteger("minute");
                                int second = x.getInteger("second");

                                double excelDateTime = DateUtils.convertLocalDateTimeToExcelDateTime(LocalDateTime.of(year, month, day, hour, minute, second));

                                dynamicPlan.calcEstimateItems(id, type, excelDateTime);
                            }
                        }
                    }

                    if(params.containsKey("actual")) {
                        boolean actual = params.getBoolean("actual");
                        dynamicPlan.calcStatus(actual);
                    }
                }
            }
        }

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), true, 1);
    }

    /**
     * 计算实际网络计划
     *
     * @param params
     * @return
     */
    @PostMapping("plan_actual")
    public ResultData<Boolean> planActual(@RequestBody JSONObject params){
        if (params == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), false, 0);
        }

        if(params.containsKey("params")) {
            JSONArray jsons = params.getJSONArray("params");
            if(!jsons.isEmpty()) {
                List<JSONObject> listParams = jsons.toJavaList(JSONObject.class);

                if(!listParams.isEmpty()) {
                    int max_level = dynamicPlan.getMax_level();
                    for (JSONObject x : listParams) {
                        if (x.containsKey("id") && x.containsKey("level")) {
                            int id = x.getInteger("id");
                            int level = x.getInteger("level");

                            if (level == max_level && x.containsKey("value")) {
                                double value = x.getDoubleValue("value");

                                dynamicPlan.calcActualItems(id, value);
                            } else if (level == max_level && x.containsKey("date") && x.containsKey("format")) {
                                String date = x.getString("date");
                                String format = x.getString("format");

                                LocalDateTime localDateTime = DateUtils.parseLocalDateTime(date, format);
                                double excelDateTime = DateUtils.convertLocalDateTimeToExcelDateTime(localDateTime);

                                dynamicPlan.calcActualItems(id, excelDateTime);
                            } else if (level == max_level &&  x.containsKey("year") && x.containsKey("month") && x.containsKey("day") && x.containsKey("hour") && x.containsKey("minute") && x.containsKey("second")) {
                                int year = x.getInteger("year");
                                int month = x.getInteger("month");
                                int day = x.getInteger("day");
                                int hour = x.getInteger("hour");
                                int minute = x.getInteger("minute");
                                int second = x.getInteger("second");

                                double excelDateTime = DateUtils.convertLocalDateTimeToExcelDateTime(LocalDateTime.of(year, month, day, hour, minute, second));

                                dynamicPlan.calcActualItems(id, excelDateTime);
                            }
                        }
                    }

                    if(params.containsKey("actual")) {
                        boolean actual = params.getBoolean("actual");
                        dynamicPlan.calcStatus(actual);
                    }
                }
            }
        }

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), true, 1);
    }

    /**
     * 导出网络计划数据
     * @return
     */
    @GetMapping("plan_export")
    public ResultData<Boolean> planExport(String path) throws IOException {
        if (path == null || path.length() == 0) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), false, 0);
        }

        dynamicPlan.exportItems(path);

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), true, 1);
    }

    /**
     * 获取网络计划列表结构
     * @return
     */
    @GetMapping("plan_list")
    public ResultData<List<NetPlanItem>> planList(){
        List<NetPlanItem> list = dynamicPlan.getItemList();

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }

    /**
     * 获取网络计划树结构
     * @return
     */
    @GetMapping("plan_tree")
    public ResultData<List<NetPlanItem>> planTree(){
        List<NetPlanItem> list = dynamicPlan.getItemTree();

        if(list != null && !list.isEmpty()) {
            return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), list, list.size());
        }

        return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), null, 0);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////   Token   ////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Token工具初始化
     *
     * @throws Exception
     */
    public static void tokenHolderInit() {
        RPHolder rpHolder = Application.getApiRPHolder();
        accessTokenDto = TokenHolder.loadAccessToken(rpHolder);

        LocalDateTime localDateTimeNow = LocalDateTime.now();
//        System.out.println("AccessToken Current DateTime: " + DateUtils.formatLocalDateTime(localDateTimeNow, DateUtils.DATETIME_FORMAT.YEAR_SECONDS));
        Long milliSecond = DateUtils.convertLocalDateTimeToTimestamp(localDateTimeNow);
        milliSecond = milliSecond + (3600L * 1000L * 24L);
        invalidTokenDateTime = DateUtils.convertTimestampToLocalDateTime(milliSecond);
//        System.out.println("AccessToken Invalid DateTime: " + DateUtils.formatLocalDateTime(invalidTokenDateTime, DateUtils.DATETIME_FORMAT.YEAR_SECONDS));
    }

    /**
     * 判断Token是否有效
     *
     * @throws Exception
     */
    public static Boolean isTokenInvalid() {
        if(invalidTokenDateTime != null) {
            return DateUtils.afterLocalDateTime(DateUtils.currentLocalDateTime(), invalidTokenDateTime);
        }

        return false;
    }

    /**
     * 初始化组织树
     */
    public static void initOrganization() {
        ExecutorService es = Executors.newCachedThreadPool();
        es.submit(() -> {
            try {
                tokenHolderInit();
                crccOrganizationDto = syncTreeOrganizationAndUsers(VueRetController.CRCCConstants.NODE_PROVIDER, VueRetController.CRCCConstants.NODE_ID, VueRetController.CRCCConstants.NODE_TYPE);
                crccUserInfoDtoList = syncInitUserInfoList(VueRetController.CRCCConstants.NODE_PROVIDER, VueRetController.CRCCConstants.NODE_ID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        es.shutdown();
    }

    /**
     *  获取 API 注册信息
     * @param apiName
     * @return
     */
    public RPHolder apiRPHolder(String apiName) {
        RPHolder rpHolder;
        if(apiName == null || apiName.length() == 0) {
            rpHolder = clientService.loadRPHolder();
        } else {
            rpHolder = clientService.loadApiRPHolder(apiName);
        }

        return rpHolder;
    }

    /**
     * 获取登录人员权限
     *
     * @param providerId
     * @param userId
     * @return
     */
    private AuthEntity authLoginEntity(String providerId, Integer userId) {
        if (providerId == null || providerId.length() == 0 || userId == null) {
            return null;
        }

        if (authEntityRepository != null) {
            AuthEntity authEntity = authEntityRepository.getCurrentAuthEntityByObjId(providerId, userId);
            if(authEntity == null) {
                CRCCOrganizationDto positionDto = apiGetUserMainPosition(providerId, userId);
                if(positionDto != null) {
                    authEntity = authEntityRepository.getCurrentAuthEntityByObjId(providerId, positionDto.getId());
                }
            }

            return authEntity;
        }

        return null;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////   hr接口   ////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 获取用户信息
     *
     * @param providerId
     * @param userId
     * @return
     * @throws Exception
     */
    private static CRCCUserInfoDto apiGetUser(String providerId, Integer userId, Boolean hasOrg, Boolean virtual) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || userId == null || userId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCUserInfoDto user = TokenHolder.getUserApi(accessTokenDto, "/org/"+providerId+"/user/"+userId);
        if(user != null) {
            user.setProvider(providerId);

            if(hasOrg) {
                CRCCOrganizationDto orgPosition = apiGetUserMainPosition(providerId, userId);
                if (orgPosition != null) {
                    String path = apiGetOrganizationPathStr(providerId, orgPosition.getId(), 1, true);
                    user.setPath(path);

                    JSONObject position = new JSONObject();
                    position.put("provider", orgPosition.getProvider());
                    position.put("id", orgPosition.getId());
                    position.put("name", orgPosition.getName());
                    user.setPosition(position);

                    CRCCOrganizationDto orgDepartment = apiGetPositionDepartment(providerId, orgPosition.getId());
                    if (orgDepartment != null) {
                        CRCCOrganizationDto orgCompany = apiGetDepartmentParent(providerId, orgDepartment.getId());

                        JSONObject department = new JSONObject();
                        department.put("provider", orgDepartment.getProvider());
                        department.put("id", orgDepartment.getId());
                        department.put("name", orgDepartment.getName());
                        user.setDepartment(department);

                        while (orgCompany != null) {
                            if (!orgCompany.getVirtual()) {
                                JSONObject company = new JSONObject();
                                company.put("provider", orgCompany.getProvider());
                                company.put("id", orgCompany.getId());
                                company.put("name", orgCompany.getName());
                                user.setCompany(company);
                                break;
                            } else {
                                if(virtual) {
                                    orgCompany = apiGetCompanyParent(providerId, orgCompany.getId());
                                } else {
                                    JSONObject company = new JSONObject();
                                    company.put("provider", orgCompany.getProvider());
                                    company.put("id", orgCompany.getId());
                                    company.put("name", orgCompany.getName());
                                    user.setCompany(company);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return user;
    }
    private static CRCCUserInfoDto apiAsyncGetUser(String providerId, Integer userId, Boolean hasOrg, Boolean virtual) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || userId == null || userId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCUserInfoDto user = TokenHolder.getUserAsyncApi(accessTokenDto, "/org/"+providerId+"/user/"+userId, providerId);
        if(user != null) {
            user.setProvider(providerId);

            if(hasOrg) {
                CRCCOrganizationDto orgPosition = apiGetUserMainPosition(providerId, userId);
                if (orgPosition != null) {
                    String path = apiGetOrganizationPathStr(providerId, orgPosition.getId(), 1, true);
                    user.setPath(path);

                    CRCCOrganizationDto orgDepartment = apiGetPositionDepartment(providerId, orgPosition.getId());
                    if (orgDepartment != null) {
                        CRCCOrganizationDto orgCompany = apiGetDepartmentParent(providerId, orgDepartment.getId());

                        while (orgCompany != null) {
                            if (!orgCompany.getVirtual()) {
                                JSONObject company = new JSONObject();
                                JSONObject department = new JSONObject();
                                JSONObject position = new JSONObject();
                                company.put("provider", orgCompany.getProvider());
                                company.put("id", orgCompany.getId());
                                company.put("name", orgCompany.getName());
                                user.setCompany(company);
                                department.put("provider", orgDepartment.getProvider());
                                department.put("id", orgDepartment.getId());
                                department.put("name", orgDepartment.getName());
                                user.setDepartment(department);
                                position.put("provider", orgPosition.getProvider());
                                position.put("id", orgPosition.getId());
                                position.put("name", orgPosition.getName());
                                user.setPosition(position);
                                break;
                            } else {
                                if(virtual) {
                                    orgCompany = apiGetCompanyParent(providerId, orgCompany.getId());
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return user;
    }
    private static String apiGetUserName(String providerId, Integer userId) {
        CRCCUserInfoDto user = apiGetUser(providerId, userId, false, false);
        if(user != null) {
            return user.getName();
        }

        return null;
    }

    /**
     * 获取用户的主岗位
     *
     * @param providerId
     * @param userId
     * @return
     * @throws Exception
     */
    private static CRCCOrganizationDto apiGetUserMainPosition(String providerId, Integer userId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || userId == null || userId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgApi(accessTokenDto, "/org/"+providerId+"/user/"+userId+"/mainposition");
        if(org != null) {
            org.setProvider(providerId);
            if(org.getType() == 3) {
                org.setVirtual(false);
            }
        }
        return org;
    }
    private static CRCCOrganizationDto apiAsyncGetUserMainPosition(String providerId, Integer userId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || userId == null || userId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgAsyncApi(accessTokenDto, "/org/"+providerId+"/user/"+userId+"/mainposition", providerId);
        if(org != null) {
            org.setProvider(providerId);
            if(org.getType() == 3) {
                org.setVirtual(false);
            }
        }
        return org;
    }
    private static String apiGetUserMainPositionName(String providerId, Integer userId) {
        CRCCOrganizationDto position = apiGetUserMainPosition(providerId, userId);
        if(position != null) {
            return position.getName();
        }

        return null;
    }

    /**
     * 获取用户的所有岗位集合
     *
     * @param providerId
     * @param userId
     * @return
     * @throws Exception
     */
    private static List<CRCCOrganizationDto> apiGetUserPositions(String providerId, Integer userId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || userId == null || userId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        List<CRCCOrganizationDto> orgList = TokenHolder.getOrgListApi(accessTokenDto, "/org/"+providerId+"/user/"+userId+"/positions");
        if(orgList != null) {
            for (CRCCOrganizationDto x : orgList) {
                x.setProvider(providerId);
                if(x.getType() == 3) {
                    x.setVirtual(false);
                }
            }
        }

        return orgList;
    }
    private static List<CRCCOrganizationDto> apiAsyncGetUserPositions(String providerId, Integer userId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || userId == null || userId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        List<CRCCOrganizationDto> orgList = TokenHolder.getOrgListAsyncApi(accessTokenDto, "/org/"+providerId+"/user/"+userId+"/positions", providerId);
        if(orgList != null) {
            for (CRCCOrganizationDto x : orgList) {
                x.setProvider(providerId);
                if(x.getType() == 3) {
                    x.setVirtual(false);
                }
            }
        }

        return orgList;
    }

    /**
     * 搜索用户
     *
     * @param providerId
     * @param orgId
     * @param username
     * @param mainPositionOnly
     * @return
     * @throws Exception
     */
    private static List<JSONObject> apiSearchUser(String providerId, Integer orgId, String username, boolean mainPositionOnly) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || username == null || username.length() == 0 || orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        return TokenHolder.getObjListApi(accessTokenDto, "/org/"+providerId+"/searchuser/"+orgId+"?username="+username+"&mainPositionOnly="+mainPositionOnly);
    }
    private static List<JSONObject> apiAsyncSearchUser(String providerId, Integer orgId, String username, boolean mainPositionOnly) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || username == null || username.length() == 0 || orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        return TokenHolder.getObjListAsyncApi(accessTokenDto, "/org/"+providerId+"/searchuser/"+orgId+"?username="+username+"&mainPositionOnly="+mainPositionOnly);
    }

    /**
     * 获取任一机构信息，通过单位id、部门id、岗位id，用户id
     *
     * @param providerId
     * @param orgId
     * @param orgType 1 单位  2 部门  3 岗位  4 用户
     * @return
     * @throws Exception
     */
    private static CRCCOrganizationDto apiGetOrganization(String providerId, Integer orgId, Integer orgType) {
        if (orgType == null) {
            return null;
        }

        switch (orgType) {
            case 0:
                return apiGetCompanyRoot();
            case 1:
                return apiGetCompany(providerId, orgId);
            case 2:
                return apiGetDepartment(providerId, orgId);
            case 3:
                return apiGetPosition(providerId, orgId);
            case 4: {
                return apiGetUserMainPosition(providerId, orgId);
            }
        }

        return null;
    }
    private static CRCCOrganizationDto apiAsyncGetOrganization(String providerId, Integer orgId, Integer orgType) {
        if (orgType == null) {
            return null;
        }

        switch (orgType) {
            case 0:
                return apiAsyncGetCompanyRoot();
            case 1:
                return apiAsyncGetCompany(providerId, orgId);
            case 2:
                return apiAsyncGetDepartment(providerId, orgId);
            case 3:
                return apiAsyncGetPosition(providerId, orgId);
            case 4: {
                return apiAsyncGetUserMainPosition(providerId, orgId);
            }
        }

        return null;
    }

    /**
     * 获取任一组织机构直接下级机构集合
     *
     * @param providerId
     * @param orgId
     * @param orgType
     * @return
     * @throws Exception
     */
    private static List<CRCCOrganizationDto> apiGetOrganizationChildren(String providerId, Integer orgId, Integer orgType) {
        if (orgType == null) {
            return null;
        }

        List<CRCCOrganizationDto> orgList = new ArrayList<>();
        switch (orgType) {
            case 0:
                CRCCOrganizationDto org = apiGetCompanyRoot();
                if(org != null) {
                    orgList = org.getChildren();
                }
                break;
            case 1:
                orgList = apiGetCompanyChildren(providerId, orgId);
                break;
            case 2:
                orgList = apiGetDepartmentChildren(providerId, orgId);
                break;
            default:
                orgList = null;
                break;
        }

        if(orgList != null && !orgList.isEmpty()) {
            return orgList;
        }

        return null;
    }
    private static List<CRCCOrganizationDto> apiAsyncGetOrganizationChildren(String providerId, Integer orgId, Integer orgType) {
        if (orgType == null) {
            return null;
        }

        List<CRCCOrganizationDto> orgList = new ArrayList<>();
        switch (orgType) {
            case 0:
                CRCCOrganizationDto org = apiAsyncGetCompanyRoot();
                if(org != null) {
                    orgList = org.getChildren();
                }
                break;
            case 1:
                orgList = apiAsyncGetCompanyChildren(providerId, orgId);
                break;
            case 2:
                orgList = apiAsyncGetDepartmentChildren(providerId, orgId);
                break;
            default:
                orgList = null;
                break;
        }

        if(orgList != null && !orgList.isEmpty()) {
            return orgList;
        }

        return null;
    }

    /**
     * 获取任一机构上级信息，通过单位id、部门id、岗位id，用户id
     *
     * @param providerId
     * @param orgId
     * @param orgType 1 单位  2 部门  3 岗位  4 用户
     * @return
     * @throws Exception
     */
    private static CRCCOrganizationDto apiGetOrganizationParent(String providerId, Integer orgId, Integer orgType) {
        if (orgType == null) {
            return null;
        }

        switch (orgType) {
            case 0:
                return apiGetCompanyRoot();
            case 1:
                return apiGetCompanyParent(providerId, orgId);
            case 2:
                return apiGetDepartmentParent(providerId, orgId);
            case 3:
                return apiGetPositionDepartment(providerId, orgId);
        }

        return null;
    }
    private static CRCCOrganizationDto apiAsyncGetOrganizationParent(String providerId, Integer orgId, Integer orgType) {
        if (orgType == null) {
            return null;
        }

        switch (orgType) {
            case 0:
                return apiAsyncGetCompanyRoot();
            case 1:
                return apiAsyncGetCompanyParent(providerId, orgId);
            case 2:
                return apiAsyncGetDepartmentParent(providerId, orgId);
            case 3:
                return apiAsyncGetPositionDepartment(providerId, orgId);
        }

        return null;
    }

    /**
     * 获取单位
     *
     * @param providerId
     * @param orgId
     * @return
     * @throws Exception
     */
    private static CRCCOrganizationDto apiGetCompany(String providerId, Integer orgId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgApi(accessTokenDto, "/org/"+providerId+"/company/"+orgId);
        if(org != null) {
            org.setProvider(providerId);
        }
        return org;
    }
    private static CRCCOrganizationDto apiAsyncGetCompany(String providerId, Integer orgId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgAsyncApi(accessTokenDto, "/org/"+providerId+"/company/"+orgId, providerId);
        if(org != null) {
            org.setProvider(providerId);
        }
        return org;
    }
    private static String apiGetCompanyName(String providerId, Integer orgId) {
        CRCCOrganizationDto company = apiGetCompany(providerId, orgId);
        if(company != null) {
            return company.getName();
        }

        return null;
    }

    /**
     * 获取所有二级集团单位集合
     *
     * @return
     * @throws Exception
     */
    private static CRCCOrganizationDto apiGetCompanyRoot() {
        if (accessTokenDto == null) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto root = new CRCCOrganizationDto();
        root.setType(CRCCConstants.NODE_TYPE);
        root.setId(CRCCConstants.NODE_ID);
        root.setProvider(CRCCConstants.NODE_PROVIDER);
        root.setName(CRCCConstants.NODE_NAME);
        root.setFullname(CRCCConstants.NODE_NAME);
        root.setCode(CRCCConstants.NODE_CODE);
        root.setOrder(0);
        root.setShow(true);
        root.setVirtual(false);
        root.setLevel(1);

        List<JSONObject> orgList = TokenHolder.getObjListApi(accessTokenDto, "/orglist");
        List<CRCCOrganizationDto> _orgList = new ArrayList<>();
        if(orgList != null) {
            for (JSONObject x : orgList) {
                String providerId = x.getString("id");
                CRCCOrganizationDto org = x.getObject("root", CRCCOrganizationDto.class);
                org.setLevel(2);
                org.setProvider(providerId);
                _orgList.add(org);
            }
        }
        root.setChildren(_orgList);

        return root;
    }
    private static CRCCOrganizationDto apiAsyncGetCompanyRoot() {
        if (accessTokenDto == null) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto root = new CRCCOrganizationDto();
        root.setType(CRCCConstants.NODE_TYPE);
        root.setId(CRCCConstants.NODE_ID);
        root.setProvider(CRCCConstants.NODE_PROVIDER);
        root.setName(CRCCConstants.NODE_NAME);
        root.setFullname(CRCCConstants.NODE_NAME);
        root.setCode(CRCCConstants.NODE_CODE);
        root.setOrder(0);
        root.setShow(true);
        root.setVirtual(false);
        root.setLevel(1);

        List<JSONObject> orgList = TokenHolder.getObjListAsyncApi(accessTokenDto, "/orglist");
        List<CRCCOrganizationDto> _orgList = new ArrayList<>();
        if(orgList != null) {
            for (JSONObject x : orgList) {
                String providerId = x.getString("id");
                CRCCOrganizationDto org = x.getObject("root", CRCCOrganizationDto.class);
                org.setLevel(2);
                org.setProvider(providerId);
                _orgList.add(org);
            }
        }
        root.setChildren(_orgList);

        return root;
    }

    /**
     * 获取上级单位
     *
     * @param providerId
     * @param orgId
     * @return
     * @throws Exception
     */
    private static CRCCOrganizationDto apiGetCompanyParent(String providerId, Integer orgId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgApi(accessTokenDto, "/org/"+providerId+"/company/"+orgId+"/parent");
        if(org != null) {
            org.setProvider(providerId);
        }
        return org;
    }
    private static CRCCOrganizationDto apiAsyncGetCompanyParent(String providerId, Integer orgId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgAsyncApi(accessTokenDto, "/org/"+providerId+"/company/"+orgId+"/parent", providerId);
        if(org != null) {
            org.setProvider(providerId);
        }
        return org;
    }

    /**
     * 获取单位直接下级组织机构集合
     *
     * @param providerId
     * @param orgId
     * @return
     * @throws Exception
     */
    private static List<CRCCOrganizationDto> apiGetCompanyChildren(String providerId, Integer orgId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        List<CRCCOrganizationDto> orgList = TokenHolder.getOrgListApi(accessTokenDto, "/org/"+providerId+"/company/"+orgId+"/children");
        if(orgList != null) {
            for (CRCCOrganizationDto x : orgList) {
                x.setProvider(providerId);
                if(x.getType() == 3) {
                    x.setVirtual(false);
                }
            }
        }
        return orgList;
    }
    private static List<CRCCOrganizationDto> apiAsyncGetCompanyChildren(String providerId, Integer orgId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        List<CRCCOrganizationDto> orgList = TokenHolder.getOrgListAsyncApi(accessTokenDto, "/org/"+providerId+"/company/"+orgId+"/children", providerId);
        if(orgList != null) {
            for (CRCCOrganizationDto x : orgList) {
                x.setProvider(providerId);
                if(x.getType() == 3) {
                    x.setVirtual(false);
                }
            }
        }
        return orgList;
    }

    /**
     * 获取组织机构路径
     *
     * @param providerId
     * @param orgId
     * @return
     * @throws Exception
     */
    private static List<CRCCOrganizationDto> apiGetOrganizationPath(String providerId, Integer orgId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        List<CRCCOrganizationDto> orgList = TokenHolder.getOrgListApi(accessTokenDto, "/org/"+providerId+"/path/"+orgId);
        if(orgList != null) {
            for (CRCCOrganizationDto x : orgList) {
                x.setProvider(providerId);
                if(x.getType() == 3) {
                    x.setVirtual(false);
                }
            }
        }
        return orgList;
    }
    private static List<CRCCOrganizationDto> apiAsyncGetOrganizationPath(String providerId, Integer orgId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        List<CRCCOrganizationDto> orgList = TokenHolder.getOrgListAsyncApi(accessTokenDto, "/org/"+providerId+"/path/"+orgId, providerId);
        if(orgList != null) {
            for (CRCCOrganizationDto x : orgList) {
                x.setProvider(providerId);
                if(x.getType() == 3) {
                    x.setVirtual(false);
                }
            }
        }
        return orgList;
    }

    /**
     * 获取组织机构路径
     *
     * @param providerId
     * @param orgId
     * @param level
     * @param virtual
     * @return
     * @throws Exception
     */
    private static String apiGetOrganizationPathStr(String providerId, Integer orgId, Integer level, Boolean virtual) {
        if(providerId.compareTo(CRCCConstants.NODE_PROVIDER) == 0 && orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return CRCCConstants.NODE_NAME;
        }

        List<CRCCOrganizationDto> orgList = apiGetOrganizationPath(providerId, orgId);
        StringBuilder path = new StringBuilder();

        if(orgList != null) {
            Integer _level = level;
            if(_level < 1) {
                _level = 1;
            }

            if(_level > orgList.size()) {
                _level = orgList.size();
            }

            Boolean _virtual = virtual;
            CRCCOrganizationDto org = orgList.get(0);
            if(org.getVirtual() != null && org.getVirtual()) {
                _virtual = false;
            }

            for (int i = orgList.size() - _level; i >= 0; i--) {
                if(!_virtual) {
                    path.append(orgList.get(i).getName()).append("/");
                } else {
                    if(orgList.get(i).getVirtual() != null && !orgList.get(i).getVirtual()) {
                        path.append(orgList.get(i).getName()).append("/");
                    }
                }
            }

            if(path.length() > 0) {
                return path.substring(0,path.length()-1);
            }
        }

        return null;
    }
    private static String apiAsyncGetOrganizationPathStr(String providerId, Integer orgId, Integer level, Boolean virtual) {
        if(providerId.compareTo(CRCCConstants.NODE_PROVIDER) == 0 && orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return CRCCConstants.NODE_NAME;
        }

        List<CRCCOrganizationDto> orgList = apiAsyncGetOrganizationPath(providerId, orgId);
        StringBuilder path = new StringBuilder();

        if(orgList != null) {
            Integer _level = level;
            if(_level < 1) {
                _level = 1;
            }

            if(_level > orgList.size()) {
                _level = orgList.size();
            }

            for (int i = orgList.size() - _level; i >= 0; i--) {
                if(!virtual) {
                    path.append(orgList.get(i).getName()).append("/");
                } else {
                    if(orgList.get(i).getVirtual() != null && !orgList.get(i).getVirtual()) {
                        path.append(orgList.get(i).getName()).append("/");
                    }
                }
            }

            if(path.length() > 0) {
                return path.substring(0,path.length()-1);
            }
        }

        return null;
    }

    /**
     * 获取部门
     *
     * @param providerId
     * @param deptId
     * @return
     * @throws Exception
     */
    private static CRCCOrganizationDto apiGetDepartment(String providerId, Integer deptId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || deptId == null || deptId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgApi(accessTokenDto, "/org/"+providerId+"/department/"+deptId);
        if(org != null) {
            org.setProvider(providerId);
        }
        return org;
    }
    private static CRCCOrganizationDto apiAsyncGetDepartment(String providerId, Integer deptId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || deptId == null || deptId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgAsyncApi(accessTokenDto, "/org/"+providerId+"/department/"+deptId, providerId);
        if(org != null) {
            org.setProvider(providerId);
        }
        return org;
    }
    private static String apiGetDepartmentName(String providerId, Integer positionId) {
        CRCCOrganizationDto department = apiGetDepartment(providerId, positionId);
        if(department != null) {
            return department.getName();
        }

        return null;
    }

    /**
     * 获取部门上级
     *
     * @param providerId
     * @param deptId
     * @return
     * @throws Exception
     */
    private static CRCCOrganizationDto apiGetDepartmentParent(String providerId, Integer deptId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || deptId == null || deptId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgApi(accessTokenDto, "/org/"+providerId+"/department/"+deptId+"/parent");
        if(org != null) {
            org.setProvider(providerId);
        }
        return org;
    }
    private static CRCCOrganizationDto apiAsyncGetDepartmentParent(String providerId, Integer deptId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || deptId == null || deptId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgAsyncApi(accessTokenDto, "/org/"+providerId+"/department/"+deptId+"/parent", providerId);
        if(org != null) {
            org.setProvider(providerId);
        }
        return org;
    }
    private static String apiGetDepartmentParentName(String providerId, Integer deptId) {
        CRCCOrganizationDto parent = apiGetDepartmentParent(providerId, deptId);
        if(parent != null) {
            return parent.getName();
        }

        return null;
    }

    /**
     * 获取部门下级组织机构集合
     *
     * @param providerId
     * @param deptId
     * @return
     * @throws Exception
     */
    private static List<CRCCOrganizationDto> apiGetDepartmentChildren(String providerId, Integer deptId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || deptId == null || deptId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        List<CRCCOrganizationDto> orgList = TokenHolder.getOrgListApi(accessTokenDto, "/org/"+providerId+"/department/"+deptId+"/children");
        if(orgList != null) {
            for (CRCCOrganizationDto x : orgList) {
                x.setProvider(providerId);
                if(x.getType() == 3) {
                    x.setVirtual(false);
                }
            }
        }
        return orgList;
    }
    private static List<CRCCOrganizationDto> apiAsyncGetDepartmentChildren(String providerId, Integer deptId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || deptId == null || deptId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        List<CRCCOrganizationDto> orgList = TokenHolder.getOrgListAsyncApi(accessTokenDto, "/org/"+providerId+"/department/"+deptId+"/children", providerId);
        if(orgList != null) {
            for (CRCCOrganizationDto x : orgList) {
                x.setProvider(providerId);
                if(x.getType() == 3) {
                    x.setVirtual(false);
                }
            }
        }
        return orgList;
    }

    /**
     * 获取岗位
     *
     * @param providerId
     * @param positionId
     * @return
     * @throws Exception
     */
    private static CRCCOrganizationDto apiGetPosition(String providerId, Integer positionId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || positionId == null || positionId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgApi(accessTokenDto, "/org/"+providerId+"/position/"+positionId);
        if(org != null) {
            org.setProvider(providerId);
            if(org.getType() == 3) {
                org.setVirtual(false);
            }
        }
        return org;
    }
    private static CRCCOrganizationDto apiAsyncGetPosition(String providerId, Integer positionId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || positionId == null || positionId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgAsyncApi(accessTokenDto, "/org/"+providerId+"/position/"+positionId, providerId);
        if(org != null) {
            org.setProvider(providerId);
            if(org.getType() == 3) {
                org.setVirtual(false);
            }
        }
        return org;
    }
    private static String apiGetPositionName(String providerId, Integer positionId) {
        CRCCOrganizationDto position = apiGetPosition(providerId, positionId);
        if(position != null) {
            return position.getName();
        }

        return null;
    }

    /**
     * 获取岗位所在部门
     *
     * @param providerId
     * @param positionId
     * @return
     * @throws Exception
     */
    private static CRCCOrganizationDto apiGetPositionDepartment(String providerId, Integer positionId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || positionId == null || positionId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgApi(accessTokenDto, "/org/"+providerId+"/position/"+positionId+"/parent");
        if(org != null) {
            org.setProvider(providerId);
        }
        return org;
    }
    private static CRCCOrganizationDto apiAsyncGetPositionDepartment(String providerId, Integer positionId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || positionId == null || positionId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgAsyncApi(accessTokenDto, "/org/"+providerId+"/position/"+positionId+"/parent", providerId);
        if(org != null) {
            org.setProvider(providerId);
        }
        return org;
    }
    private static String apiGetPositionDepartmentName(String providerId, Integer positionId) {
        CRCCOrganizationDto department = apiGetPositionDepartment(providerId, positionId);
        if(department != null) {
            return department.getName();
        }

        return null;
    }

    /**
     * 获取岗位下所有用户集合
     *
     * @param providerId
     * @param positionId
     * @return
     * @throws Exception
     */
    private static List<CRCCUserInfoDto> apiGetPositionUsers(String providerId, Integer positionId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || positionId == null || positionId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        List<CRCCUserInfoDto> userList = TokenHolder.getUserListApi(accessTokenDto, "/org/"+providerId+"/position/"+positionId+"/users");
        if(userList != null) {
            for (CRCCUserInfoDto x : userList) {
                x.setProvider(providerId);
            }
        }
        return userList;
    }
    private static void apiAsyncGetPositionUsers(String providerId, Integer positionId, List<CRCCUserInfoDto> list) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || positionId == null || positionId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        TokenHolder.getUserListAsyncApi(accessTokenDto, "/org/"+providerId+"/position/"+positionId+"/users", providerId, list);
    }
    private static void apiAsyncGetPositionUsers(String providerId, Integer positionId, CRCCOrganizationDto org) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || positionId == null || positionId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        TokenHolder.getUserListAsyncApi(accessTokenDto, "/org/"+providerId+"/position/"+positionId+"/users", providerId, org);
    }

    /**
     * 搜索用户
     *
     * @param providerId
     * @param orgId
     * @param username
     * @param mainPositionOnly
     * @return
     * @throws Exception
     */
    private static List<JSONObject> apiSearchUserInfo(String providerId, Integer orgId, String username, Boolean mainPositionOnly) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null ||  orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        return TokenHolder.getObjListApi(accessTokenDto, "/org/"+providerId+"/searchuser/"+orgId+"?username="+username+"&mainPositionOnly="+mainPositionOnly);
    }
    private static List<JSONObject> apiAsyncSearchUserInfo(String providerId, Integer orgId, String username, Boolean mainPositionOnly) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        return TokenHolder.getObjListAsyncApi(accessTokenDto, "/org/"+providerId+"/searchuser/"+orgId+"?username="+username+"&mainPositionOnly="+mainPositionOnly);
    }
    private static List<CRCCUserInfoDto> apiSearchUserInfo(String providerId, Integer orgId, String username, Boolean mainPositionOnly, Integer level, Boolean virtual) {
        if (level == null ||  virtual == null) {
            return null;
        }

        List<JSONObject> jsonList = apiSearchUserInfo(providerId, orgId, username, mainPositionOnly);

        if(jsonList != null) {
            List<CRCCUserInfoDto> listUser = new ArrayList<>();
            for(JSONObject x: jsonList) {
                JSONObject json = x.getJSONObject("user");
                JSONArray jsons = x.getJSONArray("positions");
                if(jsons != null) {
                    for(int i=0; i<jsons.size(); i++) {
                        JSONObject position = jsons.getJSONObject(i);
                        if(position != null && position.getBoolean("mainPosition")) {
                            Integer positionId = position.getInteger("id");
                            if(positionId != null) {
                                String path = apiGetOrganizationPathStr(providerId, positionId, level, virtual);
                                json.put("provider", providerId);
                                json.put("path", path);
                                break;
                            }
                        }
                    }
                }
                CRCCUserInfoDto user = new CRCCUserInfoDto();
                user.set(json);
                listUser.add(user);
            }

            if(!listUser.isEmpty()) {
                return listUser;
            }
        }

        return null;
    }
    private static List<CRCCUserInfoDto> apiSearchUserInfo(String providerId, Integer orgId, Integer orgType, String username) throws InterruptedException {
        if (orgType == null) {
            return null;
        }

        List<CRCCUserInfoDto> listUser = new ArrayList<>();

        switch (orgType) {
            case 0:
                CRCCOrganizationDto org = apiGetCompanyRoot();
                if(org != null) {
                    List<CRCCOrganizationDto> orgList = org.getChildren();
                    if(orgList != null) {
                        ExecutorService es = Executors.newCachedThreadPool();
                        CountDownLatch latch = new CountDownLatch(orgList.size());

                        for(CRCCOrganizationDto x: orgList) {
                            String provider = x.getProvider();
                            Integer id = x.getId();

                            List<CRCCUserInfoDto> finalListUser = listUser;
                            es.submit(() -> {
                                try {
                                    List<CRCCUserInfoDto> userList = apiSearchUserInfo(provider, id, username, false, 1, true);
                                    if(userList != null && !userList.isEmpty()) {
                                        finalListUser.addAll(userList);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    latch.countDown();
                                }
                            });
                        }

                        latch.await();
                        es.shutdown();
                    }
                }
                break;
            case 3:
                listUser = apiGetPositionUsers(providerId, orgId);
                if(listUser != null) {
                    listUser.removeIf(item -> !item.toString().contains(username));
                    String path = apiGetOrganizationPathStr(providerId, orgId, 1, true);
                    for(CRCCUserInfoDto x: listUser) {
                        x.setPath(path);
                    }
                }
                break;
            default: {
                listUser = apiSearchUserInfo(providerId, orgId, username, false, 1, true);
                break;
            }
        }

        if(listUser != null && !listUser.isEmpty()) {
            listUser.sort(Comparator.comparing(CRCCUserInfoDto::getProvider).thenComparing(CRCCUserInfoDto::getId));
            return listUser;
        }

        return null;
    }

    /**
     * 获取所有组织机构
     *
     * @param providerId
     * @param orgId
     * @return
     * @throws Exception
     */
    private static CRCCOrganizationDto apiGetOrganizationTree(String providerId, Integer orgId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgApi(accessTokenDto, "/tree/"+providerId+"/"+orgId);
        if(org != null) {
            org.setProvider(providerId);
        }
        return org;
    }
    private static void apiAsyncGetOrganizationTree(String providerId, Integer orgId, CRCCOrganizationDto org) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || orgId.compareTo(CRCCConstants.NODE_ID) == 0 || org == null) {
            return;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        TokenHolder.getOrgAsyncApi(accessTokenDto, "/tree/"+providerId+"/"+orgId, providerId, org);
    }
    private static void apiAsyncGetOrganizationTree(String providerId, Integer orgId, List<CRCCOrganizationDto> list, Integer index) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || orgId.compareTo(CRCCConstants.NODE_ID) == 0 || list == null || index == null) {
            return;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        TokenHolder.getOrgAsyncApi(accessTokenDto, "/tree/"+providerId+"/"+orgId, providerId, list, index);
    }

    /**
     * 获取所有组织机构
     *
     * @param providerId
     * @param orgId
     * @return
     * @throws Exception
     */
    private static CRCCOrganizationDto apiGetOrganizationAndUsersTree(String providerId, Integer orgId) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
            return null;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        CRCCOrganizationDto org = TokenHolder.getOrgApi(accessTokenDto, "/tree/"+providerId+"/"+orgId+"/users");


        if(org != null) {
            org.setProvider(providerId);
        }
        return org;
    }
    private static void apiAsyncGetOrganizationAndUsersTree(String providerId, Integer orgId, CRCCOrganizationDto org) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || orgId.compareTo(CRCCConstants.NODE_ID) == 0 || org == null) {
            return;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        TokenHolder.getOrgAsyncApi(accessTokenDto, "/tree/"+providerId+"/"+orgId+"/users", providerId, org);
    }
    private static void apiAsyncGetOrganizationAndUsersTree(String providerId, Integer orgId, List<CRCCOrganizationDto> list, Integer index) {
        if (accessTokenDto == null || providerId == null || providerId.length() == 0 || orgId == null || orgId.compareTo(CRCCConstants.NODE_ID) == 0 || list == null || index == null) {
            return;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        TokenHolder.getOrgAsyncApi(accessTokenDto, "/tree/"+providerId+"/"+orgId+"/users", providerId, list, index);
    }

    /**
     * 遍历住址机构下所有组织机构及人员
     * @param organization
     * @param company
     * @param department
     * @throws Exception
     */
    private static void apiLoopOrganizationChildAndUsers(CRCCOrganizationDto organization, JSONObject company, JSONObject department) {
        if (organization == null || company == null || department == null) {
            return;
        }

        String providerId = organization.getProvider();
        Integer orgId = organization.getId();
        Integer orgType = organization.getType();
        Integer level = organization.getLevel();
        Integer index = organization.getIndex();
        String name = organization.getName();
        Boolean virtual = organization.getVirtual();

        JSONObject _company = CloneUtils.cloneTo(company);
        JSONObject _department = CloneUtils.cloneTo(department);
        if(orgType == 1 && !virtual) {
            _company.put("provider", providerId);
            _company.put("id", orgId);
            _company.put("name", name);
        } else if(orgType == 2) {
            _department.put("provider", providerId);
            _department.put("id", orgId);
            _department.put("name", name);
        }

        List<CRCCOrganizationDto> orgChildren = apiGetOrganizationChildren(providerId, orgId, orgType);
        if (orgChildren != null) {
            orgChildren.sort(Comparator.comparing(CRCCOrganizationDto::getOrder));
            organization.setChildren(orgChildren);

            for (CRCCOrganizationDto x : orgChildren) {
                x.setLevel(level + 1);
                x.setIndex(index);

                if (x.getProvider() == null) {
                    x.setProvider(organization.getProvider());
                }

                if (x.getType() < 3) {
                    apiLoopOrganizationChildAndUsers(x, _company, _department);
                } else if (x.getType() == 3) {
                    JSONObject _position = new JSONObject();
                    _position.put("provider", x.getProvider());
                    _position.put("id", x.getId());
                    _position.put("name", x.getName());

                    List<CRCCUserInfoDto> listUsers = new ArrayList<>();
                    x.setUsers(listUsers);

                    List<CRCCUserInfoDto> _listUsers = apiGetPositionUsers(providerId, x.getId());
                    if (_listUsers != null) {
                        _listUsers.sort(Comparator.comparing(CRCCUserInfoDto::getOrder));
                        listUsers.addAll(_listUsers);
                        for (CRCCUserInfoDto y : listUsers) {
                            y.set(CRCCOrganizationDto.userIndex++, x.getProvider(), _company, _department, _position);
                        }
                    }
                }
            }
        }
    }

    /**
     * 遍历住址机构下所有组织机构
     * @param providerId
     * @param orgId
     * @param orgType
     * @param orgLevel
     * @param orgIndex
     * @throws Exception
     */
    private static void apiLoopOrganizationChildren(String providerId, Integer orgId, Integer orgType, Integer orgLevel, Integer orgIndex) {
        CRCCOrganizationDto organization = apiGetOrganization(providerId, orgId, orgType);
        if (organization != null) {
            List<CRCCOrganizationDto> orgChildren = apiGetOrganizationChildren(providerId, orgId, orgType);
            if (orgChildren != null) {
                orgChildren.sort(Comparator.comparing(CRCCOrganizationDto::getOrder));
                organization.setChildren(orgChildren);

                for (CRCCOrganizationDto x : orgChildren) {
                    x.setLevel(orgLevel + 1);
                    x.setIndex(orgIndex);

                    if (x.getProvider() == null) {
                        x.setProvider(providerId);
                    }

                    if (x.getType() < 3) {
                        apiLoopOrganizationChildren(x.getProvider(), x.getId(), x.getType(), x.getLevel(), x.getIndex());
                    }
                }
            }
        }
    }

    /**
     * 在新组织机构根节点插入新的组织机构
     *
     * @param srcOrganization
     * @param providerId
     * @param orgId
     * @return
     * @throws Exception
     */
    private static void apiInsertOrganization(CRCCOrganizationDto srcOrganization, String providerId, Integer orgId) {
        if (accessTokenDto == null || srcOrganization == null || providerId == null || providerId.length() == 0 || orgId == null) {
            return;
        }

        if(!isTokenInvalid()) {
            tokenHolderInit();
        }

        List<CRCCOrganizationDto> orgList = apiGetOrganizationPath(providerId, orgId);
        CRCCOrganizationDto.treeOrganizationList(orgList);
        if(orgList != null) {
            for (int i=1; i<orgList.size(); i++) {
                CRCCOrganizationDto destParent = orgList.get(i);
                CRCCOrganizationDto destOrg = orgList.get(i-1);
                if (CRCCOrganizationDto.findOrganizationById(srcOrganization, destParent.getProvider(), destParent.getId()) != null) {
                    CRCCOrganizationDto.insertOrganization(srcOrganization, destParent, destOrg);
                    break;
                }
            }
        }
    }

    /**
     * 获取对象权限
     *
     * @param objProvider
     * @param objId
     * @return
     */
    private static AuthEntity apiSelectAuthLoginEntity(String objProvider, Integer objId, AuthEntityRepository authEntityRepository) {
        if (objProvider == null || objProvider.length() == 0 || objId == null) {
            return null;
        }

        if (authEntityRepository != null) {
            AuthEntity authEntity = authEntityRepository.getCurrentAuthEntityByObjId(objProvider, objId);
            if(authEntity == null) {
                CRCCOrganizationDto positionDto = apiGetUserMainPosition(objProvider, objId);
                if(positionDto != null) {
                    authEntity = authEntityRepository.getCurrentAuthEntityByObjId(objProvider, positionDto.getId());
                }
            }

            return authEntity;
        }

        return null;
    }

    /**
     * 登录人员鉴权组织更新
     *
     * @param authEntity
     * @param authEntityRepository
     * @return
     */
    public static void apiUpdateAuthLoginEntity(AuthEntity authEntity, AuthEntityRepository authEntityRepository) {
        if(authEntity != null) {
            String objProvider = authEntity.getObjProvider();
            Integer objId = authEntity.getObjPostId();
            String nodeProvider = authEntity.getNodeProvider();
            Integer nodeId = authEntity.getNodeId();
            String nodeParentProvider = authEntity.getNodeParentProvider();
            Integer nodeParentId = authEntity.getNodeParentId();

            String path_obj = apiGetOrganizationPathStr(objProvider, objId, 1, true);
            String post_name = CRCCOrganizationDto.findOrganizationNameFromPath(path_obj, -1);
            String path_node = apiGetOrganizationPathStr(nodeProvider, nodeId, 1, true);
            String path_parent = apiGetOrganizationPathStr(nodeParentProvider, nodeParentId, 1, true);
            authEntity.setObjPath(path_obj);
            authEntity.setNodePath(path_node);
            authEntity.setNodeParentPath(path_parent);
            authEntity.setObjPostName(post_name);
            if(authEntity.getObjType() == 1) {
                authEntity.setObjName(post_name);
            }

            authEntityRepository.updateAuthEntity(authEntity);
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////   static函数   //////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 获取登录人员权限
     *
     * @param providerId
     * @param userId
     * @return
     */
    public static AuthEntity authLoginEntity(String providerId, Integer userId, AuthEntityRepository authEntityRepository) {
        if (providerId == null || providerId.length() == 0 || userId == null) {
            return null;
        }

        if (authEntityRepository != null) {
            List<AuthEntity> list = authEntityRepository.getAllAuthEntity();
            if (list != null && list.isEmpty()) {
                return CRCCConstants.ADMIN_AUTH;
            }

            AuthEntity authEntity = authEntityRepository.getCurrentAuthEntityByObjId(providerId, userId);
            if(authEntity == null) {
                CRCCOrganizationDto positionDto = apiGetUserMainPosition(providerId, userId);
                if(positionDto != null) {
                    authEntity = authEntityRepository.getCurrentAuthEntityByObjId(providerId, positionDto.getId());
                }
            }

            return authEntity;
        }

        return null;
    }

    /**
     * 登录人员鉴权更新、变化、删除
     *
     * @param optProvider
     * @param optId
     * @param authEntityRepository
     * @param postChangeLogEntityRepository
     * @param orgLogEntityRepository
     * @return
     * @throws Exception
     */
    public static Boolean authLogin(String optProvider, Integer optId,
                                    AuthEntityRepository authEntityRepository,
                                    PostChangeLogEntityRepository postChangeLogEntityRepository,
                                    OrgLogEntityRepository orgLogEntityRepository) {
        if (authEntityRepository != null && postChangeLogEntityRepository != null && orgLogEntityRepository != null &&
                apiSelectAuthLoginEntity(optProvider, optId, authEntityRepository) != null) {
            List<AuthEntity> listAuthEntity = authEntityRepository.getAllAuthEntity();

            if(listAuthEntity != null) {
                for (AuthEntity x : listAuthEntity) {
                    String path_node = apiGetOrganizationPathStr(x.getNodeProvider(), x.getNodeId(), 1, true);
                    if(path_node == null) {
                        String name = CRCCOrganizationDto.findOrganizationNameFromPath(x.getNodePath(), -1);
                        path_node = apiGetOrganizationPathStr(x.getNodeParentProvider(), x.getNodeParentId(), 1, true) + "/" + name;

                        String content = "授权组织机构删除变化! " + "授权对象：" +
                                x.getObjName() +
                                "，授权节点：" +
                                path_node;

                        OrgLogEntity orgLog = new OrgLogEntity(x.getNodeParentProvider(), x.getNodeParentId(), x.getNodeParentCode(), path_node, x.getNodeIndex(), content, x.getObjName());

                        authEntityRepository.deleteAuthEntity(x);
                        orgLogEntityRepository.insertOrgLogEntity(orgLog);
                    } else {
                        if (x.getObjType() == 0) {
                            CRCCOrganizationDto position = apiGetUserMainPosition(x.getObjProvider(), x.getObjId());
                            if (position != null) {
                                String post_provider = position.getProvider();
                                Integer post_id = position.getId();
                                if (x.getObjProvider().compareTo(post_provider) != 0 || x.getObjPostId().compareTo(post_id) != 0) {
                                    String post_name = position.getName();
                                    String path_obj = apiGetOrganizationPathStr(post_provider, post_id, 1, true);

                                    String content = "授权对象岗位发生变化! " + "授权对象：" +
                                            x.getObjName() +
                                            "，原岗位：" +
                                            x.getObjPath() +
                                            "，现岗位：" +
                                            path_obj +
                                            "，授权节点：" +
                                            path_node;
                                    PostChangeLogEntity postChangeLog = new PostChangeLogEntity(x.getNodeProvider(), x.getNodeId(), x.getNodeCode(), path_node, x.getNodeIndex(), content, x.getObjPath(), path_obj, x.getObjName());

                                    x.setObjNode(post_id);
                                    x.setObjPostId(post_id);
                                    x.setObjPostName(post_name);
                                    x.setObjPath(path_obj);
                                    x.setNodePath(path_node);

                                    authEntityRepository.updateAuthEntity(x);
                                    postChangeLogEntityRepository.insertPostChangeLogEntity(postChangeLog);
                                } else {
                                    String path_obj = apiGetOrganizationPathStr(x.getObjProvider(), x.getObjPostId(), 1, true);
                                    String post_name = CRCCOrganizationDto.findOrganizationNameFromPath(path_obj, -1);
                                    String path_parent = CRCCOrganizationDto.findOrganizationSubFromPath(path_node, -1);
                                    x.setObjPath(path_obj);
                                    x.setNodePath(path_node);
                                    x.setNodeParentPath(path_parent);
                                    x.setObjPostName(post_name);
                                    if(x.getObjType() == 1) {
                                        x.setObjName(post_name);
                                    }

                                    authEntityRepository.updateAuthEntity(x);
                                }
                            }
                        } else {
                            String path_obj = apiGetOrganizationPathStr(x.getObjProvider(), x.getObjPostId(), 1, true);
                            String post_name = CRCCOrganizationDto.findOrganizationNameFromPath(path_obj, -1);
                            String path_parent = CRCCOrganizationDto.findOrganizationSubFromPath(path_node, -1);
                            x.setObjPath(path_obj);
                            x.setNodePath(path_node);
                            x.setNodeParentPath(path_parent);
                            x.setObjPostName(post_name);
                            if(x.getObjType() == 1) {
                                x.setObjName(post_name);
                            }

                            authEntityRepository.updateAuthEntity(x);
                        }
                    }
                }
            }

            return true;
        }

        return false;
    }
    public static Boolean asyncAuthLogin(String optProvider, Integer optId,
                                         AuthEntityRepository authEntityRepository,
                                         PostChangeLogEntityRepository postChangeLogEntityRepository,
                                         OrgLogEntityRepository orgLogEntityRepository, Boolean await) throws Exception {
        if (authEntityRepository != null && postChangeLogEntityRepository != null && orgLogEntityRepository != null &&
                apiSelectAuthLoginEntity(optProvider, optId, authEntityRepository) != null) {
            List<AuthEntity> listAuthEntity = authEntityRepository.getAllAuthEntity();

            if(listAuthEntity != null) {
                ExecutorService es = Executors.newCachedThreadPool();
                CountDownLatch latch = new CountDownLatch(listAuthEntity.size());

                for (AuthEntity x : listAuthEntity) {
                    es.submit(() -> {
                        try {
                            String path_node = apiGetOrganizationPathStr(x.getNodeProvider(), x.getNodeId(), 1, true);
                            if(path_node == null) {
                                String name = CRCCOrganizationDto.findOrganizationNameFromPath(x.getNodePath(), -1);
                                path_node = apiGetOrganizationPathStr(x.getNodeParentProvider(), x.getNodeParentId(), 1, true) + "/" + name;

                                String content = "授权组织机构删除变化! " + "授权对象：" +
                                        x.getObjName() +
                                        "，授权节点：" +
                                        path_node;

                                OrgLogEntity orgLog = new OrgLogEntity(x.getNodeParentProvider(), x.getNodeParentId(), x.getNodeParentCode(), path_node, x.getNodeIndex(), content, x.getObjName());

                                authEntityRepository.deleteAuthEntity(x);
                                orgLogEntityRepository.insertOrgLogEntity(orgLog);
                            } else {
                                if (x.getObjType() == 0) {
                                    CRCCOrganizationDto position = apiGetUserMainPosition(x.getObjProvider(), x.getObjId());
                                    if (position != null) {
                                        String post_provider = position.getProvider();
                                        Integer post_id = position.getId();
                                        if (x.getObjProvider().compareTo(post_provider) != 0 || x.getObjPostId().compareTo(post_id) != 0) {
                                            String post_name = position.getName();
                                            String path_obj = apiGetOrganizationPathStr(post_provider, post_id, 1, true);

                                            String content = "授权对象岗位发生变化! " + "授权对象：" +
                                                    x.getObjName() +
                                                    "，原岗位：" +
                                                    x.getObjPath() +
                                                    "，现岗位：" +
                                                    path_obj +
                                                    "，授权节点：" +
                                                    path_node;
                                            PostChangeLogEntity postChangeLog = new PostChangeLogEntity(x.getNodeProvider(), x.getNodeId(), x.getNodeCode(), path_node, x.getNodeIndex(), content, x.getObjPath(), path_obj, x.getObjName());

                                            x.setObjNode(post_id);
                                            x.setObjPostId(post_id);
                                            x.setObjPostName(post_name);
                                            x.setObjPath(path_obj);
                                            x.setNodePath(path_node);

                                            authEntityRepository.updateAuthEntity(x);
                                            postChangeLogEntityRepository.insertPostChangeLogEntity(postChangeLog);
                                        } else {
                                            String path_obj = apiGetOrganizationPathStr(x.getObjProvider(), x.getObjPostId(), 1, true);
                                            String post_name = CRCCOrganizationDto.findOrganizationNameFromPath(path_obj, -1);
                                            String path_parent = CRCCOrganizationDto.findOrganizationSubFromPath(path_node, -1);
                                            x.setObjPath(path_obj);
                                            x.setNodePath(path_node);
                                            x.setNodeParentPath(path_parent);
                                            x.setObjPostName(post_name);
                                            if(x.getObjType() == 1) {
                                                x.setObjName(post_name);
                                            }

                                            authEntityRepository.updateAuthEntity(x);
                                        }
                                    }
                                } else {
                                    String path_obj = apiGetOrganizationPathStr(x.getObjProvider(), x.getObjPostId(), 1, true);
                                    String post_name = CRCCOrganizationDto.findOrganizationNameFromPath(path_obj, -1);
                                    String path_parent = CRCCOrganizationDto.findOrganizationSubFromPath(path_node, -1);
                                    x.setObjPath(path_obj);
                                    x.setNodePath(path_node);
                                    x.setNodeParentPath(path_parent);
                                    x.setObjPostName(post_name);
                                    if(x.getObjType() == 1) {
                                        x.setObjName(post_name);
                                    }

                                    authEntityRepository.updateAuthEntity(x);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    });
                }

                if(await) {
                    latch.await();
                }
                es.shutdown();
            }

            return true;
        }

        return false;
    }

    /**
     * 登录人员鉴权岗位变化
     *
     * @param optProvider
     * @param optId
     * @param authEntityRepository
     * @param postChangeLogEntityRepository
     * @param orgLogEntityRepository
     * @throws Exception
     */
    public static Boolean authLoginPostChange(String optProvider, Integer optId,
                                              AuthEntityRepository authEntityRepository,
                                              PostChangeLogEntityRepository postChangeLogEntityRepository,
                                              OrgLogEntityRepository orgLogEntityRepository) {
        if (authEntityRepository != null && postChangeLogEntityRepository != null && orgLogEntityRepository != null &&
                apiSelectAuthLoginEntity(optProvider, optId, authEntityRepository) != null) {
            List<AuthEntity> listAuthEntity = authEntityRepository.getAllAuthEntityByObjType(0);

            if(listAuthEntity != null) {
                for (AuthEntity x : listAuthEntity) {
                    CRCCOrganizationDto position = apiGetUserMainPosition(x.getObjProvider(), x.getObjId());
                    if (position != null) {
                        String post_provider = position.getProvider();
                        Integer post_id = position.getId();
                        if (x.getObjProvider().compareTo(post_provider) != 0 || x.getObjPostId().compareTo(post_id) != 0) {
                            String post_name = position.getName();
                            String path_obj = apiGetOrganizationPathStr(post_provider, post_id, 1, true);
                            String path_node = apiGetOrganizationPathStr(x.getNodeProvider(), x.getNodeId(), 1, true);

                            String content = "授权对象岗位发生变化! " + "授权对象：" +
                                    x.getObjName() +
                                    "，原岗位：" +
                                    x.getObjPath() +
                                    "，现岗位：" +
                                    path_obj +
                                    "，授权节点：" +
                                    path_node;
                            PostChangeLogEntity postChangeLog = new PostChangeLogEntity(x.getNodeProvider(), x.getNodeId(), x.getNodeCode(), path_node, x.getNodeIndex(), content, x.getObjPath(), path_obj, x.getObjName());

                            x.setObjNode(post_id);
                            x.setObjPostId(post_id);
                            x.setObjPostName(post_name);
                            x.setObjPath(path_obj);
                            x.setNodePath(path_node);

                            authEntityRepository.updateAuthEntity(x);
                            postChangeLogEntityRepository.insertPostChangeLogEntity(postChangeLog);
                        }
                    }
                }
            }

            return true;
        }

        return false;
    }
    public static Boolean asyncAuthLoginPostChange(String optProvider, Integer optId,
                                                   AuthEntityRepository authEntityRepository,
                                                   PostChangeLogEntityRepository postChangeLogEntityRepository,
                                                   OrgLogEntityRepository orgLogEntityRepository, Boolean await) throws Exception {
        if (authEntityRepository != null && postChangeLogEntityRepository != null && orgLogEntityRepository != null &&
                apiSelectAuthLoginEntity(optProvider, optId, authEntityRepository) != null) {
            List<AuthEntity> listAuthEntity = authEntityRepository.getAllAuthEntityByObjType(0);

            if(listAuthEntity != null) {
                ExecutorService es = Executors.newCachedThreadPool();
                CountDownLatch latch = new CountDownLatch(listAuthEntity.size());

                for (AuthEntity x : listAuthEntity) {
                    es.submit(() -> {
                        try {
                            CRCCOrganizationDto position = apiGetUserMainPosition(x.getObjProvider(), x.getObjId());
                            if (position != null) {
                                String post_provider = position.getProvider();
                                Integer post_id = position.getId();
                                if (x.getObjProvider().compareTo(post_provider) != 0 || x.getObjPostId().compareTo(post_id) != 0) {
                                    String post_name = position.getName();
                                    String path_obj = apiGetOrganizationPathStr(post_provider, post_id, 1, true);
                                    String path_node = apiGetOrganizationPathStr(x.getNodeProvider(), x.getNodeId(), 1, true);

                                    String content = "授权对象岗位发生变化! " + "授权对象：" +
                                            x.getObjName() +
                                            "，原岗位：" +
                                            x.getObjPath() +
                                            "，现岗位：" +
                                            path_obj +
                                            "，授权节点：" +
                                            path_node;
                                    PostChangeLogEntity postChangeLog = new PostChangeLogEntity(x.getNodeProvider(), x.getNodeId(), x.getNodeCode(), path_node, x.getNodeIndex(), content, x.getObjPath(), path_obj, x.getObjName());

                                    x.setObjNode(post_id);
                                    x.setObjPostId(post_id);
                                    x.setObjPostName(post_name);
                                    x.setObjPath(path_obj);
                                    x.setNodePath(path_node);

                                    authEntityRepository.updateAuthEntity(x);
                                    postChangeLogEntityRepository.insertPostChangeLogEntity(postChangeLog);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    });
                }

                if(await) {
                    latch.await();
                }
                es.shutdown();
            }

            return true;
        }

        return false;
    }

    /**
     * 登录人员鉴权组织删除
     *
     * @param optProvider
     * @param optId
     * @param authEntityRepository
     * @param postChangeLogEntityRepository
     * @param orgLogEntityRepository
     * @return
     * @throws Exception
     */
    public static Boolean authLoginOrgDelete(String optProvider, Integer optId,
                                             AuthEntityRepository authEntityRepository,
                                             PostChangeLogEntityRepository postChangeLogEntityRepository,
                                             OrgLogEntityRepository orgLogEntityRepository) {
        if (authEntityRepository != null && postChangeLogEntityRepository != null && orgLogEntityRepository != null &&
                apiSelectAuthLoginEntity(optProvider, optId, authEntityRepository) != null) {
            List<AuthEntity> listAuthEntity = authEntityRepository.getAllAuthEntity();

            if(listAuthEntity != null) {
                for (AuthEntity x : listAuthEntity) {
                    String path_node = apiGetOrganizationPathStr(x.getNodeProvider(), x.getNodeId(), 1, true);
                    if (path_node == null) {
                        String name = CRCCOrganizationDto.findOrganizationNameFromPath(x.getNodePath(), -1);
                        path_node = apiGetOrganizationPathStr(x.getNodeParentProvider(), x.getNodeParentId(), 1, true) + "/" + name;

                        String content = "授权组织机构删除变化! " + "授权对象：" +
                                x.getObjName() +
                                "，授权节点：" +
                                path_node;

                        OrgLogEntity orgLog = new OrgLogEntity(x.getNodeParentProvider(), x.getNodeParentId(), x.getNodeParentCode(), path_node, x.getNodeIndex(), content, x.getObjName());

                        authEntityRepository.deleteAuthEntity(x);
                        orgLogEntityRepository.insertOrgLogEntity(orgLog);
                    }
                }
            }

            return true;
        }

        return false;
    }
    public static Boolean asyncAuthLoginOrgDelete(String optProvider, Integer optId,
                                                  AuthEntityRepository authEntityRepository,
                                                  PostChangeLogEntityRepository postChangeLogEntityRepository,
                                                  OrgLogEntityRepository orgLogEntityRepository, Boolean await) throws Exception {
        if (authEntityRepository != null && postChangeLogEntityRepository != null && orgLogEntityRepository != null &&
                apiSelectAuthLoginEntity(optProvider, optId, authEntityRepository) != null) {
            List<AuthEntity> listAuthEntity = authEntityRepository.getAllAuthEntity();

            if(listAuthEntity != null) {
                ExecutorService es = Executors.newCachedThreadPool();
                CountDownLatch latch = new CountDownLatch(listAuthEntity.size());

                for (AuthEntity x : listAuthEntity) {
                    es.submit(() -> {
                        try {
                            String path_node = apiGetOrganizationPathStr(x.getNodeProvider(), x.getNodeId(), 1, true);
                            if (path_node == null) {
                                String name = CRCCOrganizationDto.findOrganizationNameFromPath(x.getNodePath(), -1);
                                path_node = apiGetOrganizationPathStr(x.getNodeParentProvider(), x.getNodeParentId(), 1, true) + "/" + name;

                                String content = "授权组织机构删除变化! " + "授权对象：" +
                                        x.getObjName() +
                                        "，授权节点：" +
                                        path_node;

                                OrgLogEntity orgLog = new OrgLogEntity(x.getNodeParentProvider(), x.getNodeParentId(), x.getNodeParentCode(), path_node, x.getNodeIndex(), content, x.getObjName());

                                authEntityRepository.deleteAuthEntity(x);
                                orgLogEntityRepository.insertOrgLogEntity(orgLog);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    });
                }

                if(await) {
                    latch.await();
                }
                es.shutdown();
            }

            return true;
        }

        return false;
    }

    /**
     * 登录人员鉴权组织更新
     *
     * @param optProvider
     * @param optId
     * @param authEntityRepository
     * @return
     * @throws Exception
     */
    public static Boolean authLoginOrgUpdate(String optProvider, Integer optId,
                                             AuthEntityRepository authEntityRepository) {
        if (authEntityRepository != null && apiSelectAuthLoginEntity(optProvider, optId, authEntityRepository) != null) {
            List<AuthEntity> listAuthEntity = authEntityRepository.getAllAuthEntity();

            if(listAuthEntity != null) {
                for (AuthEntity x : listAuthEntity) {
                    apiUpdateAuthLoginEntity(x, authEntityRepository);
                }
            }

            return true;
        }

        return false;
    }
    public static Boolean asyncAuthLoginOrgUpdate(String optProvider, Integer optId,
                                                  AuthEntityRepository authEntityRepository, Boolean await) throws Exception {
        if (authEntityRepository != null && apiSelectAuthLoginEntity(optProvider, optId, authEntityRepository) != null) {
            List<AuthEntity> listAuthEntity = authEntityRepository.getAllAuthEntity();

            if(listAuthEntity != null) {
                ExecutorService es = Executors.newCachedThreadPool();
                CountDownLatch latch = new CountDownLatch(listAuthEntity.size());

                for (AuthEntity x : listAuthEntity) {
                    es.submit(() -> {
                        try {
                            apiUpdateAuthLoginEntity(x, authEntityRepository);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    });
                }

                if(await) {
                    latch.await();
                }
                es.shutdown();
            }

            return true;
        }

        return false;
    }


    /**
     * 获取任一组织节点人员集合 采用非实时请求
     *
     * @param providerId
     * @param orgId
     * @param orgType
     * @return
     * @throws Exception
     */
    public static CRCCOrganizationDto syncTreeOrganizationAndUsers(String providerId, Integer orgId, Integer orgType) throws Exception {
        if (providerId == null || providerId.length() == 0 || orgId == null || orgType == null) {
            return null;
        }

//        System.out.println("sync Tree Organization And Users Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        CRCCOrganizationDto organization;
        switch (orgType) {
            case -1:
                organization = apiGetCompanyRoot();
                if(organization != null) {
                    List<CRCCOrganizationDto> orgList = organization.getChildren();
                    if(orgList == null) {
                        orgList = apiGetOrganizationChildren(organization.getProvider(), organization.getId(), organization.getType());
                    }

                    if(orgList != null) {
                        List<CRCCOrganizationDto> _orgList = new ArrayList<>(orgList);
                        organization.setChildren(_orgList);

                        ExecutorService es = Executors.newCachedThreadPool();
                        CountDownLatch latch = new CountDownLatch(orgList.size());
                        for (int i=0; i<orgList.size(); i++) {
                            CRCCOrganizationDto org = orgList.get(i);
                            if (org != null) {
                                int finalI = i;

                                es.submit(() -> {
                                    try {
                                        if (org.getType() == 1) {
                                            apiAsyncGetOrganizationAndUsersTree(org.getProvider(), org.getId(), _orgList, finalI);
                                        } else {
                                            asyncQueueOrganizationChildren(org);
                                            _orgList.set(finalI, org);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        latch.countDown();
                                    }
                                });
                            }
                        }
                        latch.await();
                        es.shutdown();

                    } else {
                        asyncQueueOrganizationChildren(organization);
                    }

                    Thread.sleep(300);
                }
                break;
            case 0:
                organization = apiGetCompanyRoot();
                if(organization != null) {
                    List<CRCCOrganizationDto> orgList = organization.getChildren();
                    if(orgList == null) {
                        orgList = apiGetOrganizationChildren(organization.getProvider(), organization.getId(), organization.getType());
                    }

                    if(orgList != null) {
                        List<CRCCOrganizationDto> _orgList = new ArrayList<>(orgList);
                        organization.setChildren(_orgList);

                        ExecutorService es = Executors.newCachedThreadPool();
                        CountDownLatch latch = new CountDownLatch(orgList.size());
                        for (int i=0; i<orgList.size(); i++) {
                            CRCCOrganizationDto org = orgList.get(i);
                            if (org != null) {
                                int finalI = i;

                                es.submit(() -> {
                                    try {
                                        if (org.getType() == 1) {
                                            CRCCOrganizationDto _org = apiGetOrganizationAndUsersTree(org.getProvider(), org.getId());
                                            if(_org != null) {
                                                _orgList.set(finalI, _org);
                                            }
                                        } else {
                                            queueOrganizationChildren(org);
                                            _orgList.set(finalI, org);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        latch.countDown();
                                    }
                                });
                            }
                        }
                        latch.await();
                        es.shutdown();

                    } else {
                        queueOrganizationChildren(organization);
                    }
                }
                break;
            case 1:
                organization = apiGetOrganizationAndUsersTree(providerId, orgId);
                break;
            default:
                organization = apiGetOrganization(providerId, orgId, orgType);
                queueOrganizationChildren(organization);

                break;
        }

        initOrganizationAndUsersTree(organization);

//        System.out.println("sync Tree Organization And Users Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        return organization;
    }

    public static CRCCOrganizationDto asyncTreeOrganizationAndUsers(String providerId, Integer orgId, Integer orgType) throws Exception {
        if (providerId == null || providerId.length() == 0 || orgId == null || orgType == null) {
            return null;
        }

//        System.out.println("Async Tree Organization And Users Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        CRCCOrganizationDto organization;
        switch (orgType) {
            case -1:
                organization = apiGetCompanyRoot();
                if(organization != null) {
                    List<CRCCOrganizationDto> orgList = organization.getChildren();
                    if(orgList == null) {
                        orgList = apiGetOrganizationChildren(organization.getProvider(), organization.getId(), organization.getType());
                    }

                    if(orgList != null) {
                        List<CRCCOrganizationDto> _orgList = new ArrayList<>(orgList);
                        organization.setChildren(_orgList);
                        for (int i=0; i<orgList.size(); i++) {
                            CRCCOrganizationDto org = orgList.get(i);
                            if (org != null && org.getType() == 1) {
                                List<CRCCOrganizationDto> listOrg = apiGetOrganizationChildren(org.getProvider(), org.getId(), org.getType());
                                if(listOrg != null) {
                                    List<CRCCOrganizationDto> _listOrg = new ArrayList<>(listOrg);
                                    org.setChildren(_listOrg);
                                    for (int j = 0; j < listOrg.size(); j++) {
                                        CRCCOrganizationDto _org = listOrg.get(j);
                                        if (_org != null) {
                                            if (_org.getType() == 1) {
                                                apiAsyncGetOrganizationAndUsersTree(_org.getProvider(), _org.getId(), _listOrg, j);
                                            } else {
                                                asyncQueueOrganizationChildren(_org);
                                                _listOrg.set(j, _org);
                                            }
                                        }
                                    }
                                } else {
                                    asyncQueueOrganizationChildren(org);
                                }
                            }
                        }
                    }
                }
                break;
            case 0:
                organization = apiGetCompanyRoot();
                if(organization != null) {
                    List<CRCCOrganizationDto> orgList = organization.getChildren();
                    if(orgList == null) {
                        orgList = apiGetOrganizationChildren(organization.getProvider(), organization.getId(), organization.getType());
                    }

                    if(orgList != null) {
                        List<CRCCOrganizationDto> _orgList = new ArrayList<>(orgList);
                        organization.setChildren(_orgList);
                        for (int i=0; i<orgList.size(); i++) {
                            CRCCOrganizationDto org = orgList.get(i);
                            if (org != null) {
                                if (org.getType() == 1) {
                                    apiAsyncGetOrganizationAndUsersTree(org.getProvider(), org.getId(), _orgList, i);
                                } else {
                                    asyncQueueOrganizationChildren(org);
                                    _orgList.set(i, org);
                                }
                            }
                        }
                    } else {
                        asyncQueueOrganizationChildren(organization);
                    }
                }
                break;
            case 1:
                organization = apiGetOrganization(providerId, orgId, orgType);
                List<CRCCOrganizationDto> orgList = apiGetOrganizationChildren(providerId, orgId, orgType);
                if(orgList != null) {
                    List<CRCCOrganizationDto> _orgList = new ArrayList<>(orgList);
                    organization.setChildren(_orgList);
                    for (int i = 0; i < orgList.size(); i++) {
                        CRCCOrganizationDto org = orgList.get(i);
                        if (org != null) {
                            if (org.getType() == 1) {
                                apiAsyncGetOrganizationAndUsersTree(org.getProvider(), org.getId(), _orgList, i);
                            } else {
                                ExecutorService es = Executors.newCachedThreadPool();
                                int finalI = i;
                                es.submit(() -> {
                                    try {
                                        asyncQueueOrganizationChildren(org);
                                        _orgList.set(finalI, org);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                                es.shutdown();
                            }
                        }
                    }
                } else {
                    asyncQueueOrganizationChildren(organization);
                }
                break;
            default:
                organization = apiGetOrganization(providerId, orgId, orgType);
                asyncQueueOrganizationChildren(organization);
                break;
        }

//        System.out.println("Async Tree Organization And Users Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        if(orgType == 0) {
            Thread.sleep(2000);
        } else {
            Thread.sleep(1000);
        }

        initOrganizationAndUsersTree(organization);
//        System.out.println("Async Init Tree Organization And Users Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        return organization;
    }

    public static void initOrganizationAndUsersTree(CRCCOrganizationDto organization) {
//        System.out.println("Init Organization Level And Sort begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        if (organization != null) {
            organization.setLevel(1);
            organization.setIndex(0);

            JSONObject company = new JSONObject();
            JSONObject department = new JSONObject();
            company.put("provider", CRCCConstants.NODE_PROVIDER);
            company.put("id", CRCCConstants.NODE_ID);
            company.put("name", CRCCConstants.NODE_NAME);

            CRCCOrganizationDto.initIndex();
            CRCCOrganizationDto.initOrganization(organization, company, department);
        }

//        System.out.println("Init Organization Level And Sort end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
    }

    public static void queueOrganizationChildren(CRCCOrganizationDto organization) {
        if (organization != null) {
            List<CRCCOrganizationDto> orgChildren = apiGetOrganizationChildren(organization.getProvider(), organization.getId(), organization.getType());
            if (orgChildren != null) {
                organization.setChildren(orgChildren);
                for (CRCCOrganizationDto x : orgChildren) {
                    if (x.getProvider() == null) {
                        x.setProvider(organization.getProvider());
                    }

                    if (x.getType() < 3) {
                        queueOrganizationChildren(x);
                    } else if (x.getType() == 3) {
                        List<CRCCUserInfoDto> list = apiGetPositionUsers(x.getProvider(), x.getId());
                        if(list != null && !list.isEmpty()) {
                            x.setUsers(list);
                        }
                    }
                }
            }
        }
    }

    public static void asyncQueueOrganizationChildren(CRCCOrganizationDto organization) {
        if (organization != null) {
            List<CRCCOrganizationDto> orgChildren = apiGetOrganizationChildren(organization.getProvider(), organization.getId(), organization.getType());
            if (orgChildren != null) {
                organization.setChildren(orgChildren);
                for (CRCCOrganizationDto x : orgChildren) {
                    if (x.getProvider() == null) {
                        x.setProvider(organization.getProvider());
                    }

                    if (x.getType() < 3) {
                        asyncQueueOrganizationChildren(x);
                    } else if (x.getType() == 3) {
                        apiAsyncGetPositionUsers(x.getProvider(), x.getId(), x);
                    }
                }
            }
        }
    }

    public static List<CRCCUserInfoDto> syncInitUserInfoList(String providerId, Integer orgId) {
        if (providerId == null || providerId.length() == 0 || orgId == null) {
            return null;
        }

        if(crccOrganizationDto != null) {
            return CRCCOrganizationDto.findOrganizationUsers(crccOrganizationDto);
        }

        return null;
    }

    public static List<CRCCUserInfoDto> syncInitUserInfoList(String providerId, Integer orgId, AuthEntity authEntity) {
        if (providerId == null || providerId.length() == 0 || orgId == null) {
            return null;
        }

        if(crccOrganizationDto != null && authEntity != null) {
            if(orgId.compareTo(CRCCConstants.NODE_ID) == 0) {
                if(authEntity.getNodeId().compareTo(CRCCConstants.NODE_ID) == 0) {
                    return CRCCOrganizationDto.findOrganizationUsers(crccOrganizationDto);
                } else {
                    CRCCOrganizationDto org = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, authEntity.getNodeProvider(), authEntity.getNodeId());

                    return CRCCOrganizationDto.findOrganizationUsers(org);
                }
            } else {
                CRCCOrganizationDto org = CRCCOrganizationDto.findOrganizationById(crccOrganizationDto, providerId, orgId);

                return CRCCOrganizationDto.findOrganizationUsers(org);
            }
        }

        return null;
    }

    public static void syncInitOrganizationAndUsers(String providerId, Integer orgId, Integer orgType, AuthEntity authEntity) {
        if (providerId == null || providerId.length() == 0 || orgId == null || orgType == null) {
            return;
        }

//        System.out.println("sync Init Organization And Users Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        ExecutorService es = Executors.newCachedThreadPool();
        es.submit(() -> {
            try {
                crccOrganizationDto = syncTreeOrganizationAndUsers(CRCCConstants.NODE_PROVIDER, CRCCConstants.NODE_ID, CRCCConstants.NODE_TYPE);;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        es.shutdown();

        crccUserInfoDtoList = syncInitUserInfoList(providerId, orgId, authEntity);

//        System.out.println("sync Init Organization And Users Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////   timer接口   //////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static void timerSyncTreeOrganizationAndUsers(long initialDelay, long period) {
        if(timerOrganization != null) {
            try {
                timerOrganization.shutdown();
                if(!timerOrganization.awaitTermination(5*initialDelay, TimeUnit.MILLISECONDS)){
                    timerOrganization.shutdownNow();
                }
            } catch (InterruptedException e) {
                timerOrganization.shutdownNow();
            }
        }

        timerOrganization = Executors.newSingleThreadScheduledExecutor();

        final CRCCOrganizationDto[] organization = {new CRCCOrganizationDto()};
        TimerTask timerTask = new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                try {
                    organization[0] = syncTreeOrganizationAndUsers(CRCCConstants.NODE_PROVIDER, CRCCConstants.NODE_ID, CRCCConstants.NODE_TYPE);
                } catch (InterruptedException ex) {
                    ex.printStackTrace(System.err);
                } finally {
                    crccOrganizationDto = organization[0].clone();
                    List<CRCCUserInfoDto> list = CRCCOrganizationDto.findOrganizationUsers(crccOrganizationDto);
//                    System.out.println("users count: " + list.size());
//                    System.out.println("Timer Tree Organization And Users Task.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
                }
            }
        };

        // 延时 1 秒后，按 3 秒的周期执行任务
        timerOrganization.scheduleAtFixedRate(timerTask, initialDelay, period, TimeUnit.MILLISECONDS);
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////   thread接口   //////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static CRCCOrganizationDto threadOrganizationAndUsers() {
//        System.out.println("Thread Get Organization And Users Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        CRCCOrganizationDto organization = apiGetCompanyRoot();

        if (organization != null) {
            organization.setLevel(1);

            JSONObject company = new JSONObject();
            company.put("provider", CRCCConstants.NODE_PROVIDER);
            company.put("id", CRCCConstants.NODE_ID);
            company.put("name", CRCCConstants.NODE_NAME);

            CRCCOrganizationDto.initIndex();
            threadOrganizationChildren(organization, company, company);
        }

//        System.out.println("Thread Get Organization And Users Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        return organization;
    }

    public static void threadOrganizationChildren(CRCCOrganizationDto organization, JSONObject company, JSONObject department) {
        if (organization == null || company == null || department == null) {
            return;
        }

        String providerId = organization.getProvider();
        Integer orgId = organization.getId();
        Integer orgType = organization.getType();
        Integer level = organization.getLevel();
        String name = organization.getName();
        Boolean virtual = organization.getVirtual();

        JSONObject _company = CloneUtils.cloneTo(company);
        JSONObject _department = CloneUtils.cloneTo(department);
        if(orgType == 1 && !virtual) {
            _company.put("provider", providerId);
            _company.put("id", orgId);
            _company.put("name", name);
        } else if(orgType == 2) {
            _department.put("provider", providerId);
            _department.put("id", orgId);
            _department.put("name", name);
        }

        List<CRCCOrganizationDto> orgChildren = apiGetOrganizationChildren(providerId, orgId, orgType);
        if (orgChildren != null) {
            orgChildren.sort(Comparator.comparing(CRCCOrganizationDto::getOrder));
            organization.setChildren(orgChildren);

            for (CRCCOrganizationDto x : orgChildren) {
                ExecutorService es = Executors.newCachedThreadPool();
                es.submit(() -> {
                    try {
                        x.setLevel(level + 1);
                        x.setIndex(CRCCOrganizationDto.orgIndex++);

                        if (x.getProvider() == null) {
                            x.setProvider(organization.getProvider());
                        }

                        if (x.getType() < 3) {
                            threadOrganizationChildren(x, _company, _department);
                        } else if (x.getType() == 3) {
                            JSONObject _position = new JSONObject();
                            _position.put("provider", x.getProvider());
                            _position.put("id", x.getId());
                            _position.put("name", x.getName());

                            List<CRCCUserInfoDto> listUsers = new ArrayList<>();
                            x.setUsers(listUsers);

                            ExecutorService _es = Executors.newCachedThreadPool();
                            _es.submit(() -> {
                                try {
                                    List<CRCCUserInfoDto> _listUsers = apiGetPositionUsers(providerId, x.getId());
                                    if (_listUsers != null) {
                                        _listUsers.sort(Comparator.comparing(CRCCUserInfoDto::getOrder));
                                        listUsers.addAll(_listUsers);
                                        for (CRCCUserInfoDto y : listUsers) {
                                            y.set(CRCCOrganizationDto.userIndex++, x.getProvider(), _company, _department, _position);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });

                            _es.shutdown();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                es.shutdown();
            }
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////   test接口   //////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    public void testThread() {
        tokenHolderInit();

        System.out.println("HR Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
        TokenHolder.getOrgApi(accessTokenDto, "/tree/hr/986436/users");
        System.out.println("HR Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        System.out.println("HR11 Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
        TokenHolder.getOrgApi(accessTokenDto, "/tree/hr11/1/users");
        System.out.println("HR11 Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        System.out.println("HR19 Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
        TokenHolder.getOrgApi(accessTokenDto, "/tree/hr19/1/users");
        System.out.println("HR19 Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        System.out.println("CSCRCC11N Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
        TokenHolder.getOrgApi(accessTokenDto, "/tree/CSCRCC11N/1/users");
        System.out.println("CSCRCC11N Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        System.out.println("1111111111111111111111111111111: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));


        System.out.println("HR Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
        TokenHolder.getOrgApi(accessTokenDto, "/tree/hr/986436/users");
        System.out.println("HR Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        System.out.println("HR11 Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
        TokenHolder.getOrgApi(accessTokenDto, "/tree/hr11/1/users");
        System.out.println("HR11 Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        System.out.println("HR19 Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
        TokenHolder.getOrgApi(accessTokenDto, "/tree/hr19/1/users");
        System.out.println("HR19 Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        System.out.println("CSCRCC11N Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
        TokenHolder.getOrgApi(accessTokenDto, "/tree/CSCRCC11N/1/users");
        System.out.println("CSCRCC11N Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));


        System.out.println("1111111111111111111111111111111: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));


        System.out.println("HR Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
        TokenHolder.getOrgApi(accessTokenDto, "/tree/hr/986436/users");
        System.out.println("HR Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        System.out.println("HR11 Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
        TokenHolder.getOrgApi(accessTokenDto, "/tree/hr11/1/users");
        System.out.println("HR11 Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        System.out.println("HR19 Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
        TokenHolder.getOrgApi(accessTokenDto, "/tree/hr19/1/users");
        System.out.println("HR19 Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        System.out.println("CSCRCC11N Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
        TokenHolder.getOrgApi(accessTokenDto, "/tree/CSCRCC11N/1/users");
        System.out.println("CSCRCC11N Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

    }

    @Test
    public void testSearch() throws Exception {
        tokenHolderInit();

        List<CRCCUserInfoDto> list = apiSearchUserInfo(CRCCConstants.NODE_PROVIDER, CRCCConstants.NODE_ID, CRCCConstants.NODE_TYPE,"窦");
        System.out.println("users count: " + crccUserInfoDtoList.size());
    }

    @Test
    public void testAsync() throws Exception {
        tokenHolderInit();

        CRCCOrganizationDto org = asyncTreeOrganizationAndUsers(CRCCConstants.NODE_PROVIDER, CRCCConstants.NODE_ID, CRCCConstants.NODE_TYPE);
        System.out.println("org count: " + org.getChildren().size());
    }

}