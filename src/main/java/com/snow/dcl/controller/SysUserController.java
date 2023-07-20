/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.controller;

import com.snow.dcl.annotation.SysOperateLog;
import com.snow.dcl.model.SysUser;
import com.snow.dcl.model.vo.SysUserVo;
import com.snow.dcl.service.SysUserService;
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

/**
 * @ClassName SysUserController
 * (功能描述)
 * 用户Controller
 * @Author Dcl_Snow
 * @Create 2021/8/24 15:22
 * @Version 1.0.0
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    SysUserService sysUserService;

    @SysOperateLog("新增用户")
    @ApiOperation(value = "新增用户")
    @PostMapping("/save")
    public ResponseResult save(@Validated(value = GroupValidator.Create.class) @RequestBody SysUserVo sysUserVo) {
        sysUserService.save(sysUserVo);
        return ResponseResult.success().message("新增用户成功");
    }

    @SysOperateLog("根据id删除用户")
    @ApiOperation(value = "根据id删除用户")
    @DeleteMapping("/{userId}")
    public ResponseResult delete(@Validated(value = GroupValidator.Delete.class) @PathVariable Long userId) {
        sysUserService.delete(userId);
        return ResponseResult.success().message("删除用户成功");
    }

    @SysOperateLog("批量删除用户")
    @ApiOperation(value = "批量删除用户")
    @DeleteMapping("/")
    public ResponseResult deleteAll(@Validated(value = GroupValidator.Delete.class) @RequestBody List<Long> userIds) {
        sysUserService.deleteBatch(userIds);
        return ResponseResult.success().message("批量删除成功");
    }

    @SysOperateLog("更新用户")
    @ApiOperation(value = "更新用户")
    @PutMapping("/update")
    public ResponseResult update(@Validated(value = GroupValidator.Update.class) @RequestBody SysUserVo sysUserVo) {
        sysUserService.update(sysUserVo);
        return ResponseResult.success().message("修改用户成功");
    }

    @ApiOperation(value = "根据id查询用户")
    @GetMapping("/{userId}")
    // @PreAuthorize("hasAuthority('user.list')")
    public ResponseResult queryById(@PathVariable Long userId) {
        SysUser sysUser = sysUserService.findOne(userId);
        return ResponseResult.success().data(sysUser);
    }

    @ApiOperation(value = "分页条件查询用户")
    @PostMapping("/{page}/{size}")
    // @PreAuthorize("hasAuthority('user.list')")
    public ResponseResult query(@PathVariable Integer page, @PathVariable Integer size, @RequestBody SysUserVo sysUserVo) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserVo, sysUser);
        Page<SysUser> PageSysUsers = sysUserService.findAll(sysUser, page, size);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", PageSysUsers.getTotalElements());
        hashMap.put("records", PageSysUsers.getContent());
        return ResponseResult.success().data(hashMap);
    }

    @SysOperateLog("更新用户状态")
    @ApiOperation(value = "更新用户状态")
    @GetMapping("/updateStatus/{id}/{status}")
    public ResponseResult updateStatus(@PathVariable Long id, @PathVariable Short status) {
        sysUserService.updateStatus(id, status);
        return ResponseResult.success();
    }

}
