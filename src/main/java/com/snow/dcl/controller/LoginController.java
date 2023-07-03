/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.controller;

import com.snow.dcl.model.SysUser;
import com.snow.dcl.model.vo.LoginVo;
import com.snow.dcl.service.LoginService;
import com.snow.dcl.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

/**
 * @ClassName LoginController
 * (功能描述)
 * 登录接口
 * @Author Dcl_Snow
 * @Create 2021/11/19 14:13
 * @Version 1.0.0
 */
@Api(tags = "登录管理")
@RestController
@RequestMapping("/user")
public class LoginController {

    @Resource
    private LoginService loginService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginVo loginVo) {
        Map<String, String> map = loginService.login(loginVo);
        return ResponseResult.success().message("登陆成功！").data(map);
    }

    @ApiOperation(value = "获取用户基本信息")
    @GetMapping("/getInfo")
    public ResponseResult getUserInfo(Principal principal) {

        if (null == principal) {
            return ResponseResult.fail().message("请登录！");
        }
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseResult.success().message("获取用户信息成功！").data(sysUser);
    }

    @ApiOperation(value = "用户登出")
    @GetMapping("/logout")
    public ResponseResult logout() {

        loginService.logout();
        return ResponseResult.success().message("退出成功！");
    }

}
