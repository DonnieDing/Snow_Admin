package com.snow.dcl.service;

import com.snow.dcl.model.dto.system.WxLoginDto;
import com.snow.dcl.utils.ResponseResult;

import java.util.Map;

public interface MinaUserService {

    Map<String, String> getSessionId(String code);

    ResponseResult authLogin(WxLoginDto wxLoginDto);
}
