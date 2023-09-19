package com.snow.dcl.controller;

import com.snow.dcl.service.SysFileService;
import com.snow.dcl.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @ClassName FileController
 * (功能描述)
 * 文件接口
 * @Author Dcl_Snow
 * @Create 2023/8/18 15:51
 * @Version 1.0.0
 */
@Api(tags = "文件管理")
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private SysFileService sysFileService;

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public ResponseResult uploadFile(@RequestParam String name, @RequestParam("file") MultipartFile file){
        sysFileService.create(name, file);
        return ResponseResult.success();
    }

}
