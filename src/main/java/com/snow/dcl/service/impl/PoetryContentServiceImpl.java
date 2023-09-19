package com.snow.dcl.service.impl;

import com.snow.dcl.dao.PoetryContentRepository;
import com.snow.dcl.model.PoetryContent;
import com.snow.dcl.service.PoetryContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (功能描述)
 * 诗词正文内容Service实现
 * @Author:Dcl_Snow
 * @Create: 2023/9/19 10:36
 * @version: 1.0.0
 */
@Service
public class PoetryContentServiceImpl implements PoetryContentService {

    @Resource
    private PoetryContentRepository repository;

    @Override
    public void save(PoetryContent poetryContent) {
        repository.save(poetryContent);
    }

}
