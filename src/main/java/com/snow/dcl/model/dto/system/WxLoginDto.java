package com.snow.dcl.model.dto.system;

import lombok.Data;

/**
 * @ClassName WxLoginVo
 * (功能描述)
 * 微信小程序登录参数
 * @Author Dcl_Snow
 * @Create 2022/3/4 14:22
 * @Version 1.0.0
 */
@Data
public class WxLoginDto {

    // 微信传递的加密数据，需要进行解密
    // private String encryptedData;

    // 解密算法的初始向量
    // private String iv;

    private String sessionId;
}
