package com.snow.dcl.service.impl;

import cn.hutool.http.HttpUtil;
import com.snow.dcl.constant.CacheKeyConstant;
import com.snow.dcl.exception.CustomException;
import com.snow.dcl.model.dto.system.WxLoginDto;
import com.snow.dcl.service.MinaUserService;
import com.snow.dcl.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MinaUserServiceImpl
 * (功能描述)
 * 小程序接口实现类
 * @Author Dcl_Snow
 * @Create 2022/2/25 14:47
 * @Version 1.0.0
 */
@Service
public class MinaUserServiceImpl implements MinaUserService {

    @Value("${mini.appId}")
    private String appId;

    @Value("${mini.secret}")
    private String secret;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public Map<String,String> getSessionId(String code) {
        /**
         * 1.拼接微信登录凭证校验url（auth.code2Session）
         * 2.发送http请求，获取微信返回结果
         * 3.存储凭证到Ehcache
         * 4.生成一个sessionId，返回给前端，作为当前需要登录用户的标识
         * 5.sessionId作用，当下次用户再次点击登录时，标识是谁发起的微信登录
         */
        Map map = JsonUtils.jsonToObject(code, Map.class);
        String realCode = (String) map.get("code");

        String url = "https://api.weixin.qq.com/sns/jscode2session?" + "appid=" +
                appId +
                "&secret=" +
                secret +
                "&js_code=" +
                realCode +
                "&grant_type=authorization_code";

        String response = HttpUtil.get(url);

        System.out.println(realCode);

        Map dataMap = JsonUtils.jsonToObject(response, Map.class);
        Integer errCode = (Integer) dataMap.get("errcode");
        if (errCode != null && errCode != 0) {
            throw new CustomException("小程序登录失败！");
        }

        String sessionId = SessionIdUtils.CreateId();
        redisUtils.setValueTime(CacheKeyConstant.WX_SESSION_ID + sessionId, response, 60);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("sessionId", sessionId);

        return resultMap;
    }

    @Override
    public ResponseResult authLogin(WxLoginDto wxLoginDto) {
        /**
         * 1.对WxLoginVo进行数据解密
         * 2.解密完成后获取微信用户信息（openId等）
         * 3.使用唯一的openId，到用户表中查询用户是否存在：若用户存在则执行登录逻辑，若用户不存在（新用户）则先执行注册逻辑再登录
         * 4.生成jwt token返回前端，用户后续请求携带token，后端进行token验证
         */
        return null;
    }
}
