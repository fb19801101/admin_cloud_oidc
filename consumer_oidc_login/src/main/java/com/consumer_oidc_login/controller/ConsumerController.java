package com.consumer_oidc_login.controller;

import com.alibaba.fastjson.JSON;
import com.provider_oidc_login.entity.Login;
import com.provider_oidc_login.entity.ResultData;
import com.consumer_oidc_login.service.ProviderService;
import com.provider_oidc_login.service.dto.AccessTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-10-30 7:56
 */
//@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/login")
public class ConsumerController {
    @Resource
    private ProviderService providerService;

    @Autowired
    private HttpServletRequest request;

    public ConsumerController() {}

    /**
     * 验证 AccessToKen
     * @return
     */
    public boolean checkToken() {
        AccessTokenDto accessToken = JSON.parseObject(request.getHeader("Authorization"), AccessTokenDto.class);

        if(accessToken == null) {
            return true;
        }

        return false;
    }

    /**
     * 用户登录
     *
     * @param login 用户对象
     * @return ResultData对象
     */
    @PostMapping(value = "/loginAction")
    public ResultData loginAction(@RequestBody Login login) {
        Login _login = providerService.getLoginByUsername(login.getUsername());

        if(_login != null) {
            if(_login.getPassword().compareTo(login.getPassword()) == 0) {
                return ResultData.success(_login);
            }
        }

        return ResultData.validateFailed();
    }

    /**
     * 插入用户对象
     *
     * @param login 用户对象
     * @return ResultData对象
     */
    @PostMapping(value = "/addLogin")
    public ResultData addLogin (@RequestBody Login login) {
        if(login == null) {
            providerService.addLogin(login);
            Login _login = providerService.getLoginByUsername(login.getUsername());
            return new ResultData(0, "添加成功", _login, 1);
        }

        return new ResultData(-1, "添加失败", login, 0);
    }

    /**
     * 更新用户对象
     *
     * @param login 用户对象
     * @return ResultData对象
     */
    @PostMapping(value = "/setLogin")
    public ResultData setLogin (@RequestBody Login login) {
        if(login != null) {
            providerService.setLogin(login);
            Login _login = providerService.getLoginByUsername(login.getUsername());
            return new ResultData(0, "更新成功", _login, 1);
        }

        return new ResultData(-1, "更新失败", login, 0);
    }

    /**
     * 删除用户对象
     *
     * @param username 用户名
     * @return ResultData对象
     */
    @GetMapping(value = "/delLogin")
    public ResultData delLogin (@RequestParam("username") String username) {
        Login login = providerService.getLoginByUsername(username);

        if(login != null) {
            providerService.delLogin(username);
            return new ResultData(0, "删除成功", login, 1);
        }

        return new ResultData(-1, "删除失败", null, 0);
    }

    /**
     *  获取所有用户对象
     * @return 用户对象列表
     * */
    @GetMapping(value = "/getAllLogin")
    public ResultData getAllLogin () {
        List list = providerService.getAllLogin();
        long count = providerService.countAllLogin();

        if(list != null) {
            return new ResultData(0, "获取成功", list, count);
        }

        return new ResultData(-1, "获取失败", null, 0);
    }

    /**
     *  获取用户数量
     * @return ResultData对象
     * */
    @GetMapping(value = "/countAllLogin")
    public ResultData countAllLogin () {
        Long count = providerService.countAllLogin();

        if(count != null) {
            return new ResultData(0, "获取成功", count, 1);
        }

        return new ResultData(-1, "获取失败", null, 0);
    }

    /**
     * 获取用户对象,通过用户名
     *
     * @param username 用户名
     * @return ResultData对象
     */
    @GetMapping(value = "/getLoginByUsername")
    public ResultData getLoginByUsername (@RequestParam("username") String username) {
        Login login = providerService.getLoginByUsername(username);

        if(login != null) {
            return new ResultData(0, "获取成功", login, 1);
        }

        return new ResultData(-1, "获取失败", null, 0);
    }

    /**
     * 获取用户对象,通过用户类别
     *
     * @param name 用户类别
     * @return ResultData对象
     */
    @GetMapping(value = "/getLoginByName")
    public ResultData getLoginByName (@RequestParam("name") String name) {
        List list = providerService.getLoginByName(name);

        if(list != null) {
            return new ResultData(0, "获取成功", list, list.size());
        }

        return new ResultData(-1, "获取失败", null, 0);
    }

    /**
     * 获取用户对象,通过用户ID
     *
     * @param id 用户id
     * @return ResultData对象
     */
    @GetMapping(value = "/getLoginById")
    public ResultData getLoginById (@RequestParam("id") Integer id) {
        Login login = providerService.getLoginById(id);

        if(login != null) {
            return new ResultData(0, "获取成功", login, 1);
        }

        return new ResultData(-1, "获取失败", null, 0);
    }
}