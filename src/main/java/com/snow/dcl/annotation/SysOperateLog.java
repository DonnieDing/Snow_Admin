package com.snow.dcl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * (功能描述)
 * 操作日志自定义注解
 * @Author:Dcl_Snow
 * @Create: 2023/7/19 10:43
 * @version: 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SysOperateLog {

    String value() default "";

}
