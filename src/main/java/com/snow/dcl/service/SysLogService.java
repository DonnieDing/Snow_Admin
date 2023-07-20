package com.snow.dcl.service;

import com.snow.dcl.model.SysLog;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * (功能描述)
 * 操作日志Service
 * @Author:Dcl_Snow
 * @Create: 2023/7/19 10:50
 * @version: 1.0.0
 */
public interface SysLogService {
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SysLog sysLog);
}
