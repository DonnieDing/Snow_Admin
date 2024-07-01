package com.snow.dcl.service.impl;

import cn.hutool.core.codec.Base64;
import com.snow.dcl.constant.CacheKeyConstant;
import com.snow.dcl.service.WxService;
import com.snow.dcl.utils.JsonUtils;
import com.snow.dcl.utils.RedisUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;

/**
 * @ClassName WxServiceImpl
 * (功能描述)
 * 微信数据解密实现
 * @Author Dcl_Snow
 * @Create 2022/3/4 14:52
 * @Version 1.0.0
 */
@Service
public class WxServiceImpl implements WxService {

    @Resource
    private RedisUtils redisUtils;
    @Override
    public String wxDecrypt(String encryptedData, String sessionId, String iv) throws Exception {
        String dataValue = redisUtils.getValue(CacheKeyConstant.WX_SESSION_ID + sessionId).toString();
        Map dataMap = JsonUtils.jsonToObject(dataValue, Map.class);
        String sessionKey = (String) dataMap.get("session_key");
        byte[] encData = Base64.decode(encryptedData);
        byte[] key = Base64.decode(sessionKey);
        byte[] vector = Base64.decode(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(vector);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(encData);
        return new String(encrypted, "UTF-8");
    }
}
