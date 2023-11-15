package com.snow.dcl.controller;

import com.snow.dcl.model.dto.system.WxCodeDto;
import com.snow.dcl.model.dto.system.WxLoginDto;
import com.snow.dcl.service.MinaUserService;
import com.snow.dcl.utils.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName MinaUserController
 * (功能描述)
 * 小程序用户接口
 * @Author Dcl_Snow
 * @Create 2022/2/25 14:43
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/user")
public class MinaUserController {

    @Resource
    private MinaUserService minaUserService;

    @PostMapping("/getSessionId")
    public ResponseResult getSessionId(@RequestBody WxCodeDto wxCodeDto) {
        String sessionId = minaUserService.getSessionId(wxCodeDto.getCode());
        return ResponseResult.success().data(sessionId);
    }

    @PostMapping("/wxLogin")
    public ResponseResult wxLogin(@RequestBody WxLoginDto wxLoginDto) {
        String token = minaUserService.wxLogin(wxLoginDto);
        return ResponseResult.success().data(token);
    }
}
