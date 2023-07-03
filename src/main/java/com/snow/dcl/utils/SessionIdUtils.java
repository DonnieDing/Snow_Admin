package com.snow.dcl.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SessionIdUtils {
    /**
     * (功能描述)
     * 生成订单号，时间戳 + 八位随机数
     * @Author:Dcl_Snow
     * @Create: 2022/2/25 13:34
     * @version: 1.0.0
     */
    public static String CreateId(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            result += random.nextInt(10);
        }
        return newDate + result;
    }
}
