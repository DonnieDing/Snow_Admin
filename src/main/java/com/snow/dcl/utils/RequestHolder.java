package com.snow.dcl.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
/**
 * (功能描述)
 * 获取HttpServletRequest
 * @Author:Dcl_Snow
 * @Create: 2023/7/19 14:34
 * @version: 1.0.0
 */
public class RequestHolder {

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
