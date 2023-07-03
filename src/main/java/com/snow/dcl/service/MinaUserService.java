package com.snow.dcl.service;

import com.snow.dcl.model.vo.WxLoginVo;
import com.snow.dcl.utils.ResponseResult;

import java.util.Map;

public interface MinaUserService {

    Map<String, String> getSessionId(String code);

    ResponseResult authLogin(WxLoginVo wxLoginVo);
}
