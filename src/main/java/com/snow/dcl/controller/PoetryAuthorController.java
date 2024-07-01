package com.snow.dcl.controller;

import com.snow.dcl.model.PoetryAuthor;
import com.snow.dcl.model.dto.poetry.PoetryAuthorDto;
import com.snow.dcl.service.PoetryAuthorService;
import com.snow.dcl.utils.ResponseResult;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/poetry/author")
public class PoetryAuthorController {
    @Resource
    private PoetryAuthorService poetryAuthorService;

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

    @PostMapping("/{id}")
    public ResponseResult findOne(@PathVariable Long id) {
        PoetryAuthor poetryAuthor = poetryAuthorService.findById(id);
        return ResponseResult.success().data(poetryAuthor);
    }
}
