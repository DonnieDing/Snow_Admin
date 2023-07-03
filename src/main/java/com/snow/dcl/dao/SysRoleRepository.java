package com.snow.dcl.dao;

import com.snow.dcl.model.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName SysRoleRepository
 * (功能描述)
 * 角色Repository
 * @Author Dcl_Snow
 * @Create 2021/8/26 22:36
 * @version: 1.0.0
 */
public interface SysRoleRepository extends JpaRepository<SysRole, Long>, JpaSpecificationExecutor<SysRole> {
}
