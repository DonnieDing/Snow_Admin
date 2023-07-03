package com.snow.dcl.controller;

import com.snow.dcl.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试")
@RestController
@RequestMapping("/")
public class TestController {

    @ApiOperation(value = "测试接口")
    @GetMapping("/test")
    // @PreAuthorize("hasAuthority('test')")
    public ResponseResult test() {
        return ResponseResult.success().message("hello");
    }
}
