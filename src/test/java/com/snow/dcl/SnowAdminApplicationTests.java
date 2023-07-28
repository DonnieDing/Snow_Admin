package com.snow.dcl;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.snow.dcl.exception.CustomException;
import com.snow.dcl.model.SysLog;
import com.snow.dcl.utils.SysLogUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SnowAdminApplicationTests {

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
        String localCityInfo = SysLogUtils.getLocalCityInfo("211.137.70.58");
        System.out.println(localCityInfo);
        String localCityInfo1 = SysLogUtils.getHttpCityInfo("211.137.70.58");
        System.out.println(localCityInfo1);
    }

}
