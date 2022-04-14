package com.provider_oidc_ldsc.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.provider_oidc_ldsc.domain.*;
import com.provider_oidc_ldsc.domain.shared.Application;
import com.provider_oidc_ldsc.entity.*;
import com.provider_oidc_ldsc.service.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
@RequestMapping("/ldsc")
public class DevLdscController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AuthEntityRepository authEntityRepository;

    /**
     * 门户对接，应用聚合
     *
     * @return
     */
    @GetMapping(value = "crcc-menhu/v1.0/accessible")
    public PortalData accessible(String sub) {
//        System.out.println("Portal Accessible Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
//        System.out.println(sub);

        UserInfoDto userInfoDto = new UserInfoDto(sub);

        String providerId = userInfoDto.getProviderId();
        Integer userId = userInfoDto.getUserId();
        PortalData portalData = new PortalData();

        if(providerId != null && providerId.length() > 0 && userId != null) {
            if (VueRetController.authLoginEntity(providerId, userId, authEntityRepository) != null) {
                portalData.setStatus("C00000");
                portalData.setMsg("操作成功，门户有权访问该应用");
                JSONObject json = new JSONObject();
                json.put("accessible", true);
                portalData.setData(json);

//                System.out.println("Portal Accessible Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
            } else {
                portalData.setStatus("C00001");
                portalData.setMsg("操作成功，门户没有有权访问该应用");
                JSONObject json = new JSONObject();;
                json.put("accessible", false);
                portalData.setData(json);
            }
        }

        return portalData;
    }

    /**
     * 门户对接，应用聚合入口
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @GetMapping(value = {"web/pc", "web/app", "web/todo", "web/presses"})
    public RedirectView webPortal(final RedirectAttributes redirectAttributes) {
//        System.out.println("Redirect Login Portal Pc Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        if (request != null) {
            String path = request.getServletPath();
            if(path != null) {
                if (path.equals("/ldsc/web/todo")) {
                    PortalData.setType("todo");
                } else if (path.equals("/ldsc/web/presses")) {
                    String query = request.getQueryString();
                    if (query != null) {
                        String code = query.substring(query.indexOf("=") + 1);
                        PortalData.setCode(code);
                        PortalData.setType("presses");
                    }
                } else {
                    PortalData.setType("sso");
                }
            }

            RPHolder rpHolder = Application.getApiRPHolder();

            //异常跳转回主页
            if (!rpHolder.isConfigRP()) {
                // 以Post方式传参   addFlashAttribute， 以 Get方式传参 addAttribute
                redirectAttributes.addAttribute("auth", "auth_fail");
                return new RedirectView("redirect:/");
            }

            List<String> uris = rpHolder.getVueRedirectUris();
            for (String x : uris) {
                if (x.contains(Application.ip())) {
                    return new RedirectView(x);
                }
            }
        }

        return new RedirectView("redirect:/");
    }

    /**
     * 门户对接，待办聚合
     *
     * @return
     */
    @GetMapping(value = "todo/crcc-menhu/v1.0/todoList")
    public PortalData todoList(String sub, int cat) {
//        System.out.println("Portal Todo List Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        System.out.println(sub);
        System.out.println(cat);

        UserInfoDto userInfoDto = new UserInfoDto(sub);
        String providerId = userInfoDto.getProviderId();
        Integer userId = userInfoDto.getUserId();
        PortalData portalData = new PortalData("操作成功，应用没有任何待办事项");

        if(Application.hasAuth(providerId, userId)) {
            TodoEntity todo = PortalData.getToto();
            if(todo != null && todo.getTodoNumber() > 0) {
                portalData.setTodo(todo);
            } else {
                portalData.setTodo(null);
            }
        }

//        System.out.println("Portal Todo List Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
        return portalData;
    }

    /**
     * 门户对接，消息聚合
     *
     * @return
     */
    @GetMapping(value = "info/crcc-menhu/v1.0/presses")
    public PortalData presses(String sub, String kwds, int type) {
//        System.out.println("Portal Presses Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

//        System.out.println(sub);
//        System.out.println(kwds);
//        System.out.println(type);

        UserInfoDto userInfoDto = new UserInfoDto(sub);
        String providerId = userInfoDto.getProviderId();
        Integer userId = userInfoDto.getUserId();
        PortalData portalData = new PortalData("操作成功，应用没有任何聚合信息");

        if(Application.hasAuth(providerId, userId)) {
            PressesEntity presses = PortalData.getPresses();
            if(presses != null && presses.findDataUnreadItems(false) != null) {
                portalData.setPresses(presses);
            } else {
                portalData.setPresses(null);
            }
        }

//        System.out.println("Portal Presses Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
        return portalData;
    }
}