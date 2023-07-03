package com.snow.dcl.dao;

import com.snow.dcl.model.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName SysPermissionRepository
 * (功能描述)
 * 权限Repository
 * @Author Dcl_Snow
 * @Create 2021/8/26 22:36
 * @version: 1.0.0
 */
public interface SysPermissionRepository extends JpaRepository<SysPermission, Long>, JpaSpecificationExecutor<SysPermission> {

    List<SysPermission> findByPid(Long pid);
}
