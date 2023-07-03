package com.snow.dcl.dao;

import com.snow.dcl.model.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName SysUserRepository
 * (功能描述)
 * 用户Repository
 * @Author Dcl_Snow
 * @Create 2021/8/24 15:16
 * @version 1.0.0
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser> {
    SysUser findByUsername(String username);
}
