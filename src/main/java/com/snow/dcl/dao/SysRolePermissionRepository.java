package com.snow.dcl.dao;

import com.snow.dcl.model.SysRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface SysRolePermissionRepository extends JpaRepository<SysRolePermission, Long>, JpaSpecificationExecutor<SysRolePermission> {
    void deleteByRoleId(Long roleId);

    @Query("select permissionId from SysRolePermission where roleId in ?1")
    Set<Long> findByRoleIds(Set<Long> roleIds);
}
