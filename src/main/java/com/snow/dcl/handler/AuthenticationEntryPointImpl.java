package com.snow.dcl.handler;

import com.snow.dcl.utils.HttpCodeEnum;
import com.snow.dcl.utils.ResponseResult;
import com.snow.dcl.utils.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        ResponseUtil.out(response, ResponseResult.fail().code(HttpCodeEnum.UNAUTHORIZED.getCode()).message(HttpCodeEnum.UNAUTHORIZED.getMessage()));
    }
}
