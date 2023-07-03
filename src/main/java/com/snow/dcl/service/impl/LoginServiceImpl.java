/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.service.impl;

import com.snow.dcl.constant.CacheKeyConstant;
import com.snow.dcl.constant.CacheNameConstant;
import com.snow.dcl.dao.SysUserRepository;
import com.snow.dcl.exception.CustomException;
import com.snow.dcl.model.SysUser;
import com.snow.dcl.model.vo.LoginVo;
import com.snow.dcl.service.CaptchaService;
import com.snow.dcl.service.LoginService;
import com.snow.dcl.utils.JwtUtil;
import com.snow.dcl.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName LoginServiceImpl
 * (功能描述)
 * 登录Service实现
 * @Author Dcl_Snow
 * @Create 2021/11/19 14:25
 * @Version 1.0.0
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private CaptchaService captchaService;

    @Resource
    private SysUserRepository sysUserRepository;

    @Override
    public Map<String, String> login(LoginVo loginVo) {
        // 1.校验验证码
        captchaService.verification(loginVo.getCaptcha(), loginVo.getCodeKey());
        // 1.1判断用户状态
        SysUser loginUser = sysUserRepository.findByUsername(loginVo.getUsername());
        if (loginUser.getStatus().equals((short) 0)) {
            throw new CustomException("用户已停用，请联系管理员！");
        }
        // 2.authenticationManager.authenticate()方法进行登录用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 3.判断认证是否成功，认证未通过，返回提示
        if (Objects.isNull(authenticate)) {
            throw new CustomException("登录失败！");
        }
        // 4.认证通过，生成token，并存如缓存
        //使用JWT生成token
        SysUser sysUser = (SysUser) authenticate.getPrincipal();
        if (sysUser.getStatus().equals((short) 0)) {
            throw new CustomException("该用户已停用，请联系平台管理员！");
        }
        String token = tokenHead + jwtUtil.generateToken(sysUser);
        redisUtils.setValueTime(CacheKeyConstant.SYS_USER_ID + sysUser.getId(), token, 1440);
        //返回给前端数据
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

    @Override
    public void logout() {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        redisUtils.delKey(CacheKeyConstant.SYS_USER_ID + sysUser.getId());
    }
}
