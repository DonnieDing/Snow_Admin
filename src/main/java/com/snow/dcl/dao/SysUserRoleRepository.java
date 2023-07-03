package com.snow.dcl.dao;

import com.snow.dcl.model.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SysUserRoleRepository extends JpaRepository<SysUserRole, Long>, JpaSpecificationExecutor<SysUserRole> {
    List<SysUserRole> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
