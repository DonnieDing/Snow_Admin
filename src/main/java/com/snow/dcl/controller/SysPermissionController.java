/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.controller;

import com.snow.dcl.annotation.SysOperateLog;
import com.snow.dcl.model.SysPermission;
import com.snow.dcl.model.dto.system.AssignPermissionDto;
import com.snow.dcl.model.dto.system.SysPermissionDto;
import com.snow.dcl.service.SysPermissionService;
import com.snow.dcl.utils.ResponseResult;
import com.snow.dcl.validation.GroupValidator;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @ClassName SysPermissionController
 * (功能描述)
 * 权限Controller
 * @Author Dcl_Snow
 * @Create 2021/8/27 14:57
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController {

    @Resource
    SysPermissionService sysPermissionService;

    @SysOperateLog("新增权限")
    @PostMapping("/")
    @PreAuthorize("hasAuthority('permission.add')")
    public ResponseResult save(@Validated(value = GroupValidator.Create.class) @RequestBody SysPermissionDto sysPermissionDto) {
        sysPermissionService.save(sysPermissionDto);
        return ResponseResult.success().message("新增权限成功");
    }

    @SysOperateLog("删除权限")
    @DeleteMapping("/{permissionId}")
    @PreAuthorize("hasAuthority('permission.remove')")
    public ResponseResult delete(@PathVariable Long permissionId) {
        sysPermissionService.delete(permissionId);
        return ResponseResult.success().message("删除权限成功");
    }

    @SysOperateLog("更新权限")
    @PutMapping("/")
    @PreAuthorize("hasAuthority('permission.update')")
    public ResponseResult update(@Validated(value = GroupValidator.Update.class) @RequestBody SysPermissionDto permissionVo) {
        sysPermissionService.save(permissionVo);
        return ResponseResult.success().message("更新权限成功");
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('permission.list')")
    public ResponseResult findAll() {
        Set<SysPermission> permissionAll = sysPermissionService.findAll();
        return ResponseResult.success().data(permissionAll);
    }

    @GetMapping("/toAssign/{roleId}")
    public ResponseResult selectPermissionByRole(@PathVariable Long roleId) {
        Set<SysPermission> menuList = sysPermissionService.selectPermissionByRole(roleId);
        return ResponseResult.success().data(menuList);
    }

    @PostMapping("/doAssign")
    @PreAuthorize("hasAuthority('role.assign')")
    public ResponseResult doAssign(@RequestBody AssignPermissionDto assignPermissionDto) {
        sysPermissionService.doAssign(assignPermissionDto);
        return ResponseResult.success();
    }

    @SysOperateLog("更新权限状态")
    @GetMapping("/updateStatus/{id}/{status}")
    public ResponseResult updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        sysPermissionService.updateStatus(id, status);
        return ResponseResult.success();
    }

}
