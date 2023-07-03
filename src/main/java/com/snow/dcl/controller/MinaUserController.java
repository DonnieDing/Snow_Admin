package com.snow.dcl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.snow.dcl.model.vo.WxLoginVo;
import com.snow.dcl.service.MinaUserService;
import com.snow.dcl.utils.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

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
    public ResponseResult getSessionId(@RequestBody String code) {
        Map<String, String> map = minaUserService.getSessionId(code);
        return ResponseResult.success().data(map);
    }

    @PostMapping("/authLogin")
    public ResponseResult authLogin(@RequestBody WxLoginVo wxLoginVo) throws JsonProcessingException {
        return minaUserService.authLogin(wxLoginVo);
    }
}
