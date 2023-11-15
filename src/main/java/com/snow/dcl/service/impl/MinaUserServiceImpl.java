package com.snow.dcl.service.impl;

import cn.hutool.http.HttpUtil;
import com.snow.dcl.constant.CacheKeyConstant;
import com.snow.dcl.dao.SysUserRepository;
import com.snow.dcl.exception.CustomException;
import com.snow.dcl.model.SysUser;
import com.snow.dcl.model.dto.system.WxLoginDto;
import com.snow.dcl.service.LoginService;
import com.snow.dcl.service.MinaUserService;
import com.snow.dcl.utils.JsonUtils;
import com.snow.dcl.utils.RedisUtils;
import com.snow.dcl.utils.SessionIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName MinaUserServiceImpl
 * (功能描述)
 * 小程序接口实现类
 * @Author Dcl_Snow
 * @Create 2022/2/25 14:47
 * @Version 1.0.0
 */
@Slf4j
@Service
public class MinaUserServiceImpl implements MinaUserService {

    @Value("${mini.appId}")
    private String appId;

    @Value("${mini.secret}")
    private String secret;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private SysUserRepository sysUserRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private LoginService loginService;

    @Override
    public String getSessionId(String code) {
        /**
         * 1.拼接微信登录凭证校验url（auth.code2Session）
         * 2.发送http请求，获取微信返回结果
         * 3.存储凭证到Ehcache
         * 4.生成一个sessionId，返回给前端，作为当前需要登录用户的标识
         * 5.sessionId作用，当下次用户再次点击登录时，标识是谁发起的微信登录
         */

        String url = "https://api.weixin.qq.com/sns/jscode2session?" + "appid=" +
                appId +
                "&secret=" +
                secret +
                "&js_code=" +
                code +
                "&grant_type=authorization_code";

        String response = HttpUtil.get(url);

        Map dataMap = JsonUtils.jsonToObject(response, Map.class);
        Integer errCode = (Integer) dataMap.get("errcode");
        if (errCode != null && errCode != 0) {
            throw new CustomException("小程序登录失败！");
        }

        String wxOpenId = (String) dataMap.get("openid");
        log.info("响应结果：" + response);
        String sessionId = SessionIdUtils.CreateId();
        redisUtils.setValueTime(CacheKeyConstant.WX_SESSION_ID + sessionId, wxOpenId, 60);
        return sessionId;
    }

    @Override
    public String wxLogin(WxLoginDto wxLoginDto) {
        /**
         * 1.对WxLoginVo进行数据解密
         * 2.解密完成后获取微信用户信息（openId等）
         * 3.使用唯一的openId，到用户表中查询用户是否存在：若用户存在则执行登录逻辑，若用户不存在（新用户）则先执行注册逻辑再登录
         * 4.生成jwt token返回前端，用户后续请求携带token，后端进行token验证
         */
        String sessionId = wxLoginDto.getSessionId();
        if (!StringUtils.hasText(sessionId)) {
            throw new CustomException("小程序登录失败！");
        }
        String wxOpenId = (String) redisUtils.getValue(CacheKeyConstant.WX_SESSION_ID + sessionId);
        SysUser sysUser = sysUserRepository.findByWxOpenId(wxOpenId);
        if (ObjectUtils.isEmpty(sysUser)) {
            SysUser newSysUser = new SysUser();
            newSysUser.setUsername(wxOpenId);
            newSysUser.setNickName("minaUser" + wxOpenId);
            String encodePassword = passwordEncoder.encode(wxOpenId);
            newSysUser.setPassword(encodePassword);
            sysUserRepository.save(newSysUser);
            log.info("新用户信息：" + newSysUser);
            sysUser = newSysUser;
        }
        return loginService.verifyUser(sysUser.getUsername(), wxOpenId);
    }
}
