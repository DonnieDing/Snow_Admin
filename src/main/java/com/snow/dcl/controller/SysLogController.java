package com.snow.dcl.controller;

import com.snow.dcl.model.SysLog;
import com.snow.dcl.service.SysLogService;
import com.snow.dcl.utils.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @ClassName SysUserController
 * (功能描述)
 * 用户Controller
 * @Author Dcl_Snow
 * @Create 2023/7/23 21:22
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Resource
    private SysLogService sysLogService;

    @PostMapping("/{page}/{size}")
    public ResponseResult query(@PathVariable Integer page, @PathVariable Integer size, @RequestBody SysLog sysLog) {
        Page<SysLog> PageSysLogs = sysLogService.findAll(sysLog, page, size);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", PageSysLogs.getTotalElements());
        hashMap.put("records", PageSysLogs.getContent());
        return ResponseResult.success().data(hashMap);
    }

    @PostMapping("/{id}")
    public ResponseResult query(@PathVariable Long id) {
        SysLog sysLog = sysLogService.findById(id);
        return ResponseResult.success().data(sysLog);
    }
}
