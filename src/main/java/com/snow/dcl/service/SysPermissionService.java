/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.service;

import com.snow.dcl.model.SysPermission;
import com.snow.dcl.model.vo.AssignPermissionVo;
import com.snow.dcl.model.vo.RouterVo;
import com.snow.dcl.model.vo.SysPermissionVo;

import java.util.List;
import java.util.Set;

/**
 * @ClassName SysPermissionService
 * (功能描述)
 * 权限Service
 * @Author Dcl_Snow
 * @Create 2021/8/27 14:58
 * @Version 1.0.0
 */
public interface SysPermissionService {

    void save(SysPermissionVo sysPermissionVo);

    void delete(Long permissionId);

    Set<SysPermission> findAll();

    Set<SysPermission> selectPermissionByRole(Long roleId);

    void doAssign(AssignPermissionVo assignPermissionVo);

    List<RouterVo> getUserMenuList(Long userId);

    List<String> getUserButtonList(Long userId);

    void updateStatus(Long id, Integer status);
}
