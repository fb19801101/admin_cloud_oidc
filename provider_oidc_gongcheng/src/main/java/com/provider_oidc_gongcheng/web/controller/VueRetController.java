package com.provider_oidc_gongcheng.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.provider_oidc_gongcheng.domain.DateUtils;
import com.provider_oidc_gongcheng.domain.JwtUtils;
import com.provider_oidc_gongcheng.domain.RPHolder;
import com.provider_oidc_gongcheng.domain.TokenHolder;
import com.provider_oidc_gongcheng.domain.auth.AuthRole;
import com.provider_oidc_gongcheng.domain.auth.AuthRoleDetails;
import com.provider_oidc_gongcheng.domain.auth.AuthRoleDetailsRepository;
import com.provider_oidc_gongcheng.domain.shared.Application;
import com.provider_oidc_gongcheng.entity.*;
import com.provider_oidc_gongcheng.service.OIDCClientService;
import com.provider_oidc_gongcheng.service.dto.*;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import org.jose4j.jwk.RsaJsonWebKey;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////   private static Value  ///////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static LocalDateTime invalidTokenDateTime;

    private static AccessTokenDto accessTokenDto;

    private static CRCCOrganizationDto crccOrganizationDto;

    private static List<CRCCUserInfoDto> crccUserInfoDtoList;

    private static ScheduledExecutorService timerOrganization;


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
         * hr 甘忠忠 userProvider
         */
        String ADMIN_PROVIDER = "hr";

        /**
         * hr 甘忠忠 userID
         */
        Integer ADMIN_ID = 364035;

        /**
         * hr 甘忠忠 userName
         */
        String ADMIN_NAME = "甘忠忠";
    }

    public VueRetController() {}

    /**
     * 验证 AccessToKen
     * @return
     */
    public boolean checkAccessToken() {
        try {
            RsaJsonWebKey key = JwtUtils.generalKeyFromUri("https://regtest.crcc.cn/discovery/certs", "b8fd74c8-acbc-4778-afa9-7564daeea73f");

            if (key != null && request != null) {
                AccessTokenDto accessToken = JSON.parseObject(request.getHeader("Authorization"), AccessTokenDto.class);
                if(accessToken != null) {
                    Claims claims = JwtUtils.parserJwt(key.getRsaPublicKey(), accessToken.getAccessToken());
                    return claims != null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("JWT Verifier AccessToken Exception: checkAccessToken --> " + e.getMessage());
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
     * 应用API登录
     *
     * @return
     */
    @GetMapping("api_login")
    public ResultData<String> apiLogin() {
        if (accessTokenDto == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), "AccessToken Failed", 1);
        }

        AccessTokenDto token = TokenHolder.loadAccessToken(Application.getApiRPHolder());

        if(token == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), "AccessToken Failed", 1);
        }

        String ret = TokenHolder.getStringApi(token, "http://ldsc.cr121.com:12198/","login","all-api");

        if(ret == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), "api-gongcheng call login failed", 1);
        }

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), ret, 1);
    }

    /**
     * 应用API调用
     *
     * @return
     */
    @GetMapping("api_call")
    public ResultData<String> apiCall() {
        if (accessTokenDto == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), "AccessToken Failed", 1);
        }

        AccessTokenDto token = TokenHolder.loadAccessToken(Application.getApiRPHolder());

        if(token == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), "AccessToken Failed", 1);
        }

        String ret = TokenHolder.getStringApi(token, "http://ldsc.cr121.com:12198/","echo","all-api");

        if(ret == null) {
            return new ResultData<>(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage(), "api-gongcheng call echo failed", 1);
        }

        return new ResultData<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), ret, 1);
    }

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
     * Token工具初始化
     *
     * @throws Exception
     */
    public static void tokenHolderInit() {
        RPHolder rpHolder = Application.getApiRPHolder();
        accessTokenDto = TokenHolder.loadAccessToken(rpHolder);

        LocalDateTime localDateTimeNow = LocalDateTime.now();
        System.out.println("AccessToken Current DateTime: " + DateUtils.formatLocalDateTime(localDateTimeNow, DateUtils.DATETIME_FORMAT.YEAR_SECONDS));
        Long milliSecond = DateUtils.convertLocalDateTimeToTimestamp(localDateTimeNow);
        milliSecond = milliSecond + (3600L * 1000L * 24L);
        invalidTokenDateTime = DateUtils.convertTimestampToLocalDateTime(milliSecond);
        System.out.println("AccessToken Invalid DateTime: " + DateUtils.formatLocalDateTime(invalidTokenDateTime, DateUtils.DATETIME_FORMAT.YEAR_SECONDS));
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

        System.out.println("sync Tree Organization And Users Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

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

        System.out.println("sync Tree Organization And Users Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        return organization;
    }

    public static CRCCOrganizationDto asyncTreeOrganizationAndUsers(String providerId, Integer orgId, Integer orgType) throws Exception {
        if (providerId == null || providerId.length() == 0 || orgId == null || orgType == null) {
            return null;
        }

        System.out.println("Async Tree Organization And Users Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

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

        System.out.println("Async Tree Organization And Users Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        if(orgType == 0) {
            Thread.sleep(2000);
        } else {
            Thread.sleep(1000);
        }

        initOrganizationAndUsersTree(organization);
        System.out.println("Async Init Tree Organization And Users Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        return organization;
    }

    public static void initOrganizationAndUsersTree(CRCCOrganizationDto organization) {
        System.out.println("Init Organization Level And Sort begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

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

        System.out.println("Init Organization Level And Sort end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
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

        System.out.println("sync Init Organization And Users Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

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

        System.out.println("sync Init Organization And Users Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
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
                System.out.println("users count: " + list.size());
                System.out.println("Timer Tree Organization And Users Task.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
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
        System.out.println("Thread Get Organization And Users Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

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

        System.out.println("Thread Get Organization And Users Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

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