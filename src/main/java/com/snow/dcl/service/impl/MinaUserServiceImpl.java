package com.snow.dcl.service.impl;

import com.snow.dcl.dao.SysUserRepository;
import com.snow.dcl.model.SysUser;
import com.snow.dcl.model.dto.system.WxCodeDto;
import com.snow.dcl.service.LoginService;
import com.snow.dcl.service.MinaUserService;
import com.snow.dcl.utils.MinaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

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

    @Resource
    private SysUserRepository sysUserRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private LoginService loginService;

    @Resource
    private MinaUtils minaUtils;

    @Override
    public String minaLogin(WxCodeDto wxCodeDto) {
        String code = wxCodeDto.getCode();
        String nickname = wxCodeDto.getNickname();
        String avatarUrl = wxCodeDto.getAvatarUrl();
        String wxOpenId = minaUtils.jscode2session(code);
        SysUser sysUser = sysUserRepository.findByWxOpenId(wxOpenId);
        if (ObjectUtils.isEmpty(sysUser)) {
            SysUser newSysUser = new SysUser();
            newSysUser.setUsername(nickname);
            newSysUser.setNickName(nickname);
            String encodePassword = passwordEncoder.encode(wxOpenId);
            newSysUser.setPassword(encodePassword);
            newSysUser.setWxOpenId(wxOpenId);
            newSysUser.setAvatar(avatarUrl);
            sysUserRepository.save(newSysUser);
            log.info("新用户信息：" + newSysUser);
            sysUser = newSysUser;
        } else {
            if (!nickname.equals(sysUser.getNickName())){
                sysUser.setUsername(nickname);
                sysUser.setNickName(nickname);
            }
            if (!avatarUrl.equals(sysUser.getAvatar())){
                sysUser.setAvatar(avatarUrl);
            }
            sysUserRepository.save(sysUser);
        }
        return loginService.verifyUser(sysUser.getUsername(), wxOpenId);
    }
}
