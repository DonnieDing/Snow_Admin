package com.snow.dcl.controller;

import com.snow.dcl.service.SysFileService;
import com.snow.dcl.utils.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName FileController
 * (功能描述)
 * 文件接口
 * @Author Dcl_Snow
 * @Create 2023/8/18 15:51
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/file")
public class SysFileController {

    @Resource
    private SysFileService sysFileService;

    @PostMapping("/upload")
    public ResponseResult uploadFile(@RequestParam String name, @RequestParam("file") MultipartFile file){
        sysFileService.create(name, file);
        return ResponseResult.success();
    }

    @PostMapping("/images")
    public ResponseResult uploadImages(@RequestParam("image") MultipartFile image) {
        if (ObjectUtils.isEmpty(image)){
            return ResponseResult.fail().message("未上传图片");
        }
        String imagePath = sysFileService.images(image);
        return ResponseResult.success().data(imagePath);
    }
}
