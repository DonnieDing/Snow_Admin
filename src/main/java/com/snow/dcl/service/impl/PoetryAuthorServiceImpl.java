package com.snow.dcl.service.impl;

import com.snow.dcl.dao.PoetryAuthorRepository;
import com.snow.dcl.model.PoetryAuthor;
import com.snow.dcl.service.PoetryAuthorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (功能描述)
 * 诗词作者Service实现
 * @Author:Dcl_Snow
 * @Create: 2023/9/19 10:36
 * @version: 1.0.0
 */
@Service
public class PoetryAuthorServiceImpl implements PoetryAuthorService {

    @Resource
    private PoetryAuthorRepository repository;

    @Override
    public void save (PoetryAuthor poetryAuthor){
        repository.save(poetryAuthor);
    }

}
