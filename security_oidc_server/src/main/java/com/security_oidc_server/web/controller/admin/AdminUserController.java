package com.security_oidc_server.web.controller.admin;

import com.security_oidc_server.service.UserService;
import com.security_oidc_server.service.dto.UserFormDto;
import com.security_oidc_server.service.dto.UserListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户[EU] 管理
 *
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-03-02 9:09
 */
@Controller
@RequestMapping("/admin/user/")
public class AdminUserController {


    @Autowired
    private UserService userService;


    /**
     * 用户列表
     */
    @GetMapping("list")
    public String list(UserListDto listDto, Model model) {
        listDto = userService.loadUserListDto(listDto);
        model.addAttribute("listDto", listDto);
        return "admin/user_list";
    }


    /**
     * 用户表单
     */
    @GetMapping(value = "form/plus")
    public String showForm(Model model) {
        model.addAttribute("formDto", new UserFormDto());
        return "admin/user_form";
    }


    /**
     * 用户表单
     */
    @PostMapping(value = "form/plus")
    public String submitRegisterClient(@ModelAttribute("formDto") @Valid UserFormDto formDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/user_form";
        }
        String uuid = userService.saveUserForm(formDto);
        model.addAttribute("uuid", uuid).addAttribute("alert", "SaveOK");
        return "redirect:../list";
    }


    /**
     * archive
     */
    @GetMapping("archive/{uuid}")
    public String archive(@PathVariable String uuid, Model model) {
        boolean ok = userService.archiveUserByUuid(uuid);
        model.addAttribute("alert", ok ? "ArchivedOK" : "ArchivedFailed");
        return "redirect:../list";
    }


}
