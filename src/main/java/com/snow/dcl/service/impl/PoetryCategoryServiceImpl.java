package com.snow.dcl.service.impl;

import com.snow.dcl.dao.PoetryCategoryRepository;
import com.snow.dcl.model.PoetryCategory;
import com.snow.dcl.service.PoetryCategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * (功能描述)
 * 诗词类别Service实现
 * @Author:Dcl_Snow
 * @Create: 2023/9/19 10:36
 * @version: 1.0.0
 */
@Service
public class PoetryCategoryServiceImpl implements PoetryCategoryService {

    @Resource
    private PoetryCategoryRepository repository;

    @Override
    public void save(PoetryCategory poetryCategory) {
        repository.save(poetryCategory);
    }

}
