package com.provider_oidc_login.web.controller;

import com.provider_oidc_login.entity.Login;
import com.provider_oidc_login.service.LoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-10-30 7:56
 */
@RestController
@RequestMapping("/login-api")
public class LoginController {
    @Resource
    private LoginService loginService;

    /**
     * 插入用户对象
     *
     * @param login 用户对象
     * @return int变量
     */
    @PostMapping(value = "/add-login")
    public int addLogin (Login login) {
        Login _login = loginService.getLoginByMaxId();
        if(login != null) {
            String name = login.getName();
            String username = login.getUsername();
            String password = login.getPassword();
            if(_login == null) {
                _login = new Login(1,"","","");
            }
            _login.setId(_login.getId()+1);
            _login.setName(name);
            _login.setUsername(username);
            _login.setPassword(password);
            return loginService.insertLogin(_login);
        }

        return -1;
    }

    /**
     * 更新用户对象
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping(value = "/test-login")
    public String setLogin (String username, String password) {
        return "Login Success username= "+username+"  password= "+password;
    }

    /**
     * 更新用户对象
     *
     * @param login 用户对象
     * @return int变量
     */
    @PostMapping(value = "/set-login")
    public int setLogin (Login login) {
        if(login != null) {
            Login _login = loginService.getLoginByUsername(login.getUsername());
            _login.setName(login.getName());
            _login.setUsername(login.getUsername());
            _login.setPassword(login.getPassword());
            return loginService.setLoginById(_login);
        }
        return -1;
    }

    /**
     * 删除用户对象
     *
     * @param username 用户名
     * @return int变量
     */
    @GetMapping(value = "/del-login")
    public int delLogin (String username) {
        Login login = loginService.getLoginByUsername(username);
        if(login != null) {
            return loginService.delLoginById(login.getId());
        }
        return -1;
    }

    /**
     *  获取所有用户对象
     * @return List用户对象列表
     * */
    @GetMapping(value = "/get-all-login")
    public List<Login> getAllLogin () {
        return loginService.getAllLogin();
    }

    /**
     *  获取用户数量
     * @return long变量
     * */
    @GetMapping(value = "/count-all-login")
    public long countAllLogin () {
        return loginService.countAllLogin();
    }

    /**
     * 获取用户对象,通过用户名
     *
     * @param username 用户名
     * @return Login用户对象
     */
    @GetMapping(value = "/get-login-by-username")
    public Login getLoginByUsername (String username) {
        return loginService.getLoginByUsername(username);
    }

    /**
     * 获取用户对象,通过用户类别
     *
     * @param name 用户类别
     * @return List用户对象列表
     */
    @GetMapping(value = "/get-login-by-name")
    public List<Login> getLoginByName (String name) {
        return loginService.getLoginByName(name);
    }

    /**
     * 获取用户对象,通过用户ID
     *
     * @param id 用户id
     * @return Login用户对象
     */
    @GetMapping(value = "/get-login-by-id")
    public Login getLoginById (Integer id) {
        return loginService.getLoginById(id);
    }
}
