package com.snow.dcl;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.snow.dcl.dao.SysPermissionRepository;
import com.snow.dcl.exception.CustomException;
import com.snow.dcl.model.SysLog;
import com.snow.dcl.model.SysPermission;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
class SnowAdminApplicationTests {

    @Resource
    SysPermissionRepository sysPermissionRepository;

    @Test
    void contextLoads() {
        CustomException exception = new CustomException("自定义测试异常");

        String stackTrace = ExceptionUtil.stacktraceToString(exception, -1);
        System.out.println(stackTrace);

        String string = ExceptionUtil.getMessage(exception);
        System.out.println(string);

        SysLog sysLog = new SysLog();
        sysLog.setExceptionDetail(ExceptionUtil.getMessage(exception));
        System.out.println(sysLog);
    }

    @Test
    void contextLoads1() {
        List<SysPermission> all = sysPermissionRepository.findAll();
        // List<SysPermission> sysPermissions = new ArrayList<>();
        // for (SysPermission sysPermission : all) {
        //     if (sysPermission.getStatus().equals(1)){
        //         sysPermissions.add(sysPermission);
        //     }
        // }
        // Set<SysPermission> collect = sysPermissions.stream().collect(Collectors.toSet());
        // collect.stream().forEach(sysPermission -> System.out.println(sysPermission));
        // Set<SysPermission> collect = all.stream().filter(sysPermission -> sysPermission.getStatus().equals(1)).collect(Collectors.toList()).stream().collect(Collectors.toSet());
        Set<SysPermission> collect = all.stream().filter(sysPermission -> sysPermission.getStatus().equals(1)).collect(Collectors.toSet());
        collect.stream().forEach(sysPermission -> System.out.println(sysPermission));
    }

}
