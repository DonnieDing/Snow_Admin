/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.service;

import com.snow.dcl.model.SysUser;
import com.snow.dcl.model.vo.SysUserVo;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SysUserService
 * (功能描述)
 * 用户Service
 * @Author Dcl_Snow
 * @Create 2021/8/24 15:16
 * @Version 1.0.0
 */
public interface SysUserService extends UserDetailsService {

    void save(SysUserVo sysUserVo);

    void update(SysUserVo sysUserVo);

    void delete(Long userId);

    void deleteBatch(List<Long> userIds);

    SysUser findOne(Long userId);

    Page<SysUser> findAll(SysUser sysUser, Integer page, Integer size);

    void updateStatus(Long id, Short status);

    Map<String, Object> getUserInfo(Long id);
}
