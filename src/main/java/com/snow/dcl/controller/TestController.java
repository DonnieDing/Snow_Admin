package com.snow.dcl.controller;

import com.snow.dcl.utils.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("/test")
    // @PreAuthorize("hasAuthority('test')")
    public ResponseResult test() {
        return ResponseResult.success().message("hello");
    }
}
