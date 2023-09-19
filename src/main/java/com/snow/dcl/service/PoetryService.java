package com.snow.dcl.service;

import com.snow.dcl.model.PoetryContent;
import org.springframework.data.domain.Page;

/**
 * @ClassName PoetryService
 * (功能描述)
 * @Author Dcl_Snow
 * @Create 2023/9/19 13:40
 * @Version 1.0.0
 */
public interface PoetryService {
    Page<PoetryContent> findAll(PoetryContent poetryContent, Integer page, Integer size);
}
