package com.snow.dcl.utils;

import com.snow.dcl.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @ClassName SecurityUtils
 * (功能描述)
 * Security工具类
 * @Author Dcl_Snow
 * @Create 2023/7/19 13:28
 * @Version 1.0.0
 */
public class SecurityUtils {
    /**
     * 获取登录用户名称
     *
     * @return 登录用户名称
     */
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        throw new CustomException(HttpStatus.UNAUTHORIZED, "找不到当前登录的信息");
    }
}
