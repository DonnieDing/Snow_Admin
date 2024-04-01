package com.snow.dcl.controller;

import com.snow.dcl.model.PoetryAuthor;
import com.snow.dcl.model.dto.poetry.PoetryAuthorDto;
import com.snow.dcl.service.PoetryAuthorService;
import com.snow.dcl.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@Api(tags = "诗词作者管理")
@RestController
@RequestMapping("/poetry/author")
public class PoetryAuthorController {
    @Resource
    private PoetryAuthorService poetryAuthorService;

    @ApiOperation(value = "分页条件查询诗词")
    @PostMapping("/{page}/{size}")
    // @PreAuthorize("hasAuthority('poetry.list')")
    public ResponseResult query(@PathVariable Integer page, @PathVariable Integer size, @RequestBody PoetryAuthorDto poetryAuthorDto) {
        PoetryAuthor poetryAuthor = new PoetryAuthor();
        BeanUtils.copyProperties(poetryAuthorDto, poetryAuthor);
        Page<PoetryAuthor> PagePoetryAuthor = poetryAuthorService.findAll(poetryAuthor, page, size);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", PagePoetryAuthor.getTotalElements());
        hashMap.put("records", PagePoetryAuthor.getContent());
        return ResponseResult.success().data(hashMap);
    }

    @ApiOperation(value = "根据id查询详情")
    @PostMapping("/{id}")
    public ResponseResult findOne(@PathVariable Long id) {
        PoetryAuthor poetryAuthor = poetryAuthorService.findById(id);
        return ResponseResult.success().data(poetryAuthor);
    }
}
