package com.snow.dcl.controller;

import com.snow.dcl.service.CaptchaService;
import com.snow.dcl.utils.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @GetMapping("/getCode")
    public ResponseResult captcha() {

        Map<String, String> captcha = captchaService.getCaptcha();

        return ResponseResult.success().data(captcha);
    }

}
