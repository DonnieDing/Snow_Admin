package com.snow.dcl.controller;

import com.snow.dcl.service.CaptchaService;
import com.snow.dcl.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = "验证码管理")
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @ApiOperation(value = "获取验证码")
    @GetMapping("/getCode")
    public ResponseResult captcha() {

        Map<String, String> captcha = captchaService.getCaptcha();

        return ResponseResult.success().data(captcha);
    }

}
