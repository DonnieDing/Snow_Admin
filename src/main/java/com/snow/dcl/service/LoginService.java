package com.snow.dcl.service;

import com.snow.dcl.model.vo.LoginVo;

import java.util.Map;

public interface LoginService {

    Map<String, String> login(LoginVo loginVo);

    void logout();
}
