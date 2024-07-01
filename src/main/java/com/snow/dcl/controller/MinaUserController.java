package com.snow.dcl.controller;

import com.snow.dcl.model.dto.system.WxCodeDto;
import com.snow.dcl.service.MinaUserService;
import com.snow.dcl.utils.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/minaLogin")
    public ResponseResult minaLogin(@RequestBody WxCodeDto wxCodeDto) {
        String token = minaUserService.minaLogin(wxCodeDto);
        return ResponseResult.success().data(token);
    }
}
