package com.snow.dcl.constant;

public class SpringSecurityConstant {
    /**
     * 放开权限校验的接口
     */
    public static final String[] NONE_SECURITY_URL_PATTERNS = {

            //前端的
            "/favicon.ico",

            //swagger相关的
            "/doc.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/configuration/ui",
            "/configuration/security",

    };
}
