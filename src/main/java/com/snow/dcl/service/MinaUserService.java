package com.snow.dcl.service;

import com.snow.dcl.model.dto.system.WxLoginDto;

public interface MinaUserService {

    String getSessionId(String code);

    String wxLogin(WxLoginDto wxLoginDto);
}
