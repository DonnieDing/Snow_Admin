package com.snow.dcl.service;

import com.snow.dcl.model.dto.system.LoginDto;

import java.util.Map;

public interface LoginService {

    Map<String, String> login(LoginDto loginDto);

    void logout();
}
