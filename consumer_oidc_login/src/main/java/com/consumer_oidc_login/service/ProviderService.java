package com.consumer_oidc_login.service;

import com.provider_oidc_login.entity.Login;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2020-11-06 11:40
 */
@Component
@FeignClient(value = "provider-oidc-login")
public interface ProviderService {
    @PostMapping("/login-api/add-login")
    int addLogin(@RequestBody Login login);

    @PostMapping("/login-api/set-login")
    int setLogin(@RequestBody Login login);

    @GetMapping("/login-api/del-login")
    int delLogin(@RequestParam("username") String username);

    @GetMapping("/login-api/get-all-login")
    List<Login> getAllLogin();

    @GetMapping("/login-api/count-all-login")
    long countAllLogin();

    @GetMapping("/login-api/get-login-by-username")
    Login getLoginByUsername(@RequestParam("username") String username);

    @GetMapping("/login-api/get-login-by-name")
    List<Login> getLoginByName(@RequestParam("name") String name);

    @GetMapping("/login-api/get-login-by-id")
    Login getLoginById(@RequestParam("id") Integer id);
}
