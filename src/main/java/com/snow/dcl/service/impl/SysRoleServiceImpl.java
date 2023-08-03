/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.service.impl;

import com.snow.dcl.dao.SysRoleRepository;
import com.snow.dcl.dao.SysUserRoleRepository;
import com.snow.dcl.exception.CustomException;
import com.snow.dcl.model.SysRole;
import com.snow.dcl.model.SysUserRole;
import com.snow.dcl.model.vo.AssignRoleVo;
import com.snow.dcl.model.vo.SysRoleVo;
import com.snow.dcl.service.SysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName SysRoleService
 * (功能描述)
 * 角色Service
 * @Author Dcl_Snow
 * @Create 2021/8/27 10:17
 * @Version 1.0.0
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    SysRoleRepository sysRoleRepository;

    @Resource
    SysUserRoleRepository sysUserRoleRepository;

    @Override
    public void save(SysRoleVo sysRoleVo) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleVo, sysRole);
        sysRoleRepository.save(sysRole);
    }

    @Override
    public void update(SysRoleVo sysRoleVo) {
        SysRole sysRole = sysRoleRepository.getById(sysRoleVo.getId());
        if (ObjectUtils.isEmpty(sysRole)) {
            throw new CustomException("角色不存在");
        }
        BeanUtils.copyProperties(sysRoleVo, sysRole);
        sysRoleRepository.save(sysRole);
    }

    @Override
    public void delete(Long roleId) {
        sysRoleRepository.deleteById(roleId);
    }

    @Override
    public void deleteBatch(List<Long> roleIds) {
        sysRoleRepository.deleteAllByIdInBatch(roleIds);
    }

    @Override
    public SysRole findOne(Long roleId) {
        Optional<SysRole> roleOptional = sysRoleRepository.findById(roleId);
        if (roleOptional.isPresent()) {
            return roleOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public Page<SysRole> findAll(SysRole sysRole, Integer page, Integer size) {
        // 1.动态拼接查询条件
        Specification<SysRole> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!ObjectUtils.isEmpty(sysRole.getRoleName())) {
                list.add(criteriaBuilder.like(root.get("roleName").as(String.class), "%" + sysRole.getRoleName()
                        .replaceAll("/", "//")
                        .replaceAll("_", "/_")
                        .replaceAll("%", "/%") + "%", '/'));
            }
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        // 2.构造分页
        Pageable pageable = PageRequest.of(page - 1, size);
        // 3.查询数据并返回
        Page<SysRole> pageRole = sysRoleRepository.findAll(specification, pageable);
        return pageRole;
    }

    @Override
    public Map<String, Object> getRolesByUserId(Long userId) {
        List<SysRole> allRoles = sysRoleRepository.findAll();

        List<SysUserRole> userRoles = sysUserRoleRepository.findByUserId(userId);
        List<Long> userRoleIds = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("allRoles", allRoles);
        resultMap.put("userRoleIds", userRoleIds);
        return resultMap;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void doAssign(AssignRoleVo assignRoleVo) {
        sysUserRoleRepository.deleteByUserId(assignRoleVo.getUserId());
        List<Long> roleIdList = assignRoleVo.getRoleIdList();
        if (!CollectionUtils.isEmpty(roleIdList)) {
            for (Long roleId : roleIdList) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(assignRoleVo.getUserId());
                sysUserRole.setRoleId(roleId);
                sysUserRoleRepository.save(sysUserRole);
            }
        }
    }
}
