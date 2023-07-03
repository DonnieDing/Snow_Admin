package com.snow.dcl.service;

import java.util.Map;

public interface CaptchaService {

    Map<String, String> getCaptcha();

    void verification(String captcha, String codeKey);
}

