package com.snow.dcl.handler;

import com.snow.dcl.constant.CacheKeyConstant;
import com.snow.dcl.exception.CustomException;
import com.snow.dcl.model.SysUser;
import com.snow.dcl.service.SysUserService;
import com.snow.dcl.utils.JwtUtil;
import com.snow.dcl.utils.RedisUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * (功能描述)
 * JwtToken过滤器
 *
 * @Author:Dcl_Snow
 * @Create: 2022/4/28 13:59
 * @version: 1.0.0
 */
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    private JwtUtil jwtUtil;
    private RedisUtils redisUtils;
    private SysUserService sysUserService;
    public JwtTokenAuthenticationFilter(JwtUtil jwtUtil, RedisUtils redisUtils, SysUserService sysUserService) {
        this.jwtUtil = jwtUtil;
        this.redisUtils = redisUtils;
        this.sysUserService = sysUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 如果已经通过认证
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }

        //获取header
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(header) && header.startsWith(tokenHead)) {
            //获取token主体
            String token = header.substring(tokenHead.length());
            if (jwtUtil.isExpiration(token)) {
                throw new CustomException("Token已过期！");
            }
            //解析token，获取userId
            Long userId = jwtUtil.getUserIdByToken(token);
            // 从缓存中获取用户信息
            Object cacheToken = redisUtils.getValue(CacheKeyConstant.SYS_USER_ID + userId);
            if (Objects.isNull(cacheToken)) {
                throw new CustomException("用户未登录！");
            }
            if (!ObjectUtils.isEmpty(userId) && SecurityContextHolder.getContext().getAuthentication() == null) {
                SysUser sysUser = sysUserService.findOne(userId);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser, null, sysUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        //过滤器放行
        chain.doFilter(request, response);
    }
}
