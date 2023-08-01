/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.service.impl;

import com.snow.dcl.dao.SysUserRepository;
import com.snow.dcl.exception.CustomException;
import com.snow.dcl.model.SysUser;
import com.snow.dcl.model.vo.RouterVo;
import com.snow.dcl.model.vo.SysUserVo;
import com.snow.dcl.service.SysPermissionService;
import com.snow.dcl.service.SysUserService;
import com.snow.dcl.utils.BeanCopyUtils;
import com.snow.dcl.validation.ValidationUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * @ClassName SysUserService
 * (功能描述)
 * 用户Service
 * @Author Dcl_Snow
 * @Create 2021/8/24 15:16
 * @Version 1.0.0
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    SysUserRepository sysUserRepository;

    @Resource
    SysPermissionService sysPermissionService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(SysUserVo sysUserVo) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserVo, sysUser);
        String encodePassword = passwordEncoder.encode(sysUser.getPassword());
        sysUser.setPassword(encodePassword);
        sysUserRepository.save(sysUser);
    }

    @Override
    public void update(SysUserVo sysUserVo) {
        SysUser sysUser = sysUserRepository.getById(sysUserVo.getId());
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new CustomException("用户不存在");
        }
        BeanUtils.copyProperties(sysUserVo, sysUser, BeanCopyUtils.getNullPropertyNames(sysUserVo));
        sysUserRepository.save(sysUser);
    }

    @Override
    public void delete(Long userId) {
        sysUserRepository.deleteById(userId);
    }

    @Override
    public void deleteBatch(List<Long> userIds) {
        sysUserRepository.deleteAllByIdInBatch(userIds);
    }

    @Override
    public SysUser findOne(Long userId) {
        SysUser sysUser = sysUserRepository.findById(userId).orElseGet(SysUser::new);
        ValidationUtil.isNull(sysUser.getId(), "SysUser", "id", userId);
        return sysUser;
    }

    @Override
    public Page<SysUser> findAll(SysUser sysUser, Integer page, Integer size) {
        // 1.动态拼接查询条件
        Specification<SysUser> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!ObjectUtils.isEmpty(sysUser.getUsername())) {
                list.add(criteriaBuilder.like(root.get("username").as(String.class), "%" + sysUser.getUsername()
                        .replaceAll("/", "//")
                        .replaceAll("_", "/_")
                        .replaceAll("%", "/%") + "%", '/'));
            }
            if (!ObjectUtils.isEmpty(sysUser.getNickName())) {
                list.add(criteriaBuilder.like(root.get("nickName").as(String.class), "%" + sysUser.getNickName()
                        .replaceAll("/", "//")
                        .replaceAll("_", "/_")
                        .replaceAll("%", "/%") + "%", '/'));
            }
            if (!ObjectUtils.isEmpty(sysUser.getPhone())) {
                list.add(criteriaBuilder.equal(root.get("phone").as(String.class), sysUser.getPhone()));
            }
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        // 2.构造分页
        Pageable pageable = PageRequest.of(page - 1, size);
        // 3.查询数据并返回
        Page<SysUser> pageUser = sysUserRepository.findAll(specification, pageable);
        return pageUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserRepository.findByUsername(username);
        if (null == sysUser) {
            // throw new UsernameNotFoundException(username);
            // 捕获UsernameNotFoundException后，默认的hideUserNotFoundExceptions为true
            // 最终返回的还是新建的BadCredentialsException，信息为:“用户名或密码错误”
            // 此处new一个BadCredentialsException改写异常信息“该用户不存在”
            throw new BadCredentialsException(username + ":该用户不存在！");
        }
        return sysUser;
    }

    @Override
    public void updateStatus(Long id, Short status) {
        Optional<SysUser> oSysUser = sysUserRepository.findById(id);
        if (oSysUser.isPresent()) {
            SysUser sysUser = oSysUser.get();
            sysUser.setStatus(status);
            sysUserRepository.save(sysUser);
        }
    }

    @Override
    public Map<String, Object> getUserInfo(Long id) {
        SysUser sysUser = sysUserRepository.findById(id).get();
        //根据userid查询菜单权限值
        List<RouterVo> routerVoList = sysPermissionService.getUserMenuList(id);
        //根据userid查询按钮权限值
        List<String> permsList = sysPermissionService.getUserButtonList(id);

        Map<String,Object> result = new HashMap<>();
        result.put("username",sysUser.getUsername());
        result.put("avatar",sysUser.getAvatar());
        result.put("roles",sysUser.getRoleSet());
        //菜单权限数据
        result.put("menus",routerVoList);
        //按钮权限数据
        result.put("buttons",permsList);
        return result;
    }
}
