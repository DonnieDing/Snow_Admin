package com.snow.dcl.service.impl;

import com.snow.dcl.constant.CacheKeyConstant;
import com.snow.dcl.constant.CacheNameConstant;
import com.snow.dcl.exception.CustomException;
import com.snow.dcl.service.CaptchaService;
import com.snow.dcl.utils.RedisUtils;
import com.wf.captcha.GifCaptcha;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName CaptchaServiceImpl
 * (功能描述)
 * 验证码接口实现
 * @Author Dcl_Snow
 * @Create 2021/12/16 14:26
 * @Version 1.0.0
 */
@Service
@Slf4j
@AllArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    @Resource
    private RedisUtils redisUtils;

    @Override
    public Map<String, String> getCaptcha() {

        //gif字符类型
        GifCaptcha captcha = new GifCaptcha();
        captcha.setLen(5);

        String captchaText = captcha.text();
        log.info("图形验证码：{}", captchaText);

        //存入Redis
        String codeKey = CacheKeyConstant.CAPTCHA_CODE + ":" + UUID.randomUUID();
        redisUtils.setValueTime(codeKey, captchaText, 30);

        Map<String, String> map = new HashMap<>();
        map.put("codeKey", codeKey);
        map.put("captchaImg", captcha.toBase64());

        return map;
    }

    @Override
    public void verification(String captcha, String codeKey) {
        if (!StringUtils.hasText(codeKey) || !StringUtils.hasText(captcha)) {
            throw new CustomException("验证码信息为空");
        }

        if (ObjectUtils.isEmpty(redisUtils.getValue(codeKey))) {
            throw new CustomException("验证码不存在，请重新获取验证码！");
        }
        String redisCode = redisUtils.getValue(codeKey).toString();
        if (!StringUtils.hasText(redisCode)) {
            throw new CustomException("验证码已过期！");
        }
        log.info(redisCode);
        if (!captcha.equalsIgnoreCase(redisCode.trim())) {
            throw new CustomException("验证码错误！");
        }
        //保证每个验证码只使用一次:安全
        redisUtils.delKey(codeKey);
    }
}
