package com.snow.dcl.controller;

import com.snow.dcl.model.PoetryContent;
import com.snow.dcl.model.dto.poetry.PoetryDto;
import com.snow.dcl.service.PoetryService;
import com.snow.dcl.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @ClassName PoetryController
 * (功能描述)
 * 诗词Controller
 * @Author Dcl_Snow
 * @Create 2023/9/19 13:17
 * @Version 1.0.0
 */
@Api(tags = "诗词管理")
@RestController
@RequestMapping("/poetry")
public class PoetryController {

    @Resource
    private PoetryService poetryService;

    @ApiOperation(value = "分页条件查询诗词")
    @PostMapping("/{page}/{size}")
    // @PreAuthorize("hasAuthority('poetry.list')")
    public ResponseResult query(@PathVariable Integer page, @PathVariable Integer size, @RequestBody PoetryDto poetryDto) {
        PoetryContent poetryContent = new PoetryContent();
        BeanUtils.copyProperties(poetryDto, poetryContent);
        Page<PoetryContent> PagePoetryContents = poetryService.findAll(poetryContent, page, size);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", PagePoetryContents.getTotalElements());
        hashMap.put("records", PagePoetryContents.getContent());
        return ResponseResult.success().data(hashMap);
    }

    @ApiOperation(value = "根据id查询详情")
    @PostMapping("/{id}")
    public ResponseResult findOne(@PathVariable Long id) {
        PoetryContent poetryContent = poetryService.findById(id);
        return ResponseResult.success().data(poetryContent);
    }

}
