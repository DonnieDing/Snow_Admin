/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.service;

import com.snow.dcl.model.SysRole;
import com.snow.dcl.model.dto.system.AssignRoleDto;
import com.snow.dcl.model.dto.system.SysRoleDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SysRoleService
 * (功能描述)
 * 角色Service
 * @Author Dcl_Snow
 * @Create 2021/8/27 10:17
 * @Version 1.0.0
 */
public interface SysRoleService {

    void save(SysRoleDto sysRoleDto);

    void update(SysRoleDto sysRoleDto);

    void delete(Long roleId);

    void deleteBatch(List<Long> roleIds);

    SysRole findOne(Long roleId);

    Page<SysRole> findAll(SysRole sysRole, Integer page, Integer size);

    Map<String, Object> getRolesByUserId(Long userId);

    void doAssign(AssignRoleDto assignRoleDto);
}
