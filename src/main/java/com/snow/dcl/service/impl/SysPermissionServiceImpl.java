/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.service.impl;

import com.snow.dcl.dao.SysPermissionRepository;
import com.snow.dcl.dao.SysRolePermissionRepository;
import com.snow.dcl.dao.SysRoleRepository;
import com.snow.dcl.dao.SysUserRoleRepository;
import com.snow.dcl.model.SysPermission;
import com.snow.dcl.model.SysRole;
import com.snow.dcl.model.SysRolePermission;
import com.snow.dcl.model.SysUserRole;
import com.snow.dcl.model.dto.system.AssignPermissionDto;
import com.snow.dcl.model.dto.system.RouterDto;
import com.snow.dcl.model.dto.system.SysPermissionDto;
import com.snow.dcl.service.SysPermissionService;
import com.snow.dcl.utils.MenuHelper;
import com.snow.dcl.utils.RouterHelper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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

    @Resource
    private SysRolePermissionRepository sysRolePermissionRepository;

    @Resource
    private SysUserRoleRepository sysUserRoleRepository;

    @Override
    public void save(SysPermissionDto sysPermissionDto) {
        SysPermission sysPermission;
        if (ObjectUtils.isEmpty(sysPermissionDto.getId())) {
            sysPermission = new SysPermission();
        } else {
            sysPermission = sysPermissionRepository.getById(sysPermissionDto.getId());
        }
        BeanUtils.copyProperties(sysPermissionDto, sysPermission);
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

    @Override
    @Transactional
    public void doAssign(AssignPermissionDto assignPermissionDto) {
        Long roleId = assignPermissionDto.getRoleId();
        // 根据角色id删除权限
        sysRolePermissionRepository.deleteByRoleId(roleId);
        // 遍历权限id列表，添加权限
        List<Long> permissionIdList = assignPermissionDto.getPermissionIdList();
        for (Long permissionId : permissionIdList) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(roleId);
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermissionRepository.save(sysRolePermission);
        }
    }

    //根据userid查询菜单权限值
    @Override
    public List<RouterDto> getUserMenuList(Long userId) {
        //admin是超级管理员，操作所有内容
        Set<SysPermission> sysMenuList;
        //判断userid值是1代表超级管理员，查询所有权限数据
        if (userId.equals(1L)) {
            sysMenuList = sysPermissionRepository.findAll().stream().filter(sysPermission -> sysPermission.getStatus().equals(1)).collect(Collectors.toSet());
        } else {
            //如果userid不是1，其他类型用户，查询这个用户权限
            Set<Long> roleIds = sysUserRoleRepository.findByUserId(userId).stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
            Set<Long> permissionIds = sysRolePermissionRepository.findByRoleIds(roleIds);
            sysMenuList = sysPermissionRepository.findAllById(permissionIds).stream().filter(sysPermission -> sysPermission.getStatus().equals(1)).collect(Collectors.toSet());
        }

        //构建是树形结构
        Set<SysPermission> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);

        //转换成前端路由要求格式数据
        List<RouterDto> routerDtoList = RouterHelper.buildRouters(sysMenuTreeList);
        return routerDtoList;
    }

    //根据userid查询按钮权限值
    @Override
    public List<String> getUserButtonList(Long userId) {
        Set<SysPermission> sysMenuList;
        //判断是否管理员
        if (userId.equals(1L)) {
            sysMenuList = sysPermissionRepository.findAll().stream().filter(sysPermission -> sysPermission.getStatus().equals(1)).collect(Collectors.toSet());

        } else {
            Set<Long> roleIds = sysUserRoleRepository.findByUserId(userId).stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
            Set<Long> permissionIds = sysRolePermissionRepository.findByRoleIds(roleIds);
            sysMenuList = sysPermissionRepository.findAllById(permissionIds).stream().filter(sysPermission -> sysPermission.getStatus().equals(1)).collect(Collectors.toSet());
        }
        List<String> permissionList = sysMenuList.stream().filter(sysPermission -> sysPermission.getType().equals(2)).map(sysPermission -> sysPermission.getPermissionValue()).collect(Collectors.toList());
        return permissionList;
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        SysPermission sysPermission = sysPermissionRepository.findById(id).get();
        sysPermission.setStatus(status);
        List<SysPermission> sysPermissions = sysPermissionRepository.findByPid(id);
        sysPermissions.forEach(children -> children.setStatus(status));
    }
}
