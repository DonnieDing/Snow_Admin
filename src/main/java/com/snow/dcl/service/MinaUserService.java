package com.snow.dcl.service;

import com.snow.dcl.model.dto.system.WxCodeDto;

public interface MinaUserService {

    String minaLogin(WxCodeDto wxCodeDto);
}
