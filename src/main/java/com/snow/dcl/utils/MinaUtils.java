package com.snow.dcl.utils;

import cn.hutool.http.HttpUtil;
import com.snow.dcl.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MinaUtils
 * (功能描述)
 * 小程序工具类
 * @Author Dcl_Snow
 * @Create 2023/11/22 9:05
 * @Version 1.0.0
 */
@Slf4j
@Component
public class MinaUtils {

    @Value("${mini.appId}")
    private String appId;

    @Value("${mini.secret}")
    private String secret;

    private final String CODE_TO_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";


    public String jscode2session(String code) {

        //创建参数
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", appId);
        paramMap.put("secret", secret);
        paramMap.put("js_code", code);
        paramMap.put("grant_type", "authorization_code");
        String result = HttpUtil.get(CODE_TO_SESSION_URL, paramMap);
        Map dataMap = JsonUtils.jsonToObject(result, Map.class);
        Integer errCode = (Integer) dataMap.get("errcode");
        if (errCode != null && errCode != 0) {
            throw new CustomException("小程序登录失败！");
        }
        return (String) dataMap.get("openid");
    }
}
