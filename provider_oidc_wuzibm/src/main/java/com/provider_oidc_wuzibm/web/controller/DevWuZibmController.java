package com.provider_oidc_wuzibm.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.provider_oidc_wuzibm.domain.DateUtils;
import com.provider_oidc_wuzibm.domain.RPHolder;
import com.provider_oidc_wuzibm.domain.shared.Application;
import com.provider_oidc_wuzibm.entity.AuthEntityRepository;
import com.provider_oidc_wuzibm.entity.OrgLogEntityRepository;
import com.provider_oidc_wuzibm.entity.PortalData;
import com.provider_oidc_wuzibm.entity.PostChangeLogEntityRepository;
import com.provider_oidc_wuzibm.service.OIDCClientService;
import com.provider_oidc_wuzibm.service.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

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
@RequestMapping("/wuzibm")
public class DevWuZibmController {
    @Autowired
    private OIDCClientService clientService;

    @Autowired
    private AuthEntityRepository authEntityRepository;

    @Autowired
    private PostChangeLogEntityRepository postChangeLogEntityRepository;

    @Autowired
    private OrgLogEntityRepository orgLogEntityRepository;


    /**
     * 门户对接，应用聚合
     *
     * @return
     */
    @GetMapping(value = "crcc-menhu/v1.0/accessible")
    public @ResponseBody PortalData accessible(String sub) throws Exception {
        System.out.println(sub);
        UserInfoDto userInfoDto = new UserInfoDto(sub);

        PortalData portalData = new PortalData();
        portalData.setStatus("C00000");
        portalData.setMsg("操作成功，门户有权访问该应用");
        JSONObject json = new JSONObject();
        json.put("accessible", true);
        portalData.setData(json);

        String providerId = userInfoDto.getProviderId();
        Integer userId = userInfoDto.getUserId();
        if(providerId != null && providerId.length() > 0 && userId != null) {
            RPHolder rpHolder = Application.getApiRPHolder();

            if(rpHolder.isConfigRP()) {
                if (VueRetController.asyncAuthLoginOrgDelete(providerId, userId, authEntityRepository, postChangeLogEntityRepository, orgLogEntityRepository, true)) {
                    VueRetController.asyncAuthLoginPostChange(providerId, userId, authEntityRepository, postChangeLogEntityRepository, orgLogEntityRepository, false);
                    rpHolder.setClientUserAuth("has_auth");
                } else {
                    rpHolder.setClientUserAuth("no_auth");
                }

                System.out.println("accessible:  "+rpHolder.getClientUserAuth());

                rpHolder.setClientUserType("portal");
                rpHolder.setClientProviderId(providerId);
                rpHolder.setClientUserId(userId.toString());
                clientService.saveRPHolder(rpHolder);
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
    @GetMapping(value = "web/pc")
    public RedirectView webPc(final RedirectAttributes redirectAttributes) {
        System.out.println("RedirectView " + Application.host() + " Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        RPHolder rpHolder = Application.getApiRPHolder();

        //异常跳转回主页
        if (!rpHolder.isConfigRP()) {
            // 以Post方式传参   addFlashAttribute， 以 Get方式传参 addAttribute
            redirectAttributes.addAttribute("auth", "auth_fail");
            return new RedirectView("redirect:/");
        }

        if (rpHolder.getClientUserType().equals("portal") && rpHolder.getClientUserAuth().equals("has_auth")) {
            redirectAttributes.addAttribute("auth", "has_auth");
            redirectAttributes.addAttribute("providerId", rpHolder.getClientProviderId());
            redirectAttributes.addAttribute("userId", rpHolder.getClientUserId());
        } else {
            redirectAttributes.addAttribute("auth", "no_auth");
        }

        System.out.println("web/pc:  "+rpHolder.getClientUserAuth());

        List<String> uris = rpHolder.getVueRedirectUris();
        for(String x: uris) {
            if(x.contains(Application.ip())) {
                System.out.println("RedirectView " + x + " Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
                return new RedirectView(x);
            }
        }

        return new RedirectView("redirect:/");
    }

    /**
     * 门户对接，应用聚合入口
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @GetMapping(value = "web/app")
    public RedirectView webApp(final RedirectAttributes redirectAttributes) {
        System.out.println("RedirectView " + Application.host() + " Task begin.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));

        RPHolder rpHolder = Application.getApiRPHolder();

        //异常跳转回主页
        if (!rpHolder.isConfigRP()) {
            // 以Post方式传参   addFlashAttribute， 以 Get方式传参 addAttribute
            redirectAttributes.addAttribute("auth", "auth_fail");
            return new RedirectView("redirect:/");
        }

        if (rpHolder.getClientUserType().equals("portal") && rpHolder.getClientUserAuth().equals("has_auth")) {
            redirectAttributes.addAttribute("auth", "has_auth");
            redirectAttributes.addAttribute("providerId", rpHolder.getClientProviderId());
            redirectAttributes.addAttribute("userId", rpHolder.getClientUserId());
        } else {
            redirectAttributes.addAttribute("auth", "no_auth");
        }

        List<String> uris = rpHolder.getVueRedirectUris();
        for(String x: uris) {
            if(x.contains(Application.ip())) {
                System.out.println("RedirectView " + x + " Task end.  time: " + DateUtils.formatCurrentLocalDateTime(DateUtils.DATETIME_FORMAT.HOUR_MILLIS));
                return new RedirectView(x);
            }
        }

        return new RedirectView("redirect:/");
    }

    /**
     * 门户对接，待办聚合
     *
     * @return
     */
    @GetMapping(value = "crcc-menhu/v1.0/todoList")
    public @ResponseBody PortalData todoList(String sub, int cat) {
        System.out.println(sub);
        System.out.println(cat);

        PortalData portalData = new PortalData();
        portalData.setStatus("C00000");
        portalData.setMsg("操作成功，用户有权访问该应用待办事项");
        JSONObject json = new JSONObject();
        json.put("url", "http://ldsc.cr121.com:12175/wuzibm/todoList");
        json.put("todoCategory", 2);
        json.put("todoNumber", 4);
        json.put("reserveExtensions", new JSONObject());
        json.put("totalNum", 4);
        portalData.setData(json);

        return portalData;
    }

    /**
     * 门户对接，待办聚合
     *
     * @return
     */
    @GetMapping(value = "crcc-menhu/v1.0/presses")
    public @ResponseBody PortalData presses(String kwds, int type) {
        System.out.println(kwds);
        System.out.println(type);

        PortalData portalData = new PortalData();
        portalData.setStatus("C00000");
        portalData.setMsg("操作成功，疫情防控消息推送成功");
        JSONObject json = new JSONObject();
        json.put("column", "疫情防控");
        json.put("more", "http://ldsc.cr121.com:12175/wuzibm/message");
        json.put("items", new JSONArray());
        portalData.setData(json);

        return portalData;
    }

}