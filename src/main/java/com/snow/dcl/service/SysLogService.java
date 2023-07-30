package com.snow.dcl.service;

import com.snow.dcl.model.SysLog;
import com.snow.dcl.model.SysRole;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.domain.Page;

/**
 * (功能描述)
 * 操作日志Service
 * @Author:Dcl_Snow
 * @Create: 2023/7/19 10:50
 * @version: 1.0.0
 */
public interface SysLogService {
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SysLog sysLog);

    Page<SysLog> findAll(SysLog sysLog, Integer page, Integer size);

    SysLog findById(Long id);
}
