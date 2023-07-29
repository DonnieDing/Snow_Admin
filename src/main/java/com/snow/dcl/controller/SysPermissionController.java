/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.controller;

import com.snow.dcl.annotation.SysOperateLog;
import com.snow.dcl.model.SysPermission;
import com.snow.dcl.model.vo.AssignPermissionVo;
import com.snow.dcl.model.vo.SysPermissionVo;
import com.snow.dcl.service.SysPermissionService;
import com.snow.dcl.utils.ResponseResult;
import com.snow.dcl.validation.GroupValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @ClassName SysPermissionController
 * (功能描述)
 * 权限Controller
 * @Author Dcl_Snow
 * @Create 2021/8/27 14:57
 * @Version 1.0.0
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("/sys")
public class SysPermissionController {

    @Resource
    SysPermissionService sysPermissionService;

    @SysOperateLog("新增权限")
    @ApiOperation(value = "新增权限")
    @PostMapping("/permission")
    public ResponseResult save(@Validated(value = GroupValidator.Create.class) @RequestBody SysPermissionVo sysPermissionVo) {
        sysPermissionService.save(sysPermissionVo);
        return ResponseResult.success().message("新增权限成功");
    }

    @SysOperateLog("删除权限")
    @ApiOperation(value = "删除权限")
    @DeleteMapping("/permission/{permissionId}")
    public ResponseResult delete(@PathVariable Long permissionId) {
        sysPermissionService.delete(permissionId);
        return ResponseResult.success().message("删除权限成功");
    }

    @SysOperateLog("更新权限")
    @ApiOperation(value = "更新权限")
    @PutMapping("/permission")
    public ResponseResult update(@Validated(value = GroupValidator.Update.class) @RequestBody SysPermissionVo permissionVo) {
        sysPermissionService.save(permissionVo);
        return ResponseResult.success().message("更新权限成功");
    }

    @ApiOperation(value = "查询所有权限")
    @GetMapping("/permission")
    public ResponseResult findAll() {
        Set<SysPermission> permissionAll = sysPermissionService.findAll();
        return ResponseResult.success().data(permissionAll);
    }

    @ApiOperation(value = "根据角色获取权限")
    @GetMapping("/permission/toAssign/{roleId}")
    public ResponseResult selectPermissionByRole(@PathVariable Long roleId) {
        Set<SysPermission> menuList = sysPermissionService.selectPermissionByRole(roleId);
        return ResponseResult.success().data(menuList);
    }

    @ApiOperation(value = "根据角色分配权限")
    @PostMapping("/permission/doAssign")
    public ResponseResult doAssign(@RequestBody AssignPermissionVo assignPermissionVo) {
        sysPermissionService.doAssign(assignPermissionVo);
        return ResponseResult.success();
    }
}
