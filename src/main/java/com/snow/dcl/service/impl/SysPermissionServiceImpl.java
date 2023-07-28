/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.service.impl;

import com.snow.dcl.dao.SysPermissionRepository;
import com.snow.dcl.dao.SysRoleRepository;
import com.snow.dcl.model.SysPermission;
import com.snow.dcl.model.SysRole;
import com.snow.dcl.model.vo.SysPermissionVo;
import com.snow.dcl.service.SysPermissionService;
import com.snow.dcl.utils.MenuHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName SysPermissionService
 * (功能描述)
 * 权限Service
 * @Author Dcl_Snow
 * @Create 2021/8/27 14:58
 * @Version 1.0.0
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Resource
    private SysPermissionRepository sysPermissionRepository;

    @Resource
    private SysRoleRepository sysRoleRepository;

    @Override
    public void save(SysPermissionVo sysPermissionVo) {
        SysPermission sysPermission;
        if (sysPermissionVo.getId() == null) {
            sysPermission = new SysPermission();
        } else {
            sysPermission = sysPermissionRepository.getById(sysPermissionVo.getId());
        }
        BeanUtils.copyProperties(sysPermissionVo, sysPermission);
        sysPermissionRepository.save(sysPermission);
    }

    @Override
    public void delete(Long permissionId) {
        List<Long> idList = new ArrayList<>();
        getChildrenId(permissionId, idList);
        idList.add(permissionId);
        sysPermissionRepository.deleteAllByIdInBatch(idList);
    }

    // 查询该permissionId下的所有子id
    private void getChildrenId(Long permissionId, List<Long> idList) {
        List<SysPermission> permissionsByPid = sysPermissionRepository.findByPid(permissionId);
        for (SysPermission sysPermission : permissionsByPid) {
            if (!ObjectUtils.isEmpty(sysPermission.getId())) {
                Long id = sysPermission.getId();
                idList.add(id);
                this.getChildrenId(id, idList);
            }
        }
    }

    @Override
    public Set<SysPermission> findAll() {
        List<SysPermission> allPermissions = sysPermissionRepository.findAll();
        Set<SysPermission> permissionSet = allPermissions.stream().collect(Collectors.toSet());
        return MenuHelper.buildTree(permissionSet);
    }

    /**
     * 根据角色查询树形权限
     */
    @Override
    public Set<SysPermission> selectPermissionByRole(Long roleId) {
        // 1.查询系统所有权限
        List<SysPermission> allSysPermissionList = sysPermissionRepository.findAll();
        Set<SysPermission> allSysPermissionSet = allSysPermissionList.stream().collect(Collectors.toSet());
        // 2.根据角色id查询角色的权限
        Set<SysPermission> sysPermissionSet = null;
        Optional<SysRole> oSysRole = sysRoleRepository.findById(roleId);
        if (oSysRole.isPresent()) {
            sysPermissionSet = oSysRole.get().getPermissionSet();
        }
        List<Long> rolePermissionIds = sysPermissionSet.stream().map(p -> p.getId()).collect(Collectors.toList());
        for (SysPermission sysPermission : allSysPermissionList) {
            if (rolePermissionIds.contains(sysPermission.getId())) {
                sysPermission.setSelect(true);
            } else {
                sysPermission.setSelect(false);
            }
        }
        Set<SysPermission> permissionList = MenuHelper.buildTree(allSysPermissionSet);
        return permissionList;
    }
}
