package com.snow.dcl.dao;

import com.snow.dcl.model.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
/**
 * (功能描述)
 * 操作日志Repository
 * @Author:Dcl_Snow
 * @Create: 2023/7/19 11:09
 * @version: 1.0.0
 */
@Repository
public interface SysLogRepository extends JpaRepository<SysLog,Long>, JpaSpecificationExecutor<SysLog> {

}
