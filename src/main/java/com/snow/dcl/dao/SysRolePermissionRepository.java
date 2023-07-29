package com.snow.dcl.dao;

import com.snow.dcl.model.SysRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SysRolePermissionRepository extends JpaRepository<SysRolePermission, Long>, JpaSpecificationExecutor<SysRolePermission> {
    void deleteByRoleId(Long roleId);
}
