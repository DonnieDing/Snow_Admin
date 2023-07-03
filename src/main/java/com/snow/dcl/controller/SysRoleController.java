/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.controller;

import com.snow.dcl.model.SysRole;
import com.snow.dcl.model.vo.AssignRoleVo;
import com.snow.dcl.model.vo.SysRoleVo;
import com.snow.dcl.service.SysRoleService;
import com.snow.dcl.utils.ResponseResult;
import com.snow.dcl.validation.GroupValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SysRoleController
 * (功能描述)
 * 角色Controller
 * @Author Dcl_Snow
 * @Create 2021/8/27 10:09
 * @Version 1.0.0
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    SysRoleService sysRoleService;

    @ApiOperation(value = "新增角色")
    @PostMapping("/save")
    public ResponseResult save(@Validated(value = GroupValidator.Create.class) @RequestBody SysRoleVo sysRoleVo) {
        sysRoleService.save(sysRoleVo);
        return ResponseResult.success().message("新增角色成功");
    }

    @ApiOperation(value = "更新角色")
    @PostMapping("/update")
    public ResponseResult update(@Validated(value = GroupValidator.Create.class) @RequestBody SysRoleVo sysRoleVo) {
        try {
            sysRoleService.update(sysRoleVo);
        } catch (Exception e) {
            return ResponseResult.fail().message("修改角色成失败");
        }
        return ResponseResult.success().message("修改角色成功");
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{roleId}")
    public ResponseResult delete(@Validated(value = GroupValidator.Delete.class) @PathVariable Long roleId) {
        sysRoleService.delete(roleId);
        return ResponseResult.success().message("删除角色成功");
    }

    @ApiOperation(value = "批量删除角色")
    @DeleteMapping("/")
    public ResponseResult deleteAll(@Validated(value = GroupValidator.Delete.class) @RequestBody List<Long> roleIds) {
        sysRoleService.deleteBatch(roleIds);
        return ResponseResult.success().message("批量删除成功");
    }

    @ApiOperation(value = "根据id查询角色")
    @PostMapping("/{roleId}")
    public ResponseResult queryById(@PathVariable Long roleId) {
        SysRole sysRole = sysRoleService.findOne(roleId);
        if (sysRole == null) {
            return ResponseResult.fail().message("角色不存在！");
        }
        return ResponseResult.success().data(sysRole);
    }

    @ApiOperation(value = "分页条件查询角色")
    @PostMapping("/{page}/{size}")
    // @PreAuthorize("hasAuthority('role.list')")
    public ResponseResult query(@PathVariable Integer page, @PathVariable Integer size, @RequestBody SysRoleVo sysRoleVo) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleVo, sysRole);
        Page<SysRole> PageSysRoles = sysRoleService.findAll(sysRole, page, size);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", PageSysRoles.getTotalElements());
        hashMap.put("records", PageSysRoles.getContent());
        return ResponseResult.success().data(hashMap);
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public ResponseResult toAssign(@PathVariable Long userId) {
        Map<String, Object> roleMap = sysRoleService.getRolesByUserId(userId);
        return ResponseResult.success().data(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public ResponseResult doAssign(@RequestBody AssignRoleVo assignRoleVo) {
        sysRoleService.doAssign(assignRoleVo);
        return ResponseResult.success();
    }
}
