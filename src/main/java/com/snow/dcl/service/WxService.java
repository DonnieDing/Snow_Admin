package com.snow.dcl.service;

public interface WxService {
    String wxDecrypt(String encryptedData,String sessionId, String iv) throws Exception;
}
